package net.mikeski.develop.batch.components

import net.mikeski.develop.batch.domain.Store
import org.springframework.batch.item.ItemWriter

class DataWriter : ItemWriter<Store> {
    override fun write(stores: MutableList<out Store>) {
        stores.forEach{
            println(it)
        }
    }
}