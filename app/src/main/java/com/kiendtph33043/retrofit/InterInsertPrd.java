package com.kiendtph33043.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterInsertPrd {
    @FormUrlEncoded
    @POST("insert_prd.php")
    Call<SvrResponPrd> insertPrd(
            @Field("name") String name,
            @Field("price") String price,
            @Field("description") String description
    );
}



