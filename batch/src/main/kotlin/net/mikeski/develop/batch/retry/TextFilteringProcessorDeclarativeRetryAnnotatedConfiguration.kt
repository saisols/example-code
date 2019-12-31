package net.mikeski.develop.batch.retry

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
@Retryable
class TextFilteringProcessorDeclarativeRetryAnnotatedConfiguration : ItemProcessor<String, String> {
    companion object {
        val LOG = LoggerFactory.getLogger(TextFilteringProcessorDeclarativeRetryAnnotatedConfiguration::class.toString())
    }
    @Retryable(Exception::class, maxAttempts = 5, backoff = Backoff(delay = 1000L, multiplier = 2.0))
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