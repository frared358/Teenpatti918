package com.guna.libcolorpicker;

public interface OnColorChangedListener {
    void colorChanged(String key, int color);

    void colorChanging(int color);
}
