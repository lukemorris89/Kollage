package dev.rarebit.core.logger

interface WithLogger {
    val logger: Logger

    fun logTag(): String? = null

    fun logVerbose(lazyMessage: () -> String) =
        logTag()?.let { logger.logVerbose(it, lazyMessage) }
            ?: logger.logVerbose(lazyMessage = lazyMessage)

    fun logDebug(lazyMessage: () -> String) =
        logTag()?.let { logger.logDebug(it, lazyMessage) }
            ?: logger.logDebug(lazyMessage = lazyMessage)

    fun logWarning(throwable: Throwable? = null, lazyMessage: () -> String) =
        logTag()?.let { logger.logWarning(throwable, it, lazyMessage) }
            ?: logger.logWarning(throwable = throwable, lazyMessage = lazyMessage)

    fun logError(throwable: Throwable? = null, lazyMessage: () -> String) =
        logTag()?.let { logger.logError(throwable, it, lazyMessage) }
            ?: logger.logError(throwable = throwable, lazyMessage = lazyMessage)
}
