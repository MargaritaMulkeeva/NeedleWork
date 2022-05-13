package com.example.needlework.NetWork.Service;

import com.example.needlework.NetWork.Models.CategoriesOfPatternResponse;
import com.example.needlework.NetWork.Models.LoginBody;
import com.example.needlework.NetWork.Models.LoginResponse;
import com.example.needlework.NetWork.Models.RegistrationBody;
import com.example.needlework.NetWork.Models.RegistrationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("users/sign-in")
    Call<LoginResponse> doLogin(@Body LoginBody registerBody);

    @POST("users/sign-up")
    Call<RegistrationResponse> doRegistration(@Body RegistrationBody registrationBody);

    @GET("patterns-type/get-all")
    Call<List<CategoriesOfPatternResponse>> getCategoriesOfPattern();

    @GET("discussions-type/get-all")
    Call<List<CategoriesOfPatternResponse>> getCategoriesOfDiscussion();
}
