package com.poly_store_user.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly_store_user.Interface.IImageClickListenner;
import com.poly_store_user.R;
import com.poly_store_user.model.EventBus.TinhTongEvent;
import com.poly_store_user.model.GioHang;
import com.poly_store_user.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new  MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTenspGH());
        holder.item_giohang_soluong.setText((gioHang.getSoluongGH() + ""));
        if (gioHang.getHinhspGH().contains("http")){
            Glide.with(context).load(gioHang.getHinhspGH()).into(holder.item_giohang_image);
        }else{
            String hinh = Utils.BASE_URL+"images/"+gioHang.getHinhspGH();
            Glide.with(context).load(hinh).into(holder.item_giohang_image);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiaspGH())) + " đ");
        long gia = gioHang.getSoluongGH() * gioHang.getGiaspGH();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia) + " đ");
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b){
                    Utils.mangmuahang.add(gioHang);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else{
                    for (int i = 0; i < Utils.mangmuahang.size(); i++){
                        if (Utils.mangmuahang.get(i).getMaspGH() == gioHang.getMaspGH()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });

        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG","onImageClick:"+pos+"..."+giatri);
                if (giatri == 1 ) {
                    if (gioHangList.get(pos).getSoluongGH() > 1){
                    int soluongmoi = gioHangList.get(pos).getSoluongGH()-1;
                    gioHangList.get(pos).setSoluongGH(soluongmoi);

                    holder.item_giohang_soluong.setText((gioHangList.get(pos).getSoluongGH() + ""));
                    long gia = gioHangList.get(pos).getSoluongGH() * gioHangList.get(pos).getGiaspGH();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia) + " đ");
                    EventBus.getDefault().postSticky(new TinhTongEvent());

                    }else if (gioHangList.get(pos).getSoluongGH() ==1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này không?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if(giatri == 2){
                if (gioHangList.get(pos).getSoluongGH() < 11){
                int soluongmoi = gioHangList.get(pos).getSoluongGH()+1;
                gioHangList.get(pos).setSoluongGH(soluongmoi);
                }

              }
                holder.item_giohang_soluong.setText((gioHangList.get(pos).getSoluongGH() + ""));
                long gia = gioHangList.get(pos).getSoluongGH() * gioHangList.get(pos).getGiaspGH();
                holder.item_giohang_giasp2.setText(decimalFormat.format(gia) + " đ");
                EventBus.getDefault().postSticky(new TinhTongEvent());

            }


         });



    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        IImageClickListenner listenner;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById((R.id.item_giohang_gia));
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById((R.id.item_giohang_giasp2));
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);

            //event click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);


        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view ==imgtru){
            listenner.onImageClick(view,getAdapterPosition(),1);
            //tru1
        }else if (view ==imgcong){
                // 2 cong
            listenner.onImageClick(view, getAdapterPosition(),2);

        }
        }
    }
}
