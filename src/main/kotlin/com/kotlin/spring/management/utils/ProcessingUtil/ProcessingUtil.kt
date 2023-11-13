package com.kotlin.spring.management.utils.ProcessingUtil

import java.util.*


class ProcessingUtil(
    private var methodName: String
): LinkedHashMap<String, ProcessResult>() {
    private var processCounter = 1

    init {
        this.methodName = "Process-${methodName}"
    }

    fun add(
        process: Boolean,
        processName: String,
        successMessage: String,
        errorMessage: String
    ): ProcessingUtil {
        val processResult= ProcessResult(
            processName = processName,
            result = process,
            successMessage = successMessage,
            errorMessage = errorMessage
        )
        this["process${processCounter++}"] = processResult
        return this
    }

    fun compile(): Boolean {
        return this.all { it.value.result }
    }

    fun printLogs() {
        var resultLog =
        "${this.methodName}"

    }

}

data class ProcessResult(
    val processName: String,
    val result: Boolean,
    val successMessage: String,
    val errorMessage: String
)


/** Option 즉, 성공해도 실패해도 상관없는 process 가 존재 할 수 도 있음 */