
@file:Suppress("UnstableApiUsage")

import kotlinx.benchmark.gradle.*
import org.jetbrains.kotlin.allopen.gradle.*

plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.13"
    kotlin("plugin.allopen") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.13")
}

sourceSets.configureEach {
    java.setSrcDirs(listOf("$name/src"))
    resources.setSrcDirs(listOf("$name/resources"))
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}


benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "sec"

        }
    }
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}