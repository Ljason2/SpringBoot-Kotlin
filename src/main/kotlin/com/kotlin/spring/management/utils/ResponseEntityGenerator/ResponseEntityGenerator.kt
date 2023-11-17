package com.kotlin.spring.management.utils.ResponseEntityGenerator

import com.kotlin.spring.management.domains.common.ResponseVo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseEntityGenerator(){

    companion object{
        fun generateResponse(response: ResponseVo): ResponseEntity<ResponseVo>{
            return when (response.status){
                "success" -> {
                    ResponseEntity(response, HttpStatus.OK)
                }

                "fail" -> {
                    ResponseEntity(response, HttpStatus.BAD_REQUEST)
                }

                "error" -> {
                    ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
                }

                else ->{
                    ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
                }
            }
        }
    }

}