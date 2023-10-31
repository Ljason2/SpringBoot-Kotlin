package com.kotlin.spring.management.repository.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TestMapper {
    @Select(" SELECT text FROM test WHERE text = 'HelloWorld' ")
    fun selectStringHelloWorld():String
}