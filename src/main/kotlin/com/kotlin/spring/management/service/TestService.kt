package com.kotlin.spring.management.service

import com.kotlin.spring.management.repository.mapper.TestMapper
import org.springframework.stereotype.Service

@Service
class TestService(val testMapper : TestMapper) {

    fun getStringHelloWorld() : String{
        return testMapper.selectStringHelloWorld()
    }

}