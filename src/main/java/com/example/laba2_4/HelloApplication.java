package com.example.laba2_4;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    static final int CELL_COUNT = 10;
    static final int CELL_SIZE = 20;
    static final int MINES = 5;
    static final boolean FX_CANVAS = false;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("laba4");
        View view;
        if (FX_CANVAS) {
            view = new FxView();
            stage.setScene(new Scene((Parent) view.getRoot(), CELL_SIZE * CELL_COUNT +15, CELL_SIZE * CELL_COUNT +15));
        } else {
            Canvas canvas = new Canvas(CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT);
            Pane pane = new Pane(canvas);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            view = new CanvasView(canvas);
        }

        Model model = new Model();
        Controller controller = new Controller(view, model);

        stage.show();
    }
}