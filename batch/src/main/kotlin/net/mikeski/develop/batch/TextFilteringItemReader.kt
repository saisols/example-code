package net.mikeski.develop.batch

import org.springframework.batch.item.ItemReader
import java.util.Arrays.asList

class TextFilteringItemReader : ItemReader<String> {
    val items = asList("This has no bad words", "This has one damn bad word")
    var position = 0;

    override fun read(): String? {
        if(position == items.size){
            return null
        }
        return items.get(position++)
    }
}