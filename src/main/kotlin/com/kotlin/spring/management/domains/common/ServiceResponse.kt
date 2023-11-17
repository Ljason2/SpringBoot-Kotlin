package com.kotlin.spring.management.domains.common

import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import org.mybatis.spring.MyBatisSystemException
import org.slf4j.LoggerFactory
import org.springframework.ui.Model

class ServiceResponse<T>(
    private val serviceName: String? = null,
    private val status: String?,
    private val message: String?,
    private val errorDetail: String?,
    val data: T?,
    private val booleanAble: Boolean = false
): ResponseAdapter {

    companion object {

        private val logger = LoggerFactory.getLogger(ServiceResponse::class.java)

        fun <T> generateData(
            serviceName: String? = null,
            dataSupplier: () -> T?,
            customSuccessMessage: String? = null,
            customFailureMessage: String? = null,
            processingUtil: ProcessingUtil? = null
        ) : ServiceResponse<T> {
            return try {
                val data = dataSupplier()
                if (data != null){
                    ServiceResponse(
                        serviceName = serviceName,
                        status = "success",
                        message = customSuccessMessage ?: "Data Successfully Loaded.",
                        errorDetail = null,
                        data = data
                    )
                } else {
                    processingUtil?.discard(customFailureMessage ?: "Failed To Proceed The Process")
                    ServiceResponse(
                        serviceName = serviceName,
                        status = "fail",
                        message = customFailureMessage ?: "Failed To Load Data.",
                        errorDetail = null,
                        data = null
                    )
                }

            } catch (e: MyBatisSystemException) {
                processingUtil?.discard(e.localizedMessage,)
                ServiceResponse(
                    serviceName = serviceName,
                    status = "error",
                    message = "MyBatis Error Occurred Check The Queries",
                    errorDetail = e.localizedMessage,
                    data = null
                )
            } catch (e: Exception) {
                processingUtil?.discard(e.localizedMessage,)
                ServiceResponse(
                    serviceName = serviceName,
                    status = "error",
                    message = customFailureMessage ?: "Error Occurred While Processing Data",
                    errorDetail = e.localizedMessage,
                    data = null
                )
            }
        }

        fun <Boolean> simpleStatus(
            serviceName: String? = null,
            dataSupplier: () -> Boolean?,
            customSuccessMessage: String? = null,
            customFailureMessage: String? = null,
            processingUtil: ProcessingUtil? = null
        ): ServiceResponse<Boolean> {
            return try {
                val data = dataSupplier()
                if (data != null && data == true){
                    ServiceResponse(
                        serviceName = serviceName,
                        status = "success",
                        message = customSuccessMessage ?: "The Processing Logic Successfully Managed",
                        errorDetail = null,
                        data = data,
                        booleanAble = true
                    )
                } else {
                    processingUtil?.discard(customFailureMessage ?: "Failed To Proceed The Process")
                    ServiceResponse(
                        serviceName = serviceName,
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
                    serviceName = serviceName,
                    status = "error",
                    message = customFailureMessage ?: "Error Occurred While Processing Logic",
                    errorDetail = e.localizedMessage,
                    data = null,
                    booleanAble = true
                )
            }
        }
    }

    fun extractData(): T {
        printLogs()
        if (this.data != null) {
            return this.data as T
        } else {
            logger.error("Error : ${this.errorDetail}")
            throw Exception(this.message)
        }
    }

    fun extractStatus(): Boolean {
        printLogs()
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

    fun getLogString(): String{
        return buildString {
            append("\n***************************** ServiceResponse ************************************")
            append("\nService : $serviceName")
            append("\nStatus : $status")
            append("\nmessage : $message")
            if (errorDetail != null) append("\nerrorDetail : $errorDetail")
            append("\ndata : ${data.toString()}")
            append("\n**********************************************************************************")
        }
    }

    fun printLogs(){
        logger.info(this.getLogString())
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