package com.kotlin.spring.management

import com.kotlin.spring.management.utils.ProcessingUtil.ProcessingUtil
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.lang.RuntimeException

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
        var loginFour: Boolean = false
        processingUtil.add("Logic4", loginFour, true)
        processingUtil.addFunction(
            "Logic5 Function",
            processFunction = {
                throw RuntimeException("Runtime Exception Occurred!!!")
            },
            true
        )
        processingUtil.addFunction(
            "Logic6 Function",
            processFunction = {
                val testObject: String? = null
                testObject!!.length // 여기서 NullPointerException 발생
                true
            },
            false
        )
    }

}
