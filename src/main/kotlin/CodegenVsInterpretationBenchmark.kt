package org.example

import kotlinx.benchmark.*
import kotlin.random.Random

@State(Scope.Benchmark)
class CodegenVsInterpretationBenchmark {

    private val size = 1000000
    private val testData = mutableListOf<MutableMap<String, Any?>>()

    @Setup
    fun prepare() {
        repeat((0 until size).count()) {
            val program = programs.random()
            testData.add(
                mutableMapOf(
                    "event" to "run program",
                    "program" to program,
                    "command" to commands[program]!!
                )
            )
        }
    }

    @TearDown
    fun cleanup() {
        testData.clear()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    fun benchmarkCodegen(): Int {
        testData.forEach { codegen(it) }
        return testData.size
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    fun benchmarkInterpretation(): Int {
        testData.forEach { interpretation(it) }
        return testData.size
    }
}