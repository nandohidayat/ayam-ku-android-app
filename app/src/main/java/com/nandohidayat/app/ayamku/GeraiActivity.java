package com.nandohidayat.app.ayamku;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GeraiActivity extends AppCompatActivity {
    ArrayList<Gerai> gerais;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_gerai);

        spinner = findViewById(R.id.list_gerai);

        getJSON("http://ayam-ku-nandohidayat.c9users.io/api/gerai.php");
    }

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String aVoid) {
                super.onPostExecute(aVoid);
                try {
                    loadIntoListView(aVoid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        gerais = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        String[] webchrz = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String kd_gerai = obj.getString("kd_gerai");
            String nama = obj.getString("nama");
            String phone = obj.getString("phone");
            String sms = obj.getString("sms");
            String latitude = obj.getString("latitude");
            String longitude = obj.getString("longitude");
            gerais.add(new Gerai(kd_gerai, nama, phone, sms, latitude, longitude));
        }

        String[] namas = new String[gerais.size()];
        for(int i = 0; i < gerais.size(); i++) {
            namas[i] = gerais.get(i).nama;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namas);

        spinner.setAdapter(adapter);
    }
}
