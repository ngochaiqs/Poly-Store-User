package com.poly_store_user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.poly_store_user.R;
import com.poly_store_user.model.Item;
import com.poly_store_user.utils.Utils;

import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;


    public ChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        Log.d("==/ Thông tin đơn hàng:", new Gson().toJson(itemList));
        holder.txtten.setText(item.getTenSP() + "");
        holder.txtsoluong.setText("Số lượng: " + item.getSoLuong());
        if (item.getHinhAnhSP().contains("http")){
            Glide.with(context).load(item.getHinhAnhSP()).into(holder.imageChitet);
        }else{
            String hinh = Utils.BASE_URL+"images/"+item.getHinhAnhSP();
            Glide.with(context).load(hinh).into(holder.imageChitet);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageChitet;
        TextView txtten, txtsoluong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageChitet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
