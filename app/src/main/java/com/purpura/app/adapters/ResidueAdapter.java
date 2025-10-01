package com.purpura.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purpura.app.R;
import com.purpura.app.adapters.residues.ResiduesAdapter;
import com.purpura.app.model.Residue;

import java.util.List;

public class ResidueAdapter extends RecyclerView.Adapter<ResidueAdapter.ResidueViewHolder> {


    private List<Residue> residues;
    public ResidueAdapter(List<Residue> residues) {
        this.residues = residues;
    }


    @NonNull
    @Override
    public ResidueAdapter.ResidueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_products_card, parent, false);
        ResidueAdapter.ResidueViewHolder holder = new ResidueAdapter.ResidueViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResidueViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ResidueViewHolder extends RecyclerView.ViewHolder {
        public ResidueViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
