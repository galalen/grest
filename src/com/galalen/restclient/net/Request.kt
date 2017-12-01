package com.galalen.restclient.net

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object Request {

    enum class Method {
        GET, POST, PUT, DELETE,
    }

    private fun Map<String, Any>.contactParams(): String {
        var stringParams = ""
        for ((k,v) in entries) {
            stringParams += "$k=$v&"
        }
        return stringParams.substring(0, stringParams.length - 1)
    }

    fun post(url: String, params: Map<String, Any>? = null): InputStream? {
        val mURL = URL(url)
        val http = mURL.openConnection() as HttpURLConnection
//        http.setRequestProperty("", "")
        http.requestMethod = Method.POST.toString()
        http.doOutput = true
        http.doInput = true

        if (params != null && params.isNotEmpty()) {
            var writer: OutputStreamWriter? = null
            try {
                writer = OutputStreamWriter(http.outputStream)
                val stringParams = params.contactParams()
                writer.write(stringParams)
                writer.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                writer?.close()
            }
        }

        return http.inputStream
    }

    fun get(url: String, params: Map<String, Any>? = null): InputStream? {
        var getUrl = url
        if (params != null && params.isNotEmpty()) {
            getUrl = url + "?" + params.contactParams()
        }

        val mURL = URL(getUrl)
        val http = mURL.openConnection() as HttpURLConnection
        http.requestMethod = Method.GET.toString()
        return http.inputStream
    }

    fun parseInputStream(inputStream: InputStream): String {
        var content = ""

        val br = BufferedReader(InputStreamReader(inputStream))
        var line = br.readLine()
        while (line != null) {
            content += line
            line = br.readLine()
        }
        return content
    }
}