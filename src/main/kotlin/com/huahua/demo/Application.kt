package com.huahua.demo

import love.forte.simboot.spring.autoconfigure.EnableSimbot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSimbot
class SimbotV3MavenDemoApplication

fun main(args: Array<String>) {
    runApplication<SimbotV3MavenDemoApplication>(*args)
}
