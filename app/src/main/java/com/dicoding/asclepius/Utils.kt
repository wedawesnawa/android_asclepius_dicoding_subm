package com.dicoding.asclepius

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

object Utils {
    fun uriToBitmap(context: Context, uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
}