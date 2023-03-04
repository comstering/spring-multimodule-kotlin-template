package com.example.demo

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.task.configuration.EnableTask

@SpringBootApplication
@EnableBatchProcessing
@EnableTask
class DemoBatchApplication

fun main(args: Array<String>) {
    runApplication<DemoBatchApplication>(*args)
}
