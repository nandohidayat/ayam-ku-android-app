package com.nandohidayat.app.ayamku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends Activity implements View.OnClickListener {
    String str_UserName, str_Password, str_getID, str_getPass;

    EditText edt_UName, edt_Password;

    Button login;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /* fetching the data from shared preference in order to make user login */
        /* data are saved in application through SplashActivity */
        /* only name and password is sufficient to make login */

        login = (Button) findViewById(R.id.btn_login);
        edt_UName = (EditText) findViewById(R.id.edt_userName);
        edt_Password = (EditText) findViewById(R.id.edt_password);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        str_UserName = edt_UName.getText().toString();
        str_Password = edt_Password.getText().toString();

        /* make edittext condition for empty, input etc match */
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder builder = HttpUrl.parse("http://ayam-ku-nandohidayat.c9users.io/api/user/read.php").newBuilder();
            builder.addQueryParameter("username", edt_UName.getText().toString());
            builder.addQueryParameter("password", edt_Password.getText().toString());

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

                                    if(jsonObject.getInt("hak_akses") == 1) {
                                        SplashActivity.editor.putString("loginTest", "true");
                                        SplashActivity.editor.putString("name", jsonObject.getString("name"));
                                        SplashActivity.editor.putString("username", jsonObject.getString("user_id"));
                                        SplashActivity.editor.putString("password", jsonObject.getString("password"));
                                        SplashActivity.editor.commit();

                                        Toast.makeText(getApplicationContext(),
                                                "Logging in...", Toast.LENGTH_LONG).show();

                                        Intent sendToLogout = new Intent(getApplicationContext(),
                                                MainActivity.class);

                                        startActivity(sendToLogout);
                                    } else {
                                        Toast.makeText(Login.this, "Username or Password Incorect", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(Login.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            } catch (IOException e) {
                                Toast.makeText(Login.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            Toast.makeText(Login.this, e.toString(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Login.this,
                    LoginAndRegister.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
