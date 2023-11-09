package com.kotlin.spring.management.services

import com.kotlin.spring.management.repositories.mappers.TestMapper
import org.springframework.stereotype.Service

@Service
class TestService(private val testMapper : TestMapper) {

    fun getStringHelloWorld() : String{
        return testMapper.selectStringHelloWorld()
    }

}