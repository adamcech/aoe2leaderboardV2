package com.example.aoe2deleaderboardv2.rest


import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class JsonHandler : JsonHttpResponseHandler() {

    var response: String? = null

    override fun onSuccess(statusCode: Int, headers: Array<Header?>?, response: JSONObject?) {
        this.response = response?.toString()
    }

    override fun onSuccess(statusCode: Int, headers: Array<Header?>?, timeline: JSONArray?) {
        this.response = timeline?.toString()
    }

}
