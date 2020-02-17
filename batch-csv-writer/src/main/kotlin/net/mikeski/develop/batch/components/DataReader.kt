package net.mikeski.develop.batch.components

import net.mikeski.develop.batch.domain.Store
import org.springframework.batch.item.ItemReader

class DataReader : ItemReader<Store> {
    val stores = arrayOf(Store("Test1", "This is a test"), Store("Test2", "This is \n another test"))
    var counter = 0;
    override fun read(): Store? = if(counter >= stores.size) null else stores.get(counter++)
}