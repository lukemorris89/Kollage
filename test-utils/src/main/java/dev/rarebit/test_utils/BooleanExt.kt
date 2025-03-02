package dev.rarebit.test_utils

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull

fun Boolean?.shouldBeTrue() = shouldNotBeNull().shouldBeTrue()

fun Boolean?.shouldBeFalse() = shouldNotBeNull().shouldBeFalse()