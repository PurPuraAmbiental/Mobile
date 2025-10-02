package com.purpura.app.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purpura.app.model.Company;

public class AdreesAdapter extends RecyclerView.Adapter<AdreesAdapter.AdreesViewHolder> {

    private Company company;

    public CompanyAdapter(Company company) {
        this.company = company;
    }


    @NonNull
    @Override
    public CompanyAdapter.CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.CompanyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder{

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

}
