package net.mikeski.develop.batch.components

import net.mikeski.develop.batch.domain.Store
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail

internal class CSVQuotingBeanWrapperFieldExtractorTest {

    fun getExtractor(): CSVQuotingBeanWrapperFieldExtractor<Store>{
        val extractor = CSVQuotingBeanWrapperFieldExtractor<Store>()
        extractor.setNames(arrayOf("name", "description"))
        return extractor
    }

    @Test
    fun testNonStringItems(){
        val extractor = getExtractor()
        arrayOf(1, 1L, arrayOf("asdf", 2, 4L)).forEach{
            assertEquals(it, extractor.quoteItem(it))
        }
    }

    @Test
    fun testQuoteStringItem_Plain() {
        val extractor = getExtractor()
        var nameToQuote = "Test name"
        assertEquals("\"${nameToQuote}\"",extractor.quoteItem(nameToQuote))
    }

    @Test
    fun testQuoteStringItem_ContainsQuote() {
        val extractor = getExtractor()
        val nameToQuote = "Test \"name\""
        val expectedValue = "\"Test 'name'\""
        assertEquals(expectedValue, extractor.quoteItem(nameToQuote))
    }

    @Test
    fun testExtract_ContainsQuote(){
        val extractor = getExtractor()

        val nameToQuote = "Test \"name\""
        val expectedNameValue = "\"Test 'name'\""

        val description = "This is a description \n with a newline"
        val expectedDescription = "\"This is a description \n with a newline\""

        val store = Store(nameToQuote, description)
        val extractedValues = extractor.extract(store)

        assertEquals(expectedNameValue, extractedValues[0])
        assertEquals(expectedDescription, extractedValues[1])
    }
}