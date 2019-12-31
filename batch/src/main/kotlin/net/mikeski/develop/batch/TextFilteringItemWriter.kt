package net.mikeski.develop.batch

import org.springframework.batch.item.ItemWriter

class TextFilteringItemWriter : ItemWriter<String> {
    override fun write(items: MutableList<out String>) {
        items.forEach { println(it) }
    }
}