package com.kotlin.spring.management.configurations.listener

import com.kotlin.spring.management.listener.ApplicationListener
import jakarta.servlet.ServletContextListener
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ListenerConfig {

    @Bean
    fun servletListener(): ServletListenerRegistrationBean<ServletContextListener> {
        return ServletListenerRegistrationBean(ApplicationListener())
    }

}