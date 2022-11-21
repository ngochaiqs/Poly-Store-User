package com.poly_store_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poly_store_user.R;
import com.poly_store_user.model.LoaiSP;

import java.util.List;

public class LoaiSPAdapter extends BaseAdapter {
    List<LoaiSP> loaiSPList;
    Context context;

    public LoaiSPAdapter( Context context, List<LoaiSP> loaiSPList) {
        this.loaiSPList = loaiSPList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaiSPList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisp, null);
            viewHolder.txtTenLoaiSP = view.findViewById(R.id.tvLoaiSP);
            viewHolder.imgLoaiSP = view.findViewById(R.id.imgLoaiSP);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.txtTenLoaiSP.setText(loaiSPList.get(i).getTenLoai());
        Glide.with(context).load(loaiSPList.get(i).getHinhAnh()).into(viewHolder.imgLoaiSP);
        return view;
    }
}
