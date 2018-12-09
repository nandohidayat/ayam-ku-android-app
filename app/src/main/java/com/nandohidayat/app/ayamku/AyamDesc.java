package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    Intent intent;
    String name, desc, image;
    double price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_item);

        Cursor cursor =
                this.getContentResolver().query(Uri.parse(
                        Contract.CONTENT_URI.toString()), null, null, null, "ASC");

        intent = getIntent();
        ayamId = intent.getIntExtra("ayam", 0);
        cursor.moveToPosition(ayamId);

        name = cursor.getString(cursor.getColumnIndex(Contract.AyamList.KEY_NAME));
        price = cursor.getDouble(cursor.getColumnIndex(Contract.AyamList.KEY_PRICE));
        desc = cursor.getString(cursor.getColumnIndex(Contract.AyamList.KEY_DESC));
        image = cursor.getString(cursor.getColumnIndex(Contract.AyamList.KEY_IMAGE));

        ayamImage = (ImageView) findViewById(R.id.ayamImage);
        ayamName = (TextView) findViewById(R.id.ayamName);
        ayamDesc = (TextView) findViewById(R.id.ayamDesc);
        ayamPrice = (TextView) findViewById(R.id.ayamPrice);

        ayamImage.setImageBitmap(BitmapFactory.decodeFile(image));
        ayamName.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        ayamPrice.setText("Rp " + decimalFormat.format(price));
        ayamDesc.setText(desc);
    }
}
