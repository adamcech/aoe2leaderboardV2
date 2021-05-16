package com.example.aoe2deleaderboardv2.dto

import org.json.JSONObject
import java.io.Serializable

class StringsAOE2(json: String) : Serializable {
    constructor(json: JSONObject) : this(json.toString())

    private var json = json
        set(value) {
            _civNames = null
            _mapNames = null
            field = value
        }

    private var _civNames: HashMap<Int, String>? = null
    private var _mapNames: HashMap<Int, String>? = null

    private fun getJson() = JSONObject(this.json)

    val civNames: HashMap<Int, String>
        get() {
            if (_civNames == null) {
                val hashMap = hashMapOf<Int, String>()

                getJson().optJSONArray("civ")?.let { names ->
                    for (i in 0 until names.length()) {
                        names.optJSONObject(i)?.let { detail ->
                            hashMap[detail.optInt("id", -1)] = detail.optString("string", "Unknown")
                        }
                    }
                }

                _civNames = hashMap
            }

            return _civNames as HashMap<Int, String>
        }

    val mapNames: HashMap<Int, String>
        get() {
            if (_mapNames == null) {
                val hashMap = hashMapOf<Int, String>()

                getJson().optJSONArray("map_type")?.let { names ->
                    for (i in 0 until names.length()) {
                        names.optJSONObject(i)?.let { detail ->
                            hashMap[detail.optInt("id", -1)] = detail.optString("string", "Unknown")
                        }
                    }
                }

                _mapNames = hashMap
            }
            return _mapNames as HashMap<Int, String>
        }

    override fun toString(): String {
        return "StringsAOE2(jsonString='$json')"
    }
}