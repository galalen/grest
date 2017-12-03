package com.galalen.restclient.controllers

import com.galalen.restclient.utils.RequestTask
import com.jfoenix.controls.JFXTextField
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Paint
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

class MainController : Initializable {

    @FXML
    private lateinit var bSend: Button

    @FXML
    private lateinit var bAddParam: Button

    @FXML
    private var cbRequestMethod: ChoiceBox<*>? = null

    @FXML
    private var tfUrl: TextField? = null

    @FXML
    private var resultArea: TextArea? = null

    @FXML
    private var paramsBox: VBox? = null

    @FXML
    private var contentType: Label? = null

    private val params: HashMap<String, Any>
    private val paramsRef: HashMap<JFXTextField, JFXTextField>

    init {
        params = HashMap()
        paramsRef = HashMap()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        cbRequestMethod?.items = FXCollections.observableArrayList("GET", "POST", "PUT", "DELETE")
        cbRequestMethod?.value = "GET"
        
    }

    fun sendRequest() {

        var url = tfUrl!!.text.trim().toLowerCase()

        if (url.isEmpty()) {
            resultArea?.text = "URL field can't be empty"
        } else {

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url
            }

            if (paramsRef.size > 0) {
                for ((k, v) in paramsRef) {
                    params.put(k.text.trim().toLowerCase(), v.text.trim().toLowerCase())
                }
            }

            val method = cbRequestMethod!!.selectionModel.selectedItem.toString()
            RequestTask(method = method, url = url, params = params, view = resultArea).run()
        }
    }

    fun addParam() {
        val key = JFXTextField()
        key.padding = Insets(0.0, 20.0, 0.0, 20.0)
        key.focusColor = Paint.valueOf("#00bcd4")
        key.promptText = "Key"
        key.prefHeight = 25.0
        key.prefWidth = 240.0

        val value = JFXTextField()
        value.padding = Insets(0.0, 20.0, 0.0, 20.0)
        value.promptText = "Value"
        value.focusColor = Paint.valueOf("#00bcd4")
        value.prefHeight = 25.0
        value.prefWidth = 240.0

        paramsRef.put(key, value)
        updateParamsBox()
    }

    private fun updateParamsBox() {
        paramsBox!!.children.clear()
        for ((k, v) in paramsRef) {
            val box = HBox()
            box.padding = Insets(10.0, 0.0, 10.0, 0.0)
            box.prefHeight = 46.0
            box.prefWidth = 520.0
            box.children.addAll(k, v)
            paramsBox!!.children.add(box)
        }

    }


}