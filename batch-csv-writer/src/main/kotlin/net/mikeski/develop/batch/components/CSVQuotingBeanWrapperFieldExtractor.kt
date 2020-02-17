package net.mikeski.develop.batch.components

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor

class CSVQuotingBeanWrapperFieldExtractor<T>() : BeanWrapperFieldExtractor<T>() {
    val quote: String = "\""
    val replaceQuoteWith = "'"

    fun quoteItem(item: Any): Any = if (item is String) "${quote}${item.replace(quote, replaceQuoteWith)}${quote}" else item

    override fun extract(item: T): Array<Any> =
            super.extract(item).map {
                quoteItem(it)
            }.toTypedArray()
}