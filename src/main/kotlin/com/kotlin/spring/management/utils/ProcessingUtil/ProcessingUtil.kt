package com.kotlin.spring.management.utils.ProcessingUtil

import org.slf4j.LoggerFactory
import java.util.*
import java.util.logging.Logger

/** ProcessingUtil ver alpha 0.0.1 by hchanjune
 *  하나의 함수 내부에서 여러개의 비즈니스 로직을 처리할때,
 *  ProcessingUtil에 등록하여 해당 로직들의 처리여부를 순차적으로 확인
 *  또한 실행 결과에 따라서 리턴 값 결정
 *
 *  Spring MVC 패턴용 사용예시
 *
 *  Controller 단에서
 *  @RequestMapping()
 *  fun somefunction(){
 *  processingUtil = ProcessingUtil("SomeLogic")
 *  service.someLogic(someVar, processingUtil)
 *  return if (processingUnit.compile) "successUrl" else "failUrl"
 *  }
 *
 *  Service 단에서
 *  fun someLogic(someVar, processingUtil: ProcessingUtil){
 *  var process1 = logic1
 *  processingUtil.add("프로세스1", process1, false)
 *  var process2 = logic2
 *  processingUtil.add("프로세스2", process2, false)
 *  var process3 = logic3
 *  processingUtil.add("프로세스3", process3, true)
 *  }
 *
 *  위와 같이 사용. Service 에서 다른 Object를 리턴하여도 상관 없이, ProcessingUtil 은 process의 결과를 logging 하는 용도로 만 사용 하는 것도 가능
 *
 ************************ Parameters ***************************
 *     processName : 프로세스명                                 *
 *     process : 실제 프로세스 로직                             *
 *     optional : 실패 / 성공에 상관 없을 경우(log만 출력)       *
 ***************************************************************
 */

class ProcessingUtil(
    private var methodName: String
): LinkedHashMap<String, ProcessResult>() {

    private var processCounter: Int

    init {
        this.methodName = "Process-${methodName}"
        this.processCounter = 1
    }

    companion object{
        private val logger = LoggerFactory.getLogger(ProcessingUtil::class.java)
    }

    fun add(
        processName: String,
        process: Boolean,
        optional: Boolean
    ): ProcessingUtil {
        val processResult= ProcessResult(
            processName = processName,
            result = process,
            optional = optional
        )
        this["process${processCounter++}"] = processResult
        return this
    }

    fun compile(): Boolean {
        this.printLogs()
        return this.filter { !it.value.optional }
            .all { it.value.result }
    }

    fun getLogString(): String {
        return buildString {
            append("${this@ProcessingUtil.methodName} - Process Log\n")
            this@ProcessingUtil.forEach { (processKey, processResult) ->
                append("$processKey: ${processResult.processName} - " +
                        "Result: ${processResult.result} - " +
                        "Optional: ${processResult.optional}\n")
            }
        }
    }

    fun printLogs() {
        logger.info(this.getLogString())
    }

}

data class ProcessResult(
    val processName: String,
    val result: Boolean,
    val optional: Boolean
)
