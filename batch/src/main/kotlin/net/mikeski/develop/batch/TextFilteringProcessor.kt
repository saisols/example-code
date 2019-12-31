package net.mikeski.develop.batch

import org.springframework.batch.item.ItemProcessor
import java.util.Arrays.asList


class TextFilteringProcessor: ItemProcessor<String, String> {
    private val badWords = asList("damn", "crap")

    override fun process(textToFilter: String): String {
        var finalValue = textToFilter

        badWords.forEach { badWord ->
            finalValue = finalValue.replace(badWord, "*".repeat(badWord.length), true)
        }
        return finalValue
    }
}