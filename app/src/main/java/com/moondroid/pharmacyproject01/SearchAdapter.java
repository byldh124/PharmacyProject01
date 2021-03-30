package com.moondroid.pharmacyproject01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> {

    Context context;
    ArrayList<ItemVO> itemVOS;

    public SearchAdapter(Context context, ArrayList<ItemVO> itemVOS) {
        this.context = context;
        this.itemVOS = itemVOS;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.layout_search_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(itemVOS.get(position).getName());
        holder.tvAddress.setText(itemVOS.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return itemVOS.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAddress;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_name_search);
            tvAddress = itemView.findViewById(R.id.item_address_search);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    ItemVO itemVO = itemVOS.get(pos);
                    Intent intent = ((SearchActivity)context).getIntent();
                    intent.putExtra("item", new Gson().toJson(itemVO));
                    ((SearchActivity)context).setResult(Activity.RESULT_OK, intent);
                    ((SearchActivity)context).finish();
                }
            });
        }
    }
}
