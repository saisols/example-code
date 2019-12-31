package net.mikeski.develop.batch.retry

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
@Retryable
class TextFilteringProcessorDeclarativeRetryDefaultConfiguration : ItemProcessor<String, String> {
    companion object {
        val LOG = LoggerFactory.getLogger(TextFilteringProcessorDeclarativeRetryDefaultConfiguration::class.toString())
    }
    @Retryable(Exception::class)
    @Throws(Exception::class)
    override fun process(stringToFilter: String): String? {
        LOG.warn("Processing String ${stringToFilter}");
        throw Exception("Simulating something failed")
    }

    @Recover
    fun recover(e: Exception, stringToFilter: String): String {
        LOG.warn("Recover called with exception ${e.message} for argument ${stringToFilter}");
        return "Error with filter"
    }
}