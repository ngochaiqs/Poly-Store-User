package com.poly_store_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly_store_user.Interface.ItemClickListener;
import com.poly_store_user.R;
import com.poly_store_user.activity.ChiTietActivity;
import com.poly_store_user.model.SanPham;
import com.poly_store_user.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class AoKhoacAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SanPham> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public AoKhoacAdapter(Context context, List<SanPham> array) {
        this.context = context;
        this.array = array;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aokhoac, parent, false);
            return new MyViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new loadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SanPham sanPham = array.get(position);
            myViewHolder.tensp.setText(sanPham.getTenSP().trim());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPham.getGiaSP())) + " đ");
//            myViewHolder.mota.setText(sanPham.getMoTa());
            if (sanPham.getHinhAnhSP().contains("http")){
                Glide.with(context).load(sanPham.getHinhAnhSP()).into(myViewHolder.hinhanh);
            }else{
                String hinh = Utils.BASE_URL+"images/"+sanPham.getHinhAnhSP();
                Glide.with(context).load(hinh).into(myViewHolder.hinhanh);
            }
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void conClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick){
                        //click
                        Intent intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chitiet",sanPham);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }else {
            loadingViewHolder loadingViewHolder = (loadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }
    @Override
    public int getItemViewType(int position){
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public static class loadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public loadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp, giasp, mota, idsp;
        ImageView hinhanh;

        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemak_ten);
            giasp = itemView.findViewById(R.id.itemak_gia);
//            mota = itemView.findViewById(R.id.itemak_mota);
            hinhanh = itemView.findViewById(R.id.itemak_image);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.conClick(view, getAdapterPosition(),false);
        }
    }
}
