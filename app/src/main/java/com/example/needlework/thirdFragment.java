package com.example.needlework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.SignOutBody;
import com.example.needlework.NetWork.Service.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class thirdFragment extends Fragment {

    ApiService service = ApiHandler.getmInstance().getService();
    private SharedPreferences.Editor editor;

    public thirdFragment() {
    }

    public static thirdFragment newInstance() {
        thirdFragment fragment = new thirdFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        editor = getContext().getSharedPreferences("needleWorkApp", getContext().MODE_PRIVATE).edit();

        View view = inflater.inflate(R.layout.fragment_third, container, false);
        Button exitButton = view.findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignOut();
            }
        });
        return view;
    }

    private void doSignOut() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String token = getContext().getSharedPreferences("needleWorkApp", getContext().MODE_PRIVATE).getString("token", "");
                service.doSignOut(new SignOutBody(token)).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            editor.remove("token").apply();
                            editor.commit();
                            Toast.makeText(getContext(), "Пользователь вышел", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), SignIn.class);
                            startActivity(intent);
                        }else if (response.code()==400){
                            String message = ErrorUtils.error(response).getError();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}