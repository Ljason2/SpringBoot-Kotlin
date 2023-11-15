package com.kotlin.spring.management.domain.common

import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import org.springframework.ui.Model

class ServiceResponse<T>(
    private val status: String?,
    private val message: String?,
    private val errorDetail: String?,
    val data: T?,
    private val booleanAble: Boolean = false
): ResponseAdapter {

    companion object {
        fun <T> generateData(
            dataSupplier: () -> T?,
            customSuccessMessage: String? = null,
            customFailureMessage: String? = null,
            processingUtil: ProcessingUtil? = null
        ) : ServiceResponse<T> {
            return try {
                val data = dataSupplier()
                if (data != null){
                    ServiceResponse(
                        status = "success",
                        message = customSuccessMessage ?: "Data Successfully Loaded.",
                        errorDetail = null,
                        data = data
                    )
                } else {
                    processingUtil?.discard(customFailureMessage ?: "Failed To Proceed The Process")
                    ServiceResponse(
                        status = "fail",
                        message = customFailureMessage ?: "Failed To Load Data.",
                        errorDetail = null,
                        data = null
                    )
                }

            } catch (e: Exception) {
                processingUtil?.discard(e.localizedMessage,)
                ServiceResponse(
                    status = "error",
                    message = "Error Occurred While Processing Data",
                    errorDetail = e.localizedMessage,
                    data = null
                )
            }
        }

        fun <Boolean> simpleStatus(
            dataSupplier: () -> Boolean?,
            customSuccessMessage: String? = null,
            customFailureMessage: String? = null,
            processingUtil: ProcessingUtil? = null
        ): ServiceResponse<Boolean> {
            return try {
                val data = dataSupplier()
                if (data != null && data == true){
                    ServiceResponse(
                        status = "success",
                        message = customSuccessMessage ?: "The Processing Logic Successfully Managed",
                        errorDetail = null,
                        data = data,
                        booleanAble = true
                    )
                } else {
                    processingUtil?.discard(customFailureMessage ?: "Failed To Proceed The Process")
                    ServiceResponse(
                        status = "fail",
                        message = customFailureMessage ?: "Failed To Proceed The Process",
                        errorDetail = null,
                        data = data,
                        booleanAble = true
                    )
                }
            }
            catch (e: Exception){
                processingUtil?.discard(e.localizedMessage,)
                ServiceResponse(
                    status = "error",
                    message = "Error Occurred While Processing Logic",
                    errorDetail = e.localizedMessage,
                    data = null,
                    booleanAble = true
                )
            }
        }
    }

    fun extractData(): T {
        if (this.data != null) {
            return this.data
        } else {
            throw Exception(this.errorDetail)
        }

    }

    fun extractStatus(): Boolean {
        return if (this.booleanAble) {
            data as Boolean
        } else {
            when (this.status) {
                "success " -> true
                "fail" -> false
                else -> throw Exception("Error - ${this.errorDetail}")
            }
        }

    }

    override fun adaptForWeb(model: Model, modelName: String) {
        model.addAttribute(modelName, data)
    }

    override fun adaptForApi(): ResponseVo {
        return ResponseVo(
            status = this.status,
            message = this.message,
            errorDetail = this.errorDetail,
            data = this.data
        )
    }

}