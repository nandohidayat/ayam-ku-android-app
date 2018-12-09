package com.nandohidayat.app.ayamku;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AyamAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private AyamAdapter ayamAdapter;
    private ArrayList<Ayam> ayams;
    private TextView totalPrice;
    private float price;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ayamAdapter = new AyamAdapter(ayams, this, this);

        GridLayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(ayamAdapter);
        ayamAdapter.setClickListener(this);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        price = SplashActivity.sh.getFloat("price", 0.0f);
        totalPrice.setText("Rp " + Double.toString(price));
    }

    @Override
    public void onClick(View view, int position) {
        Cursor cursor =
                this.getContentResolver().query(Uri.parse(
                        Contract.CONTENT_URI.toString()), null, null, null, "ASC");
        cursor.moveToPosition(position);
        double ayamPrice = cursor.getDouble(cursor.getColumnIndex(Contract.AyamList.KEY_PRICE));
        switch (view.getId()) {
            case R.id.ayamImage :
                price = price + (float)ayamPrice;
                SplashActivity.editor.putFloat("price", price);
                SplashActivity.editor.commit();
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                totalPrice.setText("Rp " + decimalFormat.format(price));
                return;
            default:
                SplashActivity.editor.putInt("idDesc", position);
                Intent ayamDesc = new Intent(getApplicationContext(), AyamDesc.class);
                ayamDesc.putExtra("ayam", position);
                startActivity(ayamDesc);
                return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MainActivity.this,
                    SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_call:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", "+6283842327765", null));
                startActivity(phoneIntent);
                return true;

            case R.id.action_sms:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + "+6283842327765"));
                sendIntent.putExtra("sms-body", "Hi, i want to make an order");
                startActivity(sendIntent);
                return true;
            case R.id.action_map:
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", -6.982248, 110.409244);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                return true;
            case R.id.action_setting:
                Intent updateIntent = new Intent(this, Update.class);
                startActivity(updateIntent);
                return true;
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "You have successfully logout",
                        Toast.LENGTH_LONG).show();
                SplashActivity.editor.remove("loginTest");

                SplashActivity.editor.commit();

                Intent sendToLoginandRegistration = new Intent(getApplicationContext(),
                        Login.class);

                startActivity(sendToLoginandRegistration);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void checkout(View view) {
        Intent intent = new Intent(getApplicationContext(), Checkout.class);
        startActivity(intent);
    }
}
