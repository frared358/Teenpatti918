package com.guna.libcolorpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.Locale;

public class ColorPickerOval extends Dialog implements OvalColorsSelectedListener {

    private OnColorChangedListener mListener;
    private int mInitialColor;
    private static String mKey;

    public ColorPickerOval(Context context, OnColorChangedListener listener, String key, int initialColor) {
        super(context);
        mKey = key;
        mListener = listener;
        mInitialColor = initialColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.picker_oval);
        final ColorPickerOvalView view = (ColorPickerOvalView) findViewById(R.id.viewOval);

        final EditText etHexColor = (EditText) findViewById(R.id.etHexColor);
        final EditText etR = (EditText) findViewById(R.id.etR);
        final EditText etG = (EditText) findViewById(R.id.etG);
        final EditText etB = (EditText) findViewById(R.id.etB);

        RecyclerView recyclerViewColors = (RecyclerView) findViewById(R.id.recyclerViewColors);
        recyclerViewColors.setLayoutManager(new GridLayoutManager(getContext(), 2));
        OvalColorsAdapter adapter = new OvalColorsAdapter(getContext().getResources().getIntArray(R.array.colors), this);
        recyclerViewColors.setAdapter(adapter);

        final boolean[] isREditFromView = {false};
        final boolean[] isGEditFromView = {false};
        final boolean[] isBEditFromView = {false};

        OnColorChangedListener l = new OnColorChangedListener() {
            public void colorChanged(String key, int color) {
                mListener.colorChanged(key, color);
                dismiss();
            }

            @Override
            public void colorChanging(int color) {
                Log.v("App", color + "");
                isREditFromView[0] = true;
                isGEditFromView[0] = true;
                isBEditFromView[0] = true;
                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                etHexColor.setText(hexColor);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                etR.setText(String.format(Locale.getDefault(), "%d", r));
                etG.setText(String.format(Locale.getDefault(), "%d", g));
                etB.setText(String.format(Locale.getDefault(), "%d", b));
                Selection.setSelection(etR.getText(), etR.getText().toString().length());
            }
        };

        view.init(l, mInitialColor, mKey);


        etHexColor.setText("#");
        Selection.setSelection(etHexColor.getText(), etHexColor.getText().toString().length());

        etHexColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("#")) {
                    etHexColor.setText("#");
                    Selection.setSelection(etHexColor.getText(), etHexColor.getText().toString().length());
                }
                if (s.toString().length() == 7 || s.toString().length() == 9) {
                    Log.v("App", etHexColor.getText().toString());
                    int color = Color.parseColor(etHexColor.getText().toString());
                    view.setColor(color);
                    isREditFromView[0] = true;
                    isGEditFromView[0] = true;
                    isBEditFromView[0] = true;
                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);
                    etR.setText(String.format(Locale.getDefault(), "%d", r));
                    etG.setText(String.format(Locale.getDefault(), "%d", g));
                    etB.setText(String.format(Locale.getDefault(), "%d", b));
                }
            }
        });

        final int[] R = {0};
        final int[] G = {0};
        final int[] B = {0};
        etR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isREditFromView[0]) {
                    if (s.toString().length() == 0) {
                        etR.setText(String.format(Locale.getDefault(), "%d", 0));
                    } else {
                        R[0] = Integer.parseInt(s.toString());
                        if (s.toString().startsWith("0") && R[0] != 0) {
                            etR.setText(String.format(Locale.getDefault(), "%d", R[0]));
                        }
                        if (R[0] > 255) {
                            R[0] = 255;
                            etR.setText(String.format(Locale.getDefault(), "%d", 255));
                        }
                        int color = Color.rgb(R[0], G[0], B[0]);
                        view.setColor(color);
                        String hexColor = String.format("#%06X", (0xFFFFFF & color));
                        etHexColor.setText(hexColor);
                    }
                } else isREditFromView[0] = false;
                etR.setSelection(etR.getText().length());
            }
        });

        etG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    etG.setText(String.format(Locale.getDefault(), "%d", 0));
                } else if (!isGEditFromView[0]) {
                    if (!s.toString().equals("0")) {
                        G[0] = Integer.parseInt(s.toString());
                        if (s.toString().startsWith("0")) {
                            etG.setText(String.format(Locale.getDefault(), "%d", G[0]));
                        }
                    } else {
                        G[0] = 0;
                    }
                    if (G[0] > 255) {
                        G[0] = 255;
                        etG.setText(String.format(Locale.getDefault(), "%d", 255));
                    }
                    int color = Color.rgb(R[0], G[0], B[0]);
                    view.setColor(color);
                    String hexColor = String.format("#%06X", (0xFFFFFF & color));
                    etHexColor.setText(hexColor);
                } else isGEditFromView[0] = false;
                etG.setSelection(etG.getText().length());
            }
        });

        etB.addTextChangedListener(new TextWatcher() {
                                       @Override
                                       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                       }

                                       @Override
                                       public void onTextChanged(CharSequence s, int start, int before, int count) {

                                       }

                                       @Override
                                       public void afterTextChanged(Editable s) {
                                           if (s.toString().length() == 0) {
                                               etB.setText(String.format(Locale.getDefault(), "%d", 0));
                                           } else if (!isBEditFromView[0]) {
                                               if (!s.toString().equals("0")) {
                                                   B[0] = Integer.parseInt(s.toString());
                                                   if (s.toString().startsWith("0")) {
                                                       etB.setText(String.format(Locale.getDefault(), "%d", B[0]));
                                                   }
                                               } else {
                                                   B[0] = 0;
                                               }
                                               if (B[0] > 255) {
                                                   B[0] = 255;
                                                   etB.setText(String.format(Locale.getDefault(), "%d", 255));
                                               }
                                               int color = Color.rgb(R[0], G[0], B[0]);
                                               view.setColor(color);
                                               String hexColor = String.format("#%06X", (0xFFFFFF & color));
                                               etHexColor.setText(hexColor);
                                           } else isBEditFromView[0] = false;
                                           etB.setSelection(etB.getText().length());
                                       }
                                   }

        );

        setTitle("Pick a Color");
    }

    @Override
    public void onOvalColorSelected(int color) {
        mListener.colorChanged(mKey, color);
        dismiss();
    }
}
