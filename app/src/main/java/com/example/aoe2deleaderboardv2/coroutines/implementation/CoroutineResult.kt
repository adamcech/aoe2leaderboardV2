package com.example.aoe2deleaderboardv2.coroutines.implementation

data class CoroutineResult<T>(
    val isSuccessful: Boolean,
    val msg: String = "",
    val result: T? = null
)