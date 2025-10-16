package com.purpura.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.Residue;
import com.purpura.app.remote.service.MongoService;

import java.util.ArrayList;
import java.util.List;

public class ResiduesAdapter extends RecyclerView.Adapter<ResiduesAdapter.ResidueViewHolder> {

    private List<Residue> products;
    private final Methods methods = new Methods();
    private final MongoService mongoService = new MongoService();

    public ResiduesAdapter(List<Residue> products) {
        this.products = products != null ? products : new ArrayList<>();
    }

    @NonNull
    @Override
    public ResidueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_products_card, parent, false);
        return new ResidueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResidueViewHolder holder, int position) {
        Residue residue = products.get(position);

        Glide.with(holder.residueImage.getContext())
                .load(residue.getUrlFoto())
                .into(holder.residueImage);

        holder.residueName.setText(residue.getNome());

        holder.editResidueButton.setOnClickListener(v -> {

        });

        holder.deleteResidueButton.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public void updateList(List<Residue> newProducts) {
        this.products = newProducts != null ? newProducts : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ResidueViewHolder extends RecyclerView.ViewHolder {

        ImageView residueImage;
        TextView residueName;
        ImageView editResidueButton;
        ImageView deleteResidueButton;

        public ResidueViewHolder(@NonNull View itemView) {
            super(itemView);
            residueImage = itemView.findViewById(R.id.productCardProductImage);
            residueName = itemView.findViewById(R.id.productCardProductName);
            editResidueButton = itemView.findViewById(R.id.productCardEditResidueButton);
            deleteResidueButton = itemView.findViewById(R.id.productCardDeleteResidueButton);
        }
    }
}
