package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class AyamDesc extends AppCompatActivity {
    ImageView ayamImage;
    TextView ayamName, ayamPrice, ayamDesc;
    int ayamId;
    Ayam ayam;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_item);

        ayamId = SplashActivity.sh.getInt("idDesc", 0);
        intent = getIntent();
        ayam = (Ayam) intent.getSerializableExtra("ayam");

        ayamImage = (ImageView) findViewById(R.id.ayamImage);
        ayamName = (TextView) findViewById(R.id.ayamName);
        ayamDesc = (TextView) findViewById(R.id.ayamDesc);
        ayamPrice = (TextView) findViewById(R.id.ayamPrice);

        AssetManager manager = this.getAssets();
        InputStream inputStream;
        try {
            inputStream = manager.open(ayam.getImage()+".jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            System.out.print(ayam.getImage()+".jpg");
            ayamImage.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ayamName.setText(ayam.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        ayamPrice.setText("Rp " + decimalFormat.format(ayam.getPrice()));
        ayamDesc.setText(ayam.getDesc());
    }
}
