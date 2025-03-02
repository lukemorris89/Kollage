package dev.rarebit.test_utils

import dev.rarebit.core.view.ResourceProvider

class TestResourceProvider : ResourceProvider {

    override fun getString(resId: Int): String = join(resId)

    override fun getString(resId: Int, vararg obj: Any): String = join(resId, *obj)

    private fun join(vararg obj: Any): String = obj.joinToString("-")
}
