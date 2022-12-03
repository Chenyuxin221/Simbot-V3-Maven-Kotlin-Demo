package com.huahua.demo

import love.forte.simboot.spring.autoconfigure.EnableSimbot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSimbot
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
