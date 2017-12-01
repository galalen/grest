package com.galalen.restclient.utils

import com.galalen.restclient.net.Request
import javafx.concurrent.Task
import javafx.scene.control.TextArea
import java.io.InputStream

class RequestTask(private val method: String,
                  private val url: String,
                  private val params: Map<String, Any>? = null,
                  private val view: TextArea?) :
        Task<InputStream?>() {

    override fun call(): InputStream? {
        return when (method) {
            Request.Method.POST.toString() -> Request.post(url, params)
            Request.Method.GET.toString() -> Request.get(url, params)
            else -> null
        }
    }

    override fun succeeded() {
        view?.text = Request.parseInputStream(get()!!)
    }

}