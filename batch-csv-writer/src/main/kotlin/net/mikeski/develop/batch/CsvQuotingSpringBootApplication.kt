package net.mikeski.develop.batch

import net.mikeski.develop.batch.components.CSVQuotingBeanWrapperFieldExtractor
import net.mikeski.develop.batch.components.DataReader
import net.mikeski.develop.batch.components.DataWriter
import net.mikeski.develop.batch.domain.Store
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.batch.item.support.CompositeItemWriter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.retry.annotation.EnableRetry
import java.io.File
import java.io.Writer

@SpringBootApplication(exclude = [
    DataSourceAutoConfiguration::class,
    DataSourceTransactionManagerAutoConfiguration::class,
    HibernateJpaAutoConfiguration::class
])
@EnableBatchProcessing
@EnableRetry(proxyTargetClass = true)
class CsvQuotingSpringBootApplication {
    companion object {
        val logger = LoggerFactory.getLogger(CsvQuotingSpringBootApplication.toString())
        val STORE_FIELDS = arrayOf("name", "description")
    }

    fun getOutputFileResource(): Resource =
            FileSystemResource("${System.getProperty("java.io.tmpdir")}${File.separator}spring-csv-test.csv")

    fun getCSVQuotingBeanWrapperFieldExtractor(): BeanWrapperFieldExtractor<Store> {
        val extractor: CSVQuotingBeanWrapperFieldExtractor<Store> = CSVQuotingBeanWrapperFieldExtractor()
        extractor.setNames(STORE_FIELDS)
        return extractor;
    }

    fun getStoreCSVWriter() : ItemWriter<Store> {
        val resource = getOutputFileResource()
        logger.info("Results will be written to ${resource}")

        val lineAggregator = DelimitedLineAggregator<Store>()
        lineAggregator.setFieldExtractor(getCSVQuotingBeanWrapperFieldExtractor())

        val writer = FlatFileItemWriterBuilder<Store>()
                .resource(getOutputFileResource())
                .lineAggregator(lineAggregator)
                .name("Store CSV Writer")
                .headerCallback(object: FlatFileHeaderCallback {
                    override fun writeHeader(writer: Writer) {
                        writer.write(STORE_FIELDS.joinToString(","))
                    }
                }).build()
        return writer
    }

    @Bean(name = ["Batch example 1"])
    fun jobLogAllFilteredTextItems(jbf: JobBuilderFactory, sbf: StepBuilderFactory): Job {
        val compositeItemWriter = CompositeItemWriter<Store>()
        compositeItemWriter.setDelegates(listOf(DataWriter(), getStoreCSVWriter()))

        return jbf.get("demo-text-job1").start(
                sbf.get("demo-text-step1")
                        .chunk<Store, Store>(1)
                        .reader(DataReader())
                        .writer(compositeItemWriter)
                        .build()).build()
    }
}

fun main(args: Array<String>) {
    runApplication<CsvQuotingSpringBootApplication>(*args)
}
