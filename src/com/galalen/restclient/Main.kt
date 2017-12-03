package com.galalen.restclient

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main: Application() {

    override fun start(primaryStage: Stage?) {
        val parent = FXMLLoader.load<Parent>(Main::class.java.getResource("res/layout/main.fxml"))

        val theScene = Scene(parent, 1000.0, 700.0)
        primaryStage?.run {
            scene = theScene
            isResizable = false
            show()
        }

    }

    fun run() = launch(Main::class.java)

}

fun main(args: Array<String>) = Main().run()