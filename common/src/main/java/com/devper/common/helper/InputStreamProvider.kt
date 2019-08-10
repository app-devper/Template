package com.devper.common.helper

import android.content.Context
import android.net.Uri
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream
import java.net.URL

fun fromURI(context: Context, uri: Uri): InputStreamWithSource? {
    return when (uri.scheme) {
        "content" -> fromContent(context, uri)
        "http", "https" -> return fromOKHttp(uri)
        "file" -> getDefaultInputStreamForUri(uri)
        else -> {
            getDefaultInputStreamForUri(uri)
        }
    }
}

private fun fromOKHttp(uri: Uri): InputStreamWithSource? {
    val client = OkHttpClient()
    val url = URL(uri.toString())
    val requestBuilder = Request.Builder().url(url)
    val request = requestBuilder.build()
    val response = client.newCall(request).execute()
    val body = response.body()
    if (body != null) {
        return InputStreamWithSource(uri.toString(), body.byteStream())
    }
    return null
}

private fun fromContent(ctx: Context, uri: Uri) = InputStreamWithSource(uri.toString(), ctx.contentResolver.openInputStream(uri)!!)

private fun getDefaultInputStreamForUri(uri: Uri) = InputStreamWithSource(uri.toString(), BufferedInputStream(URL(uri.toString()).openStream(), 4096))
