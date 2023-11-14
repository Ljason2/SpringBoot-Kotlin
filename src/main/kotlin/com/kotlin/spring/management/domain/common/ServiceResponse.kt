package com.kotlin.spring.management.domain.common

import org.springframework.ui.Model

class ServiceResponse<T>(
    private val result: String?,
    private val message: String?,
    private val exceptionMessage: String?,
    private val data: T?
): ResponseAdapter {

    companion object {
        fun <T : Any> createData(
            dataSupplier: () -> T
        ) : ServiceResponse<T> {
            return try {
                val data = dataSupplier()
                ServiceResponse(
                    result = "success",
                    message = "${data::class.simpleName} loaded successfully.",
                    exceptionMessage = null,
                    data = data)
            } catch (e: Exception) {
                ServiceResponse(
                    result = "error",
                    message = "Error loading data.",
                    exceptionMessage = e.localizedMessage,
                    data = null)
            }
        }
    }

    override fun adaptForWeb(model: Model, modelName: String) {
        model.addAttribute(modelName, data)
    }

    override fun adaptForApi(): ResponseVo {
        return ResponseVo(
            result = this.result,
            message = this.message,
            exceptionMessage = this.exceptionMessage,
            data = this.data
        )
    }

}