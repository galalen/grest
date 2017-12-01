package com.galalen.restclient.utils


import com.galalen.restclient.net.Request
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import java.io.InputStream
import javax.swing.JTextArea
import javax.swing.SwingWorker

class Worker(private val method: String,
                    private val url: String,
                    private val params: Map<String, Any>? = null,
                    private val view: TextArea?):
        SwingWorker<InputStream?, Any>() {

    override fun doInBackground(): InputStream? {
        return when (method) {
            Request.Method.POST.toString() -> Request.post(url, params)
            Request.Method.GET.toString() -> Request.get(url, params)
            else -> null
        }
    }

    override fun done() {
        try {
            view!!.text = Request.parseInputStream(get()!!)
        } catch (e: Exception) {
            view!!.text = "Something went wrong\n$e.message"
        }
    }

}