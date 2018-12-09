package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public static String str_login_test;

    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sh = getSharedPreferences("myprefe", 0);
        editor = sh.edit();

        str_login_test = sh.getString("loginTest", null);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        SplashActivity.editor.putFloat("price", 0);
        SplashActivity.editor.commit();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /*
                 * if user login test is true on oncreate then redirect the user
                 * to result page
                 */

                if (str_login_test != null
                        && !str_login_test.toString().trim().equals("")) {
                    Intent send = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(send);
                }
                /*
                 * if user login test is false on oncreate then redirect the
                 * user to login & registration page
                 */
                else {

                    Intent send = new Intent(getApplicationContext(),
                            LoginAndRegister.class);
                    startActivity(send);

                }
            }

        }, 3000);
    }
}
