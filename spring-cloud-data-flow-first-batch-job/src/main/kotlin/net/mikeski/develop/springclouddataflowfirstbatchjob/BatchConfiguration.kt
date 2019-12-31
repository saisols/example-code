package net.mikeski.develop.springclouddataflowfirstbatchjob

import net.mikeski.develop.springclouddataflowfirstbatchjob.steps.IntLoggingWriter
import net.mikeski.develop.springclouddataflowfirstbatchjob.steps.IntSeriesProducer
import net.mikeski.develop.springclouddataflowfirstbatchjob.steps.SquaringIntProcessor
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchConfiguration {
    @Autowired
    lateinit var intSeriesProducer: IntSeriesProducer

    @Autowired
    lateinit var squaringIntProcessor: SquaringIntProcessor

    @Autowired
    lateinit var intLoggingWriter: IntLoggingWriter

    @Bean
    fun jobLogAllFiltereddTextItemsAndFailuresImperative(jbf: JobBuilderFactory, sbf: StepBuilderFactory): Job {
        return jbf.get("int-squaring-job").start(
                sbf.get("int-squaring-job-step1")
                        .chunk<Int, Int>(1)
                        .reader(intSeriesProducer)
                        .processor(squaringIntProcessor)
                        .writer(intLoggingWriter)
                        .build()).build()
    }
}