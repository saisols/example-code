package net.mikeski.develop.springclouddataflowfirstbatchjob.steps

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Service

@Service
class SquaringIntProcessor: ItemProcessor<Int, Int> {
    override fun process(p0: Int): Int? {
        return p0 * p0
    }
}