package com.nandohidayat.app.ayamku;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CheckoutList extends ArrayAdapter<CheckoutModel> {
    Activity context;
    List<CheckoutModel> models;

    public CheckoutList(Activity context, List<CheckoutModel> models) {
        super(context, R.layout.list_checkout, models);
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_checkout, null, true);

        TextView nametext = listViewItem.findViewById(R.id.name);
        TextView pricetext = listViewItem.findViewById(R.id.price);
        TextView manytext = listViewItem.findViewById(R.id.many);

        CheckoutModel model = models.get(position);
        nametext.setText(model.name);
        pricetext.setText("Rp " + model.price);
        manytext.setText("x " + model.many);

        return listViewItem;
    }
}
