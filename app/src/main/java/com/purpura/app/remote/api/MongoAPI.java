package com.purpura.app.remote.api;

import com.purpura.app.model.Adress;
import com.purpura.app.model.Company;
import com.purpura.app.model.PixKey;
import com.purpura.app.model.Residue;
import com.purpura.app.remote.util.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Api("https://mongodb-api-purpura.onrender.com/")
public interface MongoAPI {

    //GET
    @GET("empresa/{cnpj}")
    Call<Company> getCompanyByCNPJ(@Path("cnpj") String cnpj);
    @GET("empresa/{cnpj}/residuo/{id}")
    Call<Residue> getResidueById(@Path("cnpj") String cnpj, @Path("id") String id);
    @GET("empresa/{cnpj}/pix/{id}")
    Call<PixKey> getPixKeyById(@Path("cnpj") String cnpj, @Path("pix") String pix);
    @GET("empresa/{cnpj}/endereco/{id}")
    Call<Adress> getAdressById(@Path("cnpj") String cnpj, @Path("id") String id);
    @GET("empresa/{cnpj}/residuo/all")
    Call<List<Residue>> getAllResiduos(@Path("cnpj") String cnpj);
    @GET("empresa/{cnpj}/pix/all")
    Call<List<PixKey>> getAllPixKeys(@Path("cnpj") String cnpj);
    @GET("empresa/{cnpj}/endereco/all")
    Call<List<Adress>> getAllEnderecos(@Path("cnpj") String cnpj);
    @GET("empresa/search")
    Call<List<Company>> searchCompany(@Query("cnpj") String cnpj);
    @GET("empresa/all")
    Call<List<Company>> getAllCompanies();

    //POST
    @POST("empresa/")
    Call<Company> createCompany(@Body Company company);
    @POST("empresa/{cnpj}/residuo")
    Call<Residue> createResidue(@Path("cnpj") String cnpj, @Body Residue residue);
    @POST("empresa/{cnpj}/pix")
    Call<PixKey> createPixKey(@Path("cnpj") String cnpj, @Body PixKey pixKey);
    @POST("empresa/{cnpj}/endereco")
    Call<Adress> createAdress(@Path("cnpj") String cnpj, @Body Adress adress);

    //PUT
    @PUT("empresa/{cnpj}")
    Call<Company> updateCompany(@Path("cnpj") String cnpj, @Body Company company);
    @PUT("empresa/{cnpj}/residuo/{id}")
    Call<Residue> updateResidue(@Path("cnpj") String cnpj, @Path("id") String id, @Body Residue residue);
    @PUT("empresa/{cnpj}/pix/{id}")
    Call<PixKey> updatePixKey(@Path("cnpj") String cnpj, @Path("id") String id, @Body PixKey pixKey);
    @PUT("empresa/{cnpj}/endereco/{id}")
    Call<Adress> updateAdress(@Path("cnpj") String cnpj, @Path("id") String id, @Body Adress adress);

    //DELETE
    @DELETE("empresa/{cnpj}")
    Call<Company> deleteCompany(@Path("cnpj") String cnpj);
    @DELETE("empresa/{cnpj}/residuo/{id}")
    Call<Residue> deleteResidue(@Path("cnpj") String cnpj, @Path("id") String id);
    @DELETE("empresa/{cnpj}/pix/{id}")
    Call<PixKey> deletePixKey(@Path("cnpj") String cnpj, @Path("id") String id);
    @DELETE("empresa/{cnpj}/endereco/{id}")
    Call<Adress> deleteAdress(@Path("cnpj") String cnpj, @Path("id") String id);

}