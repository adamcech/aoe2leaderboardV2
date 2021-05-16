package com.example.aoe2deleaderboardv2.coroutines.implementation

import com.example.aoe2deleaderboardv2.rest.RestClient
import kotlinx.coroutines.*

abstract class CoroutineRequestRest<T>(
    url: String,
    private val uiHandler: CoroutineHandler<T>
) {

    protected val restClient: RestClient = RestClient(url)

    protected abstract suspend fun run(): CoroutineResult<T>

    fun start() {
        uiHandler.preRun()

        CoroutineScope(Dispatchers.IO).launch {
            val result = run()

            withContext(Dispatchers.Main) {
                uiHandler.postRun(result)
            }
        }
    }

}
