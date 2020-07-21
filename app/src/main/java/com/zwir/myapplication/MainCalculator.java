package com.zwir.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
public class MainCalculator extends AppCompatActivity {
    private Utils utils = new Utils();
    private TextView inputText;
    private TextView resultText;
    SwitchCompat toggle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        // using SharedPreferences
        sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);
        setUpMode(isDarkModeOn);
        toggle.setChecked(isDarkModeOn);
    }

    private void setupViews() {
        inputText = findViewById(R.id.inputTextView);
        resultText = findViewById(R.id.resualtTextView);
        findViewById(R.id.backspace).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClearClick(view);
                return true;
            }
        });
        toggle = findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setUpMode(isChecked);
                editor.putBoolean(
                        "isDarkModeOn", isChecked);
                editor.apply();
            }
        });
    }

    public void onClearClick(View view) {
        inputText.setText(null);
        resultText.setText(null);
    }

    public void onBackSpaceClick(View view) {
        String expression = inputText.getText().toString();
        try {
            if (expression.length() == 1)
                inputText.setText(null);
            else {
                inputText.setText(expression.substring(0, expression.length() - 1));
            }
        } catch (Exception e) {

        }
    }

    public void onNumberClick(View view) {
        Button b = (Button) view;
        inputText.append(b.getText());
    }

    public void onOperationClick(View view) {
        Button b = (Button) view;
        inputText.append(b.getText());
    }

    public void onEqualsButtonClick(View view) {
        try {
            String expression = inputText.getText().toString();
            double result = utils.getResult(expression);
            resultText.setText(String.valueOf(result));
        } catch (Exception e) {
        }
    }

    void setUpMode(boolean checkMode) {
        if (checkMode) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }
    }
}
