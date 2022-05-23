package com.example.needlework.NetWork.Service;

import com.example.needlework.NetWork.Models.CategoriesOfPatternResponse;
import com.example.needlework.NetWork.Models.DiscussionsResponse;
import com.example.needlework.NetWork.Models.GetKnittingPatternBody;
import com.example.needlework.NetWork.Models.KnittingPatternResponse;
import com.example.needlework.NetWork.Models.LoginBody;
import com.example.needlework.NetWork.Models.LoginResponse;
import com.example.needlework.NetWork.Models.RegistrationBody;
import com.example.needlework.NetWork.Models.RegistrationResponse;
import com.example.needlework.NetWork.Models.SignOutBody;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("users/sign-in")
    Call<LoginResponse> doLogin(@Body LoginBody registerBody);

    @POST("users/sign-up")
    Call<RegistrationResponse> doRegistration(@Body RegistrationBody registrationBody);

    @POST("users/sign-out")
    Call<ResponseBody> doSignOut(@Body SignOutBody body);

    @GET("patterns-type/get-all")
    Call<List<CategoriesOfPatternResponse>> getCategoriesOfPattern();

    @GET("discussions-type/get-all")
    Call<List<CategoriesOfPatternResponse>> getCategoriesOfDiscussion();

    @GET("knittingPatterns/get-popular")
    Call<List<KnittingPatternResponse>> getPopularKnittingPatterns();

    @GET("knittingPatterns/get-one")
    Call<KnittingPatternResponse> getKnittingPattern(@Body GetKnittingPatternBody body);

    @GET("discussions/by-criterion")
    Call<DiscussionsResponse> getDiscussions();
}
