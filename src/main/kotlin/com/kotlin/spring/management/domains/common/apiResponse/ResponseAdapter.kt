package com.kotlin.spring.management.domains.common.apiResponse

import org.springframework.ui.Model

interface ResponseAdapter {
    fun adaptForWeb(model: Model, modelName: String)
    fun adaptForApi(): ResponseVo
}