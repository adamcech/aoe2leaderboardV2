package com.example.aoe2deleaderboardv2.coroutines.implementation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface CoroutineHandler<T> {

    fun preRun()
    fun postRun(result: CoroutineResult<T>)

}