package com.example.demo.batch.job

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SampleJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    private val logger = LoggerFactory.getLogger(SampleJobConfig::class.java)

    @Bean
    fun sampleJob(): Job = jobBuilderFactory.get("Sample Job")
        .start(sampleStep1())
        .next(sampleStep2())
        .build()

    @Bean
    fun sampleStep1(): Step = stepBuilderFactory.get("sampleStep1")
        .tasklet { _, _ ->
            logger.info("sampleStep1 -> start")
            logger.info("sampleStep1 -> end")
            RepeatStatus.FINISHED
        }
        .build()

    @Bean
    fun sampleStep2(): Step = stepBuilderFactory.get("sampleStep2")
        .tasklet { _, _ ->
            logger.info("sampleStep2 -> start")
            logger.info("sampleStep2 -> end")
            RepeatStatus.FINISHED
        }
        .build()
}
