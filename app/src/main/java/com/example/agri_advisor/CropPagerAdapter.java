package com.example.agri_advisor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CropPagerAdapter extends RecyclerView.Adapter<CropPagerAdapter.ViewHolder> {

    private Context context;
    private List<CropModel> cropList;

    public CropPagerAdapter(Context context, List<CropModel> cropList) {
        this.context = context;
        this.cropList = cropList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CropModel crop = cropList.get(position);

        holder.cropName.setText(crop.getCropName());
        Glide.with(context).load(crop.getImageUrl()).into(holder.cropImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CropDetailActivity.class);
            intent.putExtra("CROP_NAME", crop.getCropName());
            intent.putExtra("CROP_DESCRIPTION", crop.getDescription());
            intent.putExtra("CROP_IMAGE_URL", crop.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public void updateList(List<CropModel> newList) {
        cropList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cropName;
        ImageView cropImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cropName = itemView.findViewById(R.id.cropName);
            cropImage = itemView.findViewById(R.id.cropImage);
        }
    }
}
