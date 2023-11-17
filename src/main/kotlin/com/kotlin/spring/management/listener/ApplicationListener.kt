package com.kotlin.spring.management.listener

import jakarta.servlet.ServletContext
import jakarta.servlet.ServletContextEvent
import jakarta.servlet.ServletContextListener
import jakarta.servlet.annotation.WebListener

@WebListener
class ApplicationListener : ServletContextListener {

    fun ApplicationListener() {
        // TODO Auto-generated constructor stub
    }

    override fun contextDestroyed(sce: ServletContextEvent?) {
        // TODO Auto-generated method stub
    }

    override fun contextInitialized(sce: ServletContextEvent) {
        val application: ServletContext = sce.servletContext
        application.setAttribute("root", application.contextPath)
    }
}