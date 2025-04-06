package dev.rarebit.kollage.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object ShareManager {
    fun shareCollage(context: Context, uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(shareIntent, "Share collage")
        context.startActivity(chooser)
    }
}