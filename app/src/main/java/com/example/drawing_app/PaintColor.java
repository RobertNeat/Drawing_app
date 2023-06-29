package com.example.drawing_app;

import android.graphics.Color;

public enum PaintColor {

    CYAN(Color.rgb(0, 255, 255)),
    MAGENTA(Color.rgb(255, 0, 255)),
    YELLOW(Color.rgb(255, 255, 0)),
    GRAY(Color.rgb(170,170,170));

    public final int value;

    PaintColor(int value) {
        this.value = value;
    }

}
