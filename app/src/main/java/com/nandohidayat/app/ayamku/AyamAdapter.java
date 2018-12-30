package com.nandohidayat.app.ayamku;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AyamAdapter extends RecyclerView.Adapter<AyamAdapter.AyamViewHolder> {
    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    private static String url = "https://ayam-ku-nandohidayat.c9users.io/api/barang.php";

    private ArrayList<Ayam> dataList;
    private Activity activity;
    private ItemClickListener clickListener;
    private Context context;
    public static final String TAG = AyamAdapter.class.getSimpleName();
    private Cursor mCursor = null;

    public AyamAdapter(ArrayList<Ayam> dataList, Activity activity, Context context) {
        this.dataList = dataList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public AyamAdapter.AyamViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        return new AyamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AyamAdapter.AyamViewHolder ayamViewHolder, int i) {
        Cursor cursor =
                context.getContentResolver().query(Uri.parse(
                        Contract.CONTENT_URI.toString()), null, null, null, "ASC");
        Log.d("Vewhol","Di view dah keisii");
//        String name;
//        double price;
//        String desc;
//        String image;
//
//        if(cursor != null) {
//            if(cursor.moveToPosition(i)) {
//                int indexName = cursor.getColumnIndex(Contract.AyamList.KEY_NAME);
//                int indexPrice = cursor.getColumnIndex(Contract.AyamList.KEY_PRICE);
//                int indexDesc = cursor.getColumnIndex(Contract.AyamList.KEY_DESC);
//                int indexImage = cursor.getColumnIndex(Contract.AyamList.KEY_IMAGE);
//
//                name = cursor.getString(indexName);
//                price = cursor.getDouble(indexPrice);
//                desc = cursor.getString(indexDesc);
//                image = cursor.getString(indexImage);
//
//                ayamViewHolder.ayamImage.setImageBitmap(BitmapFactory.decodeFile(image));
//                ayamViewHolder.ayamName.setText(name);
//                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//                ayamViewHolder.ayamPrice.setText("Rp " + decimalFormat.format(price));
//            } else {
//                ayamViewHolder.ayamName.setText("FUCK");
//            }
//        } else {
//            Log.e (TAG, "onBindViewHolder: Cursor is null.");
//        }

        new DownloadImageTask((ImageView) ayamViewHolder.ayamImage).execute(dataList.get(i).getImage());
        ayamViewHolder.ayamName.setText(dataList.get(i).getName());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        ayamViewHolder.ayamPrice.setText("Rp " + decimalFormat.format(dataList.get(i).getPrice()));

    }

    @Override
    public int getItemCount() {
//        Cursor cursor =
//                context.getContentResolver().query(
//                        Contract.ROW_COUNT_URI, new String[] {"count(*) AS count"},
//                        null, null, "ASC");
//        try {
//            cursor.moveToFirst();
//            int count = cursor.getInt(0);
//            cursor.close();
//            return count;
//        }
//        catch (Exception e){
//            Log.d(TAG, "EXCEPTION getItemCount: " + e);
//            return -1;
//        }
        return dataList.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class AyamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ayamImage;
        private TextView ayamName;
        private TextView ayamPrice;
        private CardView cardView;

        public AyamViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            ayamImage = (ImageView) view.findViewById(R.id.ayamImage);
            ayamName = (TextView) view.findViewById(R.id.ayamName);
            ayamPrice = (TextView) view.findViewById(R.id.ayamPrice);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            ayamImage.setOnClickListener(this);
            ayamName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
