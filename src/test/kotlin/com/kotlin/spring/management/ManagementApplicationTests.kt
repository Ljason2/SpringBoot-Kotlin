package com.kotlin.spring.management

import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ManagementApplicationTests {

    @Test
    fun contextLoads() {

        var processingUtil = ProcessingUtil("TestProcess")
        someLogin(processingUtil)
        processingUtil.compile()
    }

    fun someLogin (processingUtil: ProcessingUtil){
        var loginOne: Boolean = true
        processingUtil.add("Logic1", loginOne, false)
        var loginTwo: Boolean = true
        processingUtil.add("Logic2", loginTwo, false)
        var loginThree: Boolean = true
        processingUtil.add("Logic3", loginThree, true)
    }

}
