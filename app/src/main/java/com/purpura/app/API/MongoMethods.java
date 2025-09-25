package com.purpura.app.API;

import android.content.Context;
import android.widget.Toast;

import com.purpura.app.model.Adress;
import com.purpura.app.model.Company;
import com.purpura.app.model.PixKey;
import com.purpura.app.model.Residue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MongoMethods {

    private String url = "https://mongodb-api-purpura.onrender.com/";

    private Retrofit retrofit;

    private MongoAPI mongoAPI;

    // Create - GET
    public Call<List<Adress>> getAllEnderecos(String cnpj){
        retrofit.create(MongoAPI.class);
        mongoAPI.getAllEnderecos(cnpj);
        Call<List<Adress>> call = mongoAPI.getAllEnderecos(cnpj);
        return call;
    }

    public Call<List<Company>> getAllCompanies(){
        retrofit.create(MongoAPI.class);
        mongoAPI.getAllCompanies();
        Call<List<Company>> call = mongoAPI.getAllCompanies();
        return call;
    }

    public Call<List<PixKey>> getAllPixKeys(String cnpj){
        retrofit.create(MongoAPI.class);
        mongoAPI.getAllPixKeys(cnpj);
        Call<List<PixKey>> call = mongoAPI.getAllPixKeys(cnpj);
        return call;
    }

    public Call<List<Residue>> getAllResiduos(String cnpj){
        retrofit.create(MongoAPI.class);
        mongoAPI.getAllResiduos(cnpj);
        Call<List<Residue>> call = mongoAPI.getAllResiduos(cnpj);
        return call;
    }

    public Call<List<Company>> searchCompany(String cnpj){
        retrofit.create(MongoAPI.class);
        mongoAPI.searchCompany(cnpj);
        Call<List<Company>> call = mongoAPI.searchCompany(cnpj);
        return call;
    }

    public Call<Company> getCompanyByCNPJ(String cnpj){
        retrofit.create(MongoAPI.class);
        mongoAPI.getCompanyByCNPJ(cnpj);
        Call<Company> call = mongoAPI.getCompanyByCNPJ(cnpj);
        return call;
    }

    public Call<Residue> getResidueById(String cnpj, String id){
        retrofit.create(MongoAPI.class);
        mongoAPI.getResidueById(cnpj, id);
        Call<Residue> call = mongoAPI.getResidueById(cnpj, id);
        return call;
    }

    public Call<PixKey> getPixKeyById(String cnpj, String id){
        retrofit.create(MongoAPI.class);
        mongoAPI.getPixKeyById(cnpj, id);
        Call<PixKey> call = mongoAPI.getPixKeyById(cnpj, id);
        return call;
    }
    public Call<Adress> getAdressById(String cnpj, String id){
        retrofit.create(MongoAPI.class);
        mongoAPI.getAdressById(cnpj, id);
        Call<Adress> call = mongoAPI.getAdressById(cnpj, id);
        return call;
    }

    // Create - POST
    public void createAdress(String cnpj, Adress adress, Context context){
        retrofit.create(MongoAPI.class);
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
    public void createCompany(String cnpj, Company company, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.createCompany(cnpj, company);
        Call<Company> call = mongoAPI.createCompany(cnpj, company);

        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Empresa criada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Toast.makeText(context, "Erro ao criar empresa", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void createPixKey(String cnpj, PixKey pixKey, Context context){
        retrofit.create(MongoAPI.class);
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
    public void createResidue(String cnpj, Residue residue, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.createResidue(cnpj, residue);
        Call<Residue> call = mongoAPI.createResidue(cnpj, residue);

        call.enqueue(new Callback<Residue>() {
            @Override
            public void onResponse(Call<Residue> call, Response<Residue> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Resíduo criado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Residue> call, Throwable t) {
                Toast.makeText(context, "Erro ao criar resíduo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Update - PUT
    public void updateAdress(String cnpj, String id, Adress adress, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.updateAdress(cnpj, id, adress);
        Call<Adress> call = mongoAPI.updateAdress(cnpj, id, adress);
        call.enqueue(new Callback<Adress>() {
            @Override
            public void onResponse(Call<Adress> call, Response<Adress> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Endereço atualizado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Adress> call, Throwable t) {
                Toast.makeText(context, "Erro ao atualizar endereço", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateCompany(String cnpj, Company company, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.updateCompany(cnpj, company);
        Call<Company> call = mongoAPI.updateCompany(cnpj, company);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Empresa atualizada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Toast.makeText(context, "Erro ao atualizar empresa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePixKey(String cnpj, String id, PixKey pixKey, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.updatePixKey(cnpj, id, pixKey);
        Call<PixKey> call = mongoAPI.updatePixKey(cnpj, id, pixKey);
        call.enqueue(new Callback<PixKey>() {
            @Override
            public void onResponse(Call<PixKey> call, Response<PixKey> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Chave atualizada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PixKey> call, Throwable t) {
                Toast.makeText(context, "Erro ao atualizar chave", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateResidue(String cnpj, String id, Residue residue, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.updateResidue(cnpj, id, residue);
        Call<Residue> call = mongoAPI.updateResidue(cnpj, id, residue);
        call.enqueue(new Callback<Residue>() {
            @Override
            public void onResponse(Call<Residue> call, Response<Residue> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Resíduo atualizado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Residue> call, Throwable t) {
                Toast.makeText(context, "Erro ao atualizar resíduo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Create - Delete

    public void deleteAdress(String cnpj, String id, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.deleteAdress(cnpj, id);
        Call<Adress> call = mongoAPI.deleteAdress(cnpj, id);
        call.enqueue(new Callback<Adress>() {
            @Override
            public void onResponse(Call<Adress> call, Response<Adress> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Endereço deletado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Adress> call, Throwable t) {
                Toast.makeText(context, "Erro ao deletar endereço", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCompany(String cnpj, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.deleteCompany(cnpj);
        Call<Company> call = mongoAPI.deleteCompany(cnpj);
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Empresa deletada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Toast.makeText(context, "Erro ao deletar empresa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletePixKey(String cnpj, String id, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.deletePixKey(cnpj, id);
        Call<PixKey> call = mongoAPI.deletePixKey(cnpj, id);
        call.enqueue(new Callback<PixKey>() {
            @Override
            public void onResponse(Call<PixKey> call, Response<PixKey> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Chave deletada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PixKey> call, Throwable t) {
                Toast.makeText(context, "Erro ao deletar chave", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteResidue(String cnpj, String id, Context context){
        retrofit.create(MongoAPI.class);
        mongoAPI.deleteResidue(cnpj, id);
        Call<Residue> call = mongoAPI.deleteResidue(cnpj, id);
        call.enqueue(new Callback<Residue>() {
            @Override
            public void onResponse(Call<Residue> call, Response<Residue> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Resíduo deletado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Residue> call, Throwable t) {
                Toast.makeText(context, "Erro ao deletar resíduo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}