package net.mikeski.develop.batch.retry

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener
import org.springframework.retry.policy.TimeoutRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Service

@Service
class TextFilteringProcessorDeclarativeRetryImperativeConfiguration : ItemProcessor<String, String> {
    companion object {
        val LOG = LoggerFactory.getLogger(TextFilteringProcessorDeclarativeRetryImperativeConfiguration::class.toString())
    }

    override fun process(stringToFilter: String): String? {
        var template = RetryTemplate()
        val policy = TimeoutRetryPolicy()
        template.registerListener(
                object : RetryListener {
                    override fun <T : Any?, E : Throwable?> open(p0: RetryContext?, p1: RetryCallback<T, E>?): Boolean {
                        LOG.info("Retry listener openv ${p0}")
                        p0!!.setAttribute("Hi", "hi")
                        return true
                    }

                    override fun <T : Any?, E : Throwable?> close(p0: RetryContext?, p1: RetryCallback<T, E>?, p2: Throwable?) {
                        LOG.info("Retry listener close")
                    }

                    override fun <T : Any?, E : Throwable?> onError(p0: RetryContext?, p1: RetryCallback<T, E>?, p2: Throwable?) {
                        LOG.info("Hi::${p0!!.getAttribute("Hi")} Retry listener onError ${p0} -")
                    }
                }
        )
        policy.timeout = 100L
        template.setRetryPolicy(policy);
        val result: String = template.execute<String, Exception>(
                object : RetryCallback<String, Exception> {
                    @Throws(Exception::class)
                    override fun doWithRetry(context: RetryContext): String { // Do stuff that might fail, e.g. webservice operation
                        LOG.warn("Processing String ${stringToFilter}, throwing error");
                        throw Exception("Simulating something failed in TextFilteringProcessorDeclarativeRetryImperativeConfiguration")
                    }
                }
        )
        return result
    }

}
