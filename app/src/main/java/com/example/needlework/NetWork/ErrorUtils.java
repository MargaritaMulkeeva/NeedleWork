package com.example.needlework.NetWork;

import com.example.needlework.NetWork.Models.ApiError;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ErrorUtils {
    static Retrofit retrofit;

    public static ApiError error(Response<?> response){
        Converter<ResponseBody, ApiError> converter =
                retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try{
            error = converter.convert(response.errorBody());
        }
        catch (Exception e){
            return new ApiError("Произлшла неизвестная ошибка. Попробуйте позже");
        }
        return error;
    }

}
