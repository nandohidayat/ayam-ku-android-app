package com.example.nando.ayamku;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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


        addData();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ayamAdapter = new AyamAdapter(ayams, this, this);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
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
        final Ayam ayam = ayams.get(position);
        switch (view.getId()) {
            case R.id.ayamImage :
                price = price + (float)ayam.getPrice();
                SplashActivity.editor.putFloat("price", price);
                SplashActivity.editor.commit();
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                totalPrice.setText("Rp " + decimalFormat.format(price));
                return;
            default:
                SplashActivity.editor.putInt("idDesc", position);
                Intent ayamDesc = new Intent(getApplicationContext(), AyamDesc.class);
                ayamDesc.putExtra("ayam", ayam);
                startActivity(ayamDesc);
                return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void addData() {
        ayams = new ArrayList<>();

        ayams.add(new Ayam("ayam0", "Rusty Chicken Thighs", 23000.0, "Rave Review: \"Love this recipe! So easy and it's delicious without the hours of marinating. I'll toss this into my chicken and let it sit for 30 minutes then throw it on the BBQ, comes out great every time!\" — Rhonda Jay"));
        ayams.add(new Ayam("ayam1", "Shoyu Chicken", 25000.0, "Rave Review: \"We love this recipe. This is one I cook often for my family. Even my picky eaters can't get enough.\" — happysoule"));
        ayams.add(new Ayam("ayam2", "Chicken, Sausage", 30000.0, "Rave Review: \"Phenomenal. My meat-and-potatoes guy was practically licking his plate. Love the char on the potatoes, tasted even better the next day. Will definitely be making this again. So easy, and only had to dirty one dish.\" — Danielle Davis"));
        ayams.add(new Ayam("ayam3", "Peanut Curry Chicken", 31000.0, "Rave Review: \"Excellent as is. Smooth and creamy. Nice balance of heat and sweet.\" — Tim"));
        ayams.add(new Ayam("ayam4", "Baked Apricot Chicken", 26000.0, "Rave Review: \"Amazing recipe! Quick, easy and tastes so good. The sauce is so good drizzled over the white rice.\" — Jen Gambill"));
        ayams.add(new Ayam("ayam5", "Baked Teriyaki Chicken", 25000.0, "Rave Review: \"Made exactly like recipe states and it is excellent! Did double the sauce. Highly recommend!\" — Holly"));
        ayams.add(new Ayam("ayam6", "Greek Lemon Chicken", 33000.0, "Rave Review: \"This smelled so good while baking! You know it's good when your picky 13 yr old boy tells you it's the best chicken he's ever had!!\" — WestCoastMom"));
        ayams.add(new Ayam("ayam7", "Honey-Garlic Chicken", 34000.0, "Rave Review: \"Easy to make and uses pantry staples. Always a very good thing! I doubled the chicken thighs (but not the rest of the ingredients) and put half in the freezer for a quick meal when I need it.\" — Paula"));
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
