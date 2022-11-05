package com.poly_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store.Interface.ItemClickListener;
import com.poly_store.R;
import com.poly_store.model.DonHang;
import com.poly_store.model.EventBus.DonHangEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyviewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang){
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng: " + donHang.getMaDH());
        holder.trangthai.setText(trangThaiDon(donHang.getTrangThai()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //chi tiet adapter
        ChiTietAdapter chiTietAdapter = new ChiTietAdapter(context, donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chiTietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener() {
            @Override
            public void conClick(View view, int pos, boolean isLongClick) {
                if (isLongClick){
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));

                }
            }
        });
    }
    private String trangThaiDon(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lý";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang, trangthai;
        RecyclerView reChitiet;
        ItemClickListener listener;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.madonhang);
            trangthai = itemView.findViewById(R.id.tinhtrang);
            reChitiet = itemView.findViewById(R.id.recycleview_chitiet);
            itemView.setOnLongClickListener(this);

        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.conClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}