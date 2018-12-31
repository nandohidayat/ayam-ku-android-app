package com.nandohidayat.app.ayamku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Registration extends Activity implements View.OnClickListener {
    Button register;

    String str_Name, str_User, str_Password, str_RePassword;

    EditText edt_Name, edt_User, edt_Password, edt_RePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration);

        register = (Button) findViewById(R.id.btn_register);
        edt_Name = (EditText) findViewById(R.id.edt_Rname);
        edt_User = (EditText) findViewById(R.id.edt_Ruser);
        edt_Password = (EditText) findViewById(R.id.edt_Rpassword);
        edt_RePassword = (EditText) findViewById(R.id.edt_RRepassword);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        str_Name = edt_Name.getText().toString();
        str_User = edt_User.getText().toString();
        str_Password = edt_Password.getText().toString();
        str_RePassword = edt_RePassword.getText().toString();

        if (str_Name.length() == 0 & str_User.length() == 0 & str_Password.length() == 0
                & str_RePassword.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "All fields are mandatory to fill", Toast.LENGTH_LONG)
                    .show();
        } else if (str_Name.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your Name",
                    Toast.LENGTH_LONG).show();
        } else if (str_User.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your Username",
                    Toast.LENGTH_LONG).show();
        } else if (str_Password.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please enter your Password", Toast.LENGTH_LONG).show();
        } else if (str_RePassword.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please Re-enter your Password", Toast.LENGTH_LONG).show();
        } else if (str_Password.contains(str_RePassword) != str_RePassword
                .contains(str_Password)) {
            Toast.makeText(getApplicationContext(),
                    "Confirm Password does not match", Toast.LENGTH_LONG)
                    .show();
        } else {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder builder = HttpUrl.parse("http://ayam-ku-nandohidayat.c9users.io/api/user/create.php").newBuilder();
                builder.addQueryParameter("name", str_Name);
                builder.addQueryParameter("username", str_User);
                builder.addQueryParameter("password", str_Password);

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
                                    if(response.body().string().contains("success")) {
                                        Toast.makeText(Registration.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_LONG).show();
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

            Intent sendtoLogin = new Intent(getApplicationContext(),
                    LoginAndRegister.class);

            startActivity(sendtoLogin);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Registration.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
