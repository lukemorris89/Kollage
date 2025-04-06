package dev.rarebit.kollage.util.fileutil

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException

fun isUriValid(context: Context, uri: Uri): Boolean {
    return try {
        context.contentResolver.openFileDescriptor(uri, "r")?.use {
            true
        } ?: false
    } catch (e: FileNotFoundException) {
        false
    } catch (e: SecurityException) {
        false
    }
}
