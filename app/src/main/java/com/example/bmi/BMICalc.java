package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMICalc extends AppCompatActivity {

    private EditText heightInput;
    private EditText weightInput;
    private TextView bmiScore;
    private TextView bmiResult;
    private Button calculateBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i_calc);

        //Gömmer toppfältet
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animinerg av bmi-knapp
        final Animation animateButton = AnimationUtils.loadAnimation(this, R.anim.fade);

        //Skapa connetion till
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        bmiScore = findViewById(R.id.bmiScoreTextView);
        bmiResult = findViewById(R.id.bmiResultTextView);
        calculateBMI = findViewById(R.id.bmiCalcButton);

        //Koppling till TextWatcher-metod för att se till att anvädaren matat in två värden
        heightInput.addTextChangedListener(loginTextWatcher);
        weightInput.addTextChangedListener(loginTextWatcher);

        calculateBMI.setOnClickListener(new View.OnClickListener() {
            //Metod för animering i samband med användare klickar på knappen
            @Override
            public void onClick(View view) {
                view.startAnimation(animateButton);

                //Omvandla input till String
                double height = Double.parseDouble(heightInput.getText().toString());
                double weight = Double.parseDouble(weightInput.getText().toString());
                //Konvertera längden till rätt format
                double convertheight = height / 100;
                //Beräkna av BMI
                double totalBMI = weight / (convertheight*convertheight);
                //Avrunda BMI till två decimaler
                double roundedDecimal = Math.round(totalBMI * 100.0) / 100.0;

                String str = Double.toString(roundedDecimal);
                bmiScore.setText("Ditt BMI är " + str);

                //Meddela användaren vad BMI'n innebär
                if (roundedDecimal < 18.5) {
                    bmiResult.setText("Du beräknas vara underviktig");
                } else if (roundedDecimal < 25) {
                    bmiResult.setText("Du beräknas ha normal vikt");
                } else if (roundedDecimal < 30) {
                    bmiResult.setText("Du beräknas vara överviktig");
                } else
                    bmiResult.setText("Du beräknas ha fetma");

                //Tar ned tangentbordet efter knapptryck
                closeKeyboard();

            }
        });



    };

    //Ser till att BMI-knappen endast är klickbar om båda TextFields har inmatade värden
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String input1 = heightInput.getText().toString().trim();
            String input2 = weightInput.getText().toString().trim();
            calculateBMI.setEnabled(!input1.isEmpty() && !input2.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}