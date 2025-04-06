package dev.rarebit.kollage.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import dev.rarebit.kollage.data.model.Collage
import kotlinx.serialization.json.Json
import net.thauvin.erik.urlencoder.UrlEncoderUtil

object CustomNavType {

    val CollageType = object : NavType<Collage>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Collage? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): Collage {
            return Json.decodeFromString(UrlEncoderUtil.decode(value))
        }

        override fun serializeAsValue(value: Collage): String {
            return UrlEncoderUtil.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Collage) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}