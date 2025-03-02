package dev.rarebit.core.view

data class ViewEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun consume(): T? = content.takeUnless { hasBeenHandled }?.also { hasBeenHandled = true }

    fun peek(): T = content
}