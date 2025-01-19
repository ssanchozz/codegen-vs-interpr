package org.example

import kotlinx.benchmark.*
import kotlin.random.Random

@State(Scope.Benchmark)
class CodegenVsInterpretationBenchmark {

    private val size = 1000
    private val testData = mutableListOf<MutableMap<String, Any?>>()

    @Setup
    fun prepare() {
        repeat((0 until size).count()) {
            testData.add(
                mutableMapOf(
                    "a" to randomStringByKotlinRandom(Random.nextInt(5, 15)),
                    "b" to randomStringByKotlinRandom(Random.nextInt(5, 15))
                )
            )
        }
    }

    @TearDown
    fun cleanup() {
        testData.clear()
    }

    @Benchmark
    fun benchmarkCodegen(): Int {
        testData.forEach { codegen(it) }
        return testData.size
    }

    @Benchmark
    fun benchmarkInterpretation(): Int {
        testData.forEach { interpretation(it) }
        return testData.size
    }
}