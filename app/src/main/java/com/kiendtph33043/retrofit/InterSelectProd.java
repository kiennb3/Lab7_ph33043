package com.kiendtph33043.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterSelectProd {
    @GET("get_all_product.php")
    Call<SvrResponseProd> getProd();
}
