package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

public class Update extends AppCompatActivity {
    String str_Name, str_UserName, str_Password, str_RePassword;
    EditText edt_Name, edt_UserName, edt_Password, edt_RePassword;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        str_Name = SplashActivity.sh.getString("name", null);
        str_UserName = SplashActivity.sh.getString("username", null);

        edt_Name = (EditText) findViewById(R.id.edt_name);
        edt_UserName = (EditText) findViewById(R.id.edt_username);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        edt_RePassword = (EditText) findViewById(R.id.edt_rpassword);

        btn_update = (Button) findViewById(R.id.btn_update);

        edt_Name.setText(str_Name);
        edt_UserName.setText(str_UserName);
    }

    public void updateInfo(View v) {
        str_Name = edt_Name.getText().toString();
        str_UserName = edt_UserName.getText().toString();
        str_Password = edt_Password.getText().toString();
        str_RePassword = edt_RePassword.getText().toString();

        if(str_Name.length() == 0) {
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_LONG).show();
        } else if(str_UserName.length() == 0) {
            Toast.makeText(this, "Please enter your Username", Toast.LENGTH_LONG).show();
        } else if(str_Password.contains(str_RePassword) != str_RePassword.contains(str_Password)) {
            Toast.makeText(this, "Confirm Password does not match", Toast.LENGTH_LONG).show();
        } else {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder builder = HttpUrl.parse("http://ayam-ku-nandohidayat.c9users.io/api/user/update.php").newBuilder();
                builder.addQueryParameter("name", str_Name);
                builder.addQueryParameter("username", str_UserName);
                if(str_Password.length() != 0) {
                    builder.addQueryParameter("password", str_Password);
                }

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
                                    if (response.body().string().equals("success")) {
                                        Toast.makeText(Update.this, "Saved", Toast.LENGTH_LONG);
                                        SplashActivity.editor.putString("name", str_Name);
                                        SplashActivity.editor.putString("username", str_UserName);
                                        SplashActivity.editor.putString("password", str_Password);
                                        SplashActivity.editor.commit();
                                    } else {
                                        Toast.makeText(Update.this, "Failed to Update", Toast.LENGTH_LONG);
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

            SplashActivity.editor.putString("name", str_UserName);
            SplashActivity.editor.putString("password", str_Password);
            SplashActivity.editor.commit();
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            startActivity(home);
        }
    }
}
