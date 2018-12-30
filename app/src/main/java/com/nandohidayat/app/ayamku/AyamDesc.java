package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class AyamDesc extends AppCompatActivity {
    ImageView ayamImage;
    TextView ayamName, ayamPrice, ayamDesc;
    Ayam ayam;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_item);

        intent = getIntent();
        Gson gson = new Gson();
        ayam = gson.fromJson(intent.getStringExtra("ayam"), Ayam.class);

        ayamImage = (ImageView) findViewById(R.id.ayamImage);
        ayamName = (TextView) findViewById(R.id.ayamName);
        ayamDesc = (TextView) findViewById(R.id.ayamDesc);
        ayamPrice = (TextView) findViewById(R.id.ayamPrice);

        new DownloadImageTask(ayamImage).execute(ayam.getImage());
        ayamName.setText(ayam.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        ayamPrice.setText("Rp " + decimalFormat.format(ayam.getPrice()));
        ayamDesc.setText(ayam.getDesc());
    }
}
