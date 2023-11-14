package com.kotlin.spring.management.domain.common

import org.springframework.ui.Model

interface ResponseAdapter {
    fun adaptForWeb(model: Model, modelName: String)
    fun adaptForApi(): ResponseVo
}