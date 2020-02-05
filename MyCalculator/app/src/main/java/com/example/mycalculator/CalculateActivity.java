package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalculateActivity extends AppCompatActivity {

    private String previousNumber = "";
    private String currentNumber = "";
    private String expression = "";
    private boolean showCurrent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculate);

        //add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbCalculate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_calculate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.miCopy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("number", previousNumber);;
                if (!expression.equals("")){
                    ClipData.newPlainText("number", currentNumber);
                }
                clipboard.setPrimaryClip(clip);
                return true;
        }

        return true;
    }

    public void onClickHandler(View view){
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        switch(view.getId()){
            case R.id.btnOne:
                setNumber("1");
                break;
            case R.id.btnTwo:
                setNumber("2");
                break;
            case R.id.btnThree:
                setNumber("3");
                break;
            case R.id.btnFour:
                setNumber("4");
                break;
            case R.id.btnFive:
                setNumber("5");
                break;
            case R.id.btnSix:
                setNumber("6");
                break;
            case R.id.btnSeven:
                setNumber("7");
                break;
            case R.id.btnEight:
                setNumber("8");
                break;
            case R.id.btnNine:
                setNumber("9");
                break;
            case R.id.btnZero:
                setNumber("0");
                break;
            case R.id.btnMultiply:
                setExpression("*");
                break;
            case R.id.btnDivision:
                setExpression("/");
                break;
            case R.id.btnMinus:
                setExpression("-");
                break;
            case R.id.btnPlus:
                setExpression("+");
                break;
            case R.id.btnDot:
                setNumber(".");
                break;
            case R.id.btnEqual:
                calculate();
                break;

        }
    }

    private void setNumber(String number){
        if (expression.equals("")){
            if (!(previousNumber.contains(".") && number.equals("."))){
                previousNumber += number;
                updateDisplay(previousNumber);
            }
        }else{
            if (!(currentNumber.contains(".") && number.equals("."))){
                currentNumber += number;
                updateDisplay(currentNumber);
            }
        }
    }

    private void setExpression(String expression){
        if (!this.expression.equals("")){
            calculate();
        }
        this.expression = expression;
    }

    private void calculate(){
        double result = 0;
        switch (expression){
            case "*":
                result = Double.parseDouble(previousNumber) * Double.parseDouble(currentNumber);
                break;
            case "/":
                result = Double.parseDouble(previousNumber) / Double.parseDouble(currentNumber);
                break;
            case "-":
                result = Double.parseDouble(previousNumber) - Double.parseDouble(currentNumber);
                break;
            case "+":
                result = Double.parseDouble(previousNumber) + Double.parseDouble(currentNumber);
                break;
        }
        previousNumber = ""+result;
        currentNumber = "";
        expression = "";
        updateDisplay(""+result);
    }

    private void updateDisplay(String str){
        ((TextView) findViewById(R.id.tvDisplay)).setText(str);
    }
}
