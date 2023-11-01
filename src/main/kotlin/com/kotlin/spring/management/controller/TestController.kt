package com.kotlin.spring.management.controller

import com.kotlin.spring.management.service.TestService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class TestController(private val testService: TestService) {

    companion object {
        private val logger = LoggerFactory.getLogger(TestController::class.java)
    }

    @GetMapping("/")
    fun testPage(model:Model) : String {
        logger.info("Hello World!")
        model.addAttribute("model", testService.getStringHelloWorld())
        return "thymeleaf"
    }
}