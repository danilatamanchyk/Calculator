package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText display;

    private StringBuilder stringBuilder = new StringBuilder();
    private boolean twoNumbers = false;
    private double finishNumber;
    private String currentNumber;
    private String currentText;
    private boolean number = false;
    private boolean operator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
    }

    public void onClickNumber(View view) {
        number = true;
        currentNumber = ((Button) view).getText().toString();
        stringBuilder.append(currentNumber);
        currentText = display.getText().toString();
        display.setText(currentText + currentNumber);
    }

    public void onClickOperator(View view) {
        if (number == true){
            if (twoNumbers == true) {
                finishNumber = evaluateExpression(stringBuilder);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(finishNumber);
                display.setText(Double.toString(finishNumber));
                stringBuilder.append(((Button) view).getText().toString());
                twoNumbers = false;
            } else {
                stringBuilder.append(((Button) view).getText().toString());
                twoNumbers = true;
            }
            display.append(((Button) view).getText().toString());
            number = false;
            operator = true;
        }
    }

    public void onClickEqual(View view) {
        if (operator == true&&number == true){
            finishNumber = evaluateExpression(stringBuilder);
            twoNumbers = false;
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append(finishNumber);
            display.setText(stringBuilder.toString());
        }
    }

    public void onClickCE(View view) {
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            display.setText(stringBuilder.toString());
        }
    }


    public static double evaluateExpression(StringBuilder expression) {
        char[] operators = {'+', '-', '*', '/'};
        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<Character> operatorsList = new ArrayList<>();

        int startPos = 0;
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            for (char op : operators) {
                if (currentChar == op) {
                    double number = Double.parseDouble(expression.substring(startPos, i));
                    numbers.add(number);
                    operatorsList.add(currentChar);
                    startPos = i + 1;
                    break;
                }
            }
        }

        // Добавляем последнее число после последнего оператора
        double lastNumber = Double.parseDouble(expression.substring(startPos));
        numbers.add(lastNumber);

        // Выполняем операции в порядке, заданном операторами
        double result = numbers.get(0);
        for (int i = 0; i < operatorsList.size(); i++) {
            char operator = operatorsList.get(i);
            double nextNumber = numbers.get(i + 1);

            switch (operator) {
                case '+':
                    result += nextNumber;
                    break;
                case '-':
                    result -= nextNumber;
                    break;
                case '*':
                    result *= nextNumber;
                    break;
                case '/':
                    if (nextNumber != 0) {
                        result /= nextNumber;
                    } else {
                        return 0.0; // Деление на ноль
                    }
                    break;
                default:
                    return 0.0; // Неизвестный оператор
            }
        }
        return result;
    }
}

    