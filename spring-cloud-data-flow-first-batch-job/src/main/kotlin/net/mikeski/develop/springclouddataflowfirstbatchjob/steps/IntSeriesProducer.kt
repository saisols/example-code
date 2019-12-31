package net.mikeski.develop.springclouddataflowfirstbatchjob.steps

import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Service

@Service
class IntSeriesProducer : ItemReader<Int> {
    private var currentValue = 1
    private var maxValue = 100
    override fun read(): Int? {
        if(currentValue > maxValue){
            return null
        }
        return currentValue++;
    }
}