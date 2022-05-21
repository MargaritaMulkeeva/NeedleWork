package com.example.needlework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.LoginBody;
import com.example.needlework.NetWork.Models.LoginResponse;
import com.example.needlework.NetWork.Service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {


    Button btn_signIn;

    EditText et_login, et_password;

    private static String TAG="SignIn";

    ApiService service = ApiHandler.getmInstance().getService();
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editor = getSharedPreferences("needleWorkApp", MODE_PRIVATE).edit();

        btn_signIn = findViewById(R.id.btn_signIn);
        et_login = findViewById(R.id.etLogin);
        et_password = findViewById(R.id.editTextTextPassword);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin(){
        service.doLogin(getLoginData()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    editor.putString("token", response.body().getToken()).apply();
                    editor.commit();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(response.code()==400){
                    String serverErrorMessage = ErrorUtils.error(response).getError();
                    Toast.makeText(getApplicationContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignIn.this, "Произошла неизвестная ошибка! Попробуйте позже", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private LoginBody getLoginData(){
        return new LoginBody(et_login.getText().toString(), et_password.getText().toString());
    }
}