package com.example.jimmy.testspoontacularmk3.CustomViewGroup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.jimmy.testspoontacularmk3.R;

public class SearchEditText extends RelativeLayout {

    private final static String TAG = "ClearableEditText";
    private LayoutInflater inflater = null;
    private EditText edit_text;
    private Button btn_clear;
    private View mCustomView;

    public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SearchEditText(Context context) {
        super(context);
        initViews();
    }

    private void initViews() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.searchedittext, this, true);
        edit_text = findViewById(R.id.ingredients_edit_text);
        btn_clear = findViewById(R.id.clearable_button_clear);
        btn_clear.setVisibility(RelativeLayout.VISIBLE);
        clearText();
        showHideClearButton();
    }

    private void clearText() {
        btn_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text.setText("");
            }
        });
    }

    private void showHideClearButton() {
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    btn_clear.setVisibility(RelativeLayout.VISIBLE);
                else
                    btn_clear.setVisibility(RelativeLayout.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public Editable getText() {
        Editable text = edit_text.getText();
        return text;
    }
}
