package com.example.laba2_4;

public interface View {
    void setOnClick(OnClick onClick);
    void setOnClick_right(OnClick_right onClick_right);

    void drawField(Model.Cell[][] field);
    void drawWin();
    void drawLoss();

    javafx.scene.Parent getRoot();

    interface OnClick {
        void onClick(int row, int col);
    }

    interface OnClick_right {
        void onClick_right(int row, int col);
    }
}