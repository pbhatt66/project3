package com.example.rubank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * TransactionManagerMain class launches the Transaction Manager GUI.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class TransactionManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TransactionManagerMain.class.getResource("TransactionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Project 3 - Transaction Manager");
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(615);
        stage.setMinWidth(615);
    }

    public static void main(String[] args) {
        launch();
    }
}