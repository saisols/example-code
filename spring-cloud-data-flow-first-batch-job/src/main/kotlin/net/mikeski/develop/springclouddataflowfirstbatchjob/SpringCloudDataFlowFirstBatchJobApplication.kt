package net.mikeski.develop.springclouddataflowfirstbatchjob

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.context.annotation.Import

//@SpringBootApplication
@Import(DatabaseConfig::class, BatchConfiguration::class)
@EnableTask
@EnableBatchProcessing
class SpringCloudDataFlowFirstBatchJobApplication

fun main(args: Array<String>) {
	runApplication<SpringCloudDataFlowFirstBatchJobApplication>(*args)
}
