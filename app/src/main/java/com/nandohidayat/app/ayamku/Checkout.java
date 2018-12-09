package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Checkout extends AppCompatActivity {
    EditText totalPrice, payAmount, change;
    double price, pay, changes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        price = SplashActivity.sh.getFloat("price", 0.0f);
        totalPrice = (EditText) findViewById(R.id.totalPrice);
        payAmount = (EditText) findViewById(R.id.payAmount);
        change = (EditText) findViewById(R.id.change);

        final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        totalPrice.setText("Rp " + decimalFormat.format(price));

        payAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(payAmount.getText().toString().length() == 0)
                    pay = 0;
                else
                    pay = Double.parseDouble(payAmount.getText().toString());
                changes = pay - price;
                change.setText("Rp " + decimalFormat.format(changes));
            }
        });
    }

    public void pay(View view) {
        SplashActivity.editor.putFloat("price", 0.0f);
        SplashActivity.editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Paid", Toast.LENGTH_LONG).show();
    }
}
