package dev.rarebit.core.logger

import timber.log.Timber

class LoggerImpl : Logger {
    override fun logVerbose(
        tag: String,
        lazyMessage: () -> String,
    ) = Timber.tag(tag).v(lazyMessage())

    override fun logDebug(
        tag: String,
        lazyMessage: () -> String,
    ) = Timber.tag(tag).d(lazyMessage())

    override fun logWarning(
        throwable: Throwable?,
        tag: String,
        lazyMessage: () -> String,
    ) = Timber.tag(tag).w(throwable, lazyMessage())

    override fun logError(
        throwable: Throwable?,
        tag: String,
        lazyMessage: () -> String,
    ) = Timber.tag(tag).e(throwable, lazyMessage())
}