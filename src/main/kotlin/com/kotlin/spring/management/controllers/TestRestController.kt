package com.kotlin.spring.management.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "RestController For Test", description = "Test Controller for swagger3 test")
@RestController
@RequestMapping("/")
class TestRestController {

    @Operation(summary = "문자열 리턴", description = "입력 받은 문자열을 리턴합니다.")
    @Parameter(name = "string", description = "입력할 문자열")
    @GetMapping("returnString")
    fun returnString(@RequestParam string: String) : String = "리턴할 문자열 의 값은 $string 입니다."

}