package com.purpura.app.remote.service;

import android.content.Context;
import android.widget.Toast;

import com.purpura.app.model.Adress;
import com.purpura.app.model.PixKey;
import com.purpura.app.remote.api.MongoAPI;
import com.purpura.app.remote.util.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MongoService {

    private MongoAPI mongoAPI;

    public MongoService() {
        mongoAPI = new RetrofitService<>(MongoAPI.class).getService();
    }

    //Create - POST
    public void createAdress(String cnpj,Adress adress, Context context){

        mongoAPI.createAdress(cnpj, adress);
        Call<Adress> call = mongoAPI.createAdress(cnpj, adress);

        call.enqueue(new Callback<Adress>() {
            @Override
            public void onResponse(Call<Adress> call, Response<Adress> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Endereço criado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Adress> call, Throwable t) {
                Toast.makeText(context, "Erro ao criar endereço", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createPixKey(String cnpj, PixKey pixKey, Context context){

        mongoAPI.createPixKey(cnpj, pixKey);
        Call<PixKey> call = mongoAPI.createPixKey(cnpj, pixKey);

        call.enqueue(new Callback<PixKey>() {
            @Override
            public void onResponse(Call<PixKey> call, Response<PixKey> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Chave criada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PixKey> call, Throwable t) {
                Toast.makeText(context, "Erro ao criar chave", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
