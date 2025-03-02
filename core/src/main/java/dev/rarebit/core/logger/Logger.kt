package dev.rarebit.core.logger

import java.util.regex.Pattern

interface Logger {
    companion object {
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }

    fun logVerbose(
        tag: String = createTag(),
        lazyMessage: () -> String,
    )

    fun logDebug(
        tag: String = createTag(),
        lazyMessage: () -> String,
    )

    fun logWarning(
        throwable: Throwable? = null,
        tag: String = createTag(),
        lazyMessage: () -> String,
    )

    fun logError(
        throwable: Throwable? = null,
        tag: String = createTag(),
        lazyMessage: () -> String,
    )

    @Suppress("ThrowingExceptionsWithoutMessageOrCause")
    private fun createTag(): String {
        var tag = Throwable().stackTrace
            .first { it.className.contains("Log").not() }
            .className.substringAfterLast('.')

        val matcher = ANONYMOUS_CLASS.matcher(tag)
        if (matcher.find()) {
            tag = matcher.replaceAll("&AnonymousClass")
        }
        return tag
    }
}
