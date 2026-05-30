package com.example.laba2_4;

public interface View {
    void setOnClick(OnClick onClick);
    void setOnClick_right(OnClick_right onClick_right);
    void drawField(Model.Cell[][] field);
    void drawWin();
    void drawLoss();
    Object getRoot();

    interface OnClick {
        void onClick(int x, int y);
    }

    interface OnClick_right {
        void onClick_right(int x, int y);
    }
}
