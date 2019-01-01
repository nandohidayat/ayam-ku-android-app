package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder builder = HttpUrl.parse("http://ayam-ku-nandohidayat.c9users.io/api/jual/read.php").newBuilder();
            builder.addQueryParameter("username", SplashActivity.sh.getString("username", null));
            builder.addQueryParameter("total", price + "");
            builder.addQueryParameter("bayar", pay + "");

            String url = builder.build().toString();

            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                try {
                                    String data = response.body().string();
                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String status = jsonObject.getString("status");

                                    if(status.contains("success")) {
                                        Toast.makeText(Checkout.this, "Payment Successfull", Toast.LENGTH_LONG).show();
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonObject.getString("pdf")));
                                        startActivity(browserIntent);
                                    } else {
                                        Toast.makeText(Checkout.this, "Payment Failed, Please Try Again", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        SplashActivity.editor.putFloat("price", 0.0f);
        SplashActivity.editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
