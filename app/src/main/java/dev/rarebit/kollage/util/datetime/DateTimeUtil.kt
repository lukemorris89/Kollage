package dev.rarebit.kollage.util.datetime

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtil {
    private fun getDateTimeFormatter(pattern: String, locale: Locale = Locale.UK) =
        DateTimeFormatter.ofPattern(pattern, locale).withZone(ZoneId.systemDefault())

    private val dayMonthYearWithOrdinalSuffixFormat = getDateTimeFormatter("'%s%s' MMM, YYYY", locale = Locale.UK)
    private val dayMonthWithOrdinalSuffixFormat = getDateTimeFormatter("'%s%s' MMM", locale = Locale.UK)
    private val monthYearFormat = getDateTimeFormatter("MMM yyyy", locale = Locale.US)

    private fun String.applyOrdinalDaySuffix(dayOfMonth: Int): String {
        if (dayOfMonth < 1 || dayOfMonth > 31) return ""
        val ord = when (dayOfMonth) {
            1, 21, 31 -> "st"
            2, 22 -> "nd"
            3, 23 -> "rd"
            else -> "th"
        }
        return this.format(dayOfMonth, ord)
    }

    fun toDayMonthYearString(date: LocalDate) = date.format(dayMonthYearWithOrdinalSuffixFormat).applyOrdinalDaySuffix(date.dayOfMonth)
    fun LocalDate.toDayMonthString() = format(dayMonthWithOrdinalSuffixFormat).applyOrdinalDaySuffix(dayOfMonth)
    fun LocalDate.toMonthYearString() = format(monthYearFormat)

    fun getDateGroupings(dates: List<LocalDate?>) = dates.map {
        getDateGrouping(it)
    }.toSet()

    fun getDateGrouping(date: LocalDate?) = when {
        date == LocalDate.now() -> "Today"
        date == LocalDate.now().minusDays(1) -> "Yesterday"
        date?.month == LocalDate.now().month && date?.year == LocalDate.now().year -> date.toDayMonthString()
        date == null -> null
        else -> date.toMonthYearString()
    }
}