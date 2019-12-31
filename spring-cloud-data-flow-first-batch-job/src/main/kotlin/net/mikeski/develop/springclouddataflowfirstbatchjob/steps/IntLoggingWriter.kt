package net.mikeski.develop.springclouddataflowfirstbatchjob.steps

import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Service

@Service
class IntLoggingWriter : ItemWriter<Int> {
    override fun write(p0: MutableList<out Int>) {
        p0.forEach{
            System.out.println("${it}")
        }
    }
}