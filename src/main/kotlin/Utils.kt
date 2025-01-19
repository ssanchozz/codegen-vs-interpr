package org.example

import kotlin.random.Random

private val CHAR_POOL = "abcdyadfngoibbrtqwerpoertyuixcvmn".toCharArray()

fun randomStringByKotlinRandom(len: Int) = (1..len)
    .map { Random.nextInt(0, CHAR_POOL.size).let { CHAR_POOL[it] } }
    .joinToString("")