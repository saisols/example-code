package net.mikeski.develop.batch

import net.mikeski.develop.batch.retry.TextFilteringProcessorDeclarativeRetryAnnotatedConfiguration
import net.mikeski.develop.batch.retry.TextFilteringProcessorDeclarativeRetryDefaultConfiguration
import net.mikeski.develop.batch.retry.TextFilteringProcessorDeclarativeRetryImperativeConfiguration
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
@EnableBatchProcessing
@EnableRetry(proxyTargetClass=true)
class TextFilteringSpringBootApplication {

    @Autowired lateinit var textFilteringProcessorDeclarativeRetryDefaultConfiguration: TextFilteringProcessorDeclarativeRetryDefaultConfiguration

    @Autowired lateinit var textFilteringProcessorDeclarativeRetryAnnotatedConfiguration: TextFilteringProcessorDeclarativeRetryAnnotatedConfiguration

    @Autowired lateinit var textFilteringProcessorDeclarativeRetryImperativeConfiguration: TextFilteringProcessorDeclarativeRetryImperativeConfiguration

    @Bean(name=["Batch example 1"])
    fun jobLogAllFilteredTextItems(jbf: JobBuilderFactory, sbf: StepBuilderFactory): Job {
        return jbf.get("demo-text-job1").start(
                sbf.get("demo-text-step1")
                        .chunk<String, String>(1)
                        .reader(TextFilteringItemReader())
                        .processor(TextFilteringProcessor())
                        .writer(TextFilteringItemWriter())
                        .build()).build()
    }

    @Bean(name=["Batch example 2"])
    fun jobLogAllFiltereddTextItemsAndFailures(jbf: JobBuilderFactory, sbf: StepBuilderFactory): Job {
        return jbf.get("demo-text-job2").start(
                sbf.get("demo-text-step2")
                        .chunk<String, String>(1)
                        .reader(TextFilteringItemReader())
                        .processor(textFilteringProcessorDeclarativeRetryDefaultConfiguration)
                        .writer(TextFilteringItemWriter())
                        .build()).build()
    }

    @Bean(name=["Batch example 3"])
    fun jobLogAllFiltereddTextItemsAndFailuresConfigured(jbf: JobBuilderFactory, sbf: StepBuilderFactory): Job {
        return jbf.get("demo-text-job3").start(
                sbf.get("demo-text-step3")
                        .chunk<String, String>(1)
                        .reader(TextFilteringItemReader())
                        .processor(textFilteringProcessorDeclarativeRetryAnnotatedConfiguration)
                        .writer(TextFilteringItemWriter())
                        .build()).build()
    }

    @Bean(name=["Batch example 4"])
    fun jobLogAllFiltereddTextItemsAndFailuresImperative(jbf: JobBuilderFactory, sbf: StepBuilderFactory): Job {
        return jbf.get("demo-text-job4").start(
                sbf.get("demo-text-step4")
                        .chunk<String, String>(1)
                        .reader(TextFilteringItemReader())
                        .processor(textFilteringProcessorDeclarativeRetryImperativeConfiguration)
                        .writer(TextFilteringItemWriter())
                        .build()).build()
    }
}

fun main(args: Array<String>) {
    runApplication<TextFilteringSpringBootApplication>(*args)
}
