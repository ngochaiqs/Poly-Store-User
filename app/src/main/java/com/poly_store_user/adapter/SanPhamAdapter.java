package com.poly_store_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly_store_user.Interface.ItemClickListener;
import com.poly_store_user.R;
import com.poly_store_user.activity.ChiTietActivity;
import com.poly_store_user.model.EventBus.SuaXoaEvent;
import com.poly_store_user.model.SanPham;
import com.poly_store_user.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.MyViewHolder> {
    Context context;
    List<SanPham> sanPhamList;

    public SanPhamAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPham.getGiaSP())) + " đ");
        if (sanPham.getHinhAnhSP().contains("http")){
            Glide.with(context).load(sanPham.getHinhAnhSP()).into(holder.imgSP);
        }else{
            String hinh = Utils.BASE_URL+"images/"+sanPham.getHinhAnhSP();
            Glide.with(context).load(hinh).into(holder.imgSP);
        }



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void conClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick) {
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", sanPham);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(sanPham));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView txtTenSP, txtGiaSP;
        ImageView imgSP;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.tvTenSanPham);
            txtGiaSP = itemView.findViewById(R.id.tvGiaSanPham);
            imgSP = itemView.findViewById(R.id.imgSanPham);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickListener.conClick(v, getAdapterPosition(), true);
                    return false;
                }
            });
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.conClick(view, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Chọn thao tác");
            contextMenu.add(0, 0, getAdapterPosition(), "Xóa");
            contextMenu.add(0, 1, getAdapterPosition(), "Sửa");
        }
    }
}

