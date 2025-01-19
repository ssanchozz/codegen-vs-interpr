package org.example

import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.measureTime

fun main() {
    naiveBenchmark()
}

private fun naiveBenchmark() {
    val testData = (0 until 10000000).map {
        mutableMapOf<String, Any?>(
            "a" to randomStringByKotlinRandom(Random.nextInt(5, 15)),
            "b" to randomStringByKotlinRandom(Random.nextInt(5, 15))
        )
    }

    val elapsedCodegen = measureTime {
        testData.forEach {
            codegen(it)
        }
    }

    val elapsedInterpretation = measureTime {
        testData.forEach {
            interpretation(it)
        }
    }

    println("Codegen took $elapsedCodegen")
    println("Interpretation took $elapsedInterpretation")

    if (elapsedCodegen < elapsedInterpretation) {
        val percentage = (elapsedInterpretation.toDouble(DurationUnit.MILLISECONDS) - elapsedCodegen.toDouble(DurationUnit.MILLISECONDS)) * 100 / elapsedCodegen.toDouble(DurationUnit.MILLISECONDS)
        println("Codegen faster than interpretation by $percentage%")
    }
}
