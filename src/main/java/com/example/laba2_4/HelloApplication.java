package com.example.laba2_4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    static final int CELL_COUNT = 10;
    static final int CELL_SIZE = 20;
    static final int MINES = 5;

    static final boolean FX_CANVAS = false;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Minesweeper (Laba 4)");
        View view = FX_CANVAS ? new FxView() : new CanvasView();

        Scene scene = new Scene(view.getRoot());
        stage.setScene(scene);
        stage.setResizable(false);

        Model model = new Model();
        Controller controller = new Controller(view, model);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}