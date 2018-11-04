package com.example.nando.ayamku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends AppCompatActivity {
    String str_UserName, str_Password, str_RePassword, str_getID, str_getPass;
    EditText edt_UserName, edt_Password, edt_RePassword;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        str_getID = SplashActivity.sh.getString("name", null);
        edt_UserName = (EditText) findViewById(R.id.edt_userName);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        edt_RePassword = (EditText) findViewById(R.id.edt_Rpassword);
        btn_update = (Button) findViewById(R.id.btn_update);
        edt_UserName.setText(str_getID);

    }




    public void updateInfo(View v) {
        str_UserName = edt_UserName.getText().toString();
        str_Password = edt_Password.getText().toString();
        str_RePassword = edt_RePassword.getText().toString();

        if(str_UserName.length() == 0) {
            Toast.makeText(this, "Please enter your User Name", Toast.LENGTH_LONG).show();
        } else if(str_Password.contains(str_RePassword) != str_RePassword.contains(str_Password)) {
            Toast.makeText(this, "Confirm Password does not match", Toast.LENGTH_LONG).show();
        } else if(str_Password.length() == 0) {
            SplashActivity.editor.putString("name", str_UserName);
            SplashActivity.editor.commit();
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            startActivity(home);
        } else {
            SplashActivity.editor.putString("name", str_UserName);
            SplashActivity.editor.putString("password", str_Password);
            SplashActivity.editor.commit();
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            startActivity(home);
        }
    }
}
