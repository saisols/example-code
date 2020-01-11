package net.mikeski.develop.springclouddataflowfirstbatchjob

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

@Configuration
@Import(DatabaseConfig.WithoutDB::class,DatabaseConfig.WithDB::class)
class DatabaseConfig {
    @Profile("standalone")
    @EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
    internal class WithoutDB

    @Profile("!standalone")
    @EnableAutoConfiguration
    internal class WithDB
}