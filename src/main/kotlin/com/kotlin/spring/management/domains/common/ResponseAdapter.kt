package com.kotlin.spring.management.domains.common

import com.kotlin.spring.management.domains.common.ResponseVo
import org.springframework.ui.Model

interface ResponseAdapter {
    fun adaptForWeb(model: Model, modelName: String)
    fun adaptForApi(): ResponseVo
}