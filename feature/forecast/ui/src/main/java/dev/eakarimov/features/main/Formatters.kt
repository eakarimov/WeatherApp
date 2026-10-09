package dev.eakarimov.features.main

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal val LocalDate.dayOfWeekString: String
    get() = format(dayOfWeekPattern)

internal val LocalDateTime.dayOfWeekString: String
    get() = format(dayOfWeekPattern)

internal val LocalDateTime.dayOfMonthString: String
    get() = format(DateTimeFormatter.ofPattern("MMM d"))

internal val LocalDateTime.hourString: String
    get() = format(DateTimeFormatter.ofPattern("HH:mm"))

private val dayOfWeekPattern = DateTimeFormatter.ofPattern("EEE")
