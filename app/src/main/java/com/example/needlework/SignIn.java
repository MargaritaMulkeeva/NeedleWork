package com.example.needlework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.needlework.Discussions.AddDiscussions;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.user.GetUserResponseBody;
import com.example.needlework.NetWork.Models.user.LoginRequestBody;
import com.example.needlework.NetWork.Models.user.LoginResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.common.Constants;

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

        editor = getSharedPreferences(Constants.storageName, MODE_PRIVATE).edit();

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
        service.doLogin(getLoginData()).enqueue(new Callback<LoginResponseBody>() {
            @Override
            public void onResponse(Call<LoginResponseBody> call, Response<LoginResponseBody> response) {
                if(response.isSuccessful()){
                    editor.putString("token", response.body().getToken()).apply();
                    service.getUser(response.body().getToken()).enqueue(new Callback<GetUserResponseBody>() {
                        @Override
                        public void onResponse(Call<GetUserResponseBody> call, Response<GetUserResponseBody> response) {
                            if (response.isSuccessful()){
                                editor.putLong("userId", response.body().getUser().getId()).apply();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetUserResponseBody> call, Throwable t) {

                        }
                    });
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    String message = ErrorUtils.error(response).getError();
                    new AlertDialog.Builder(SignIn.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseBody> call, Throwable t) {
                new AlertDialog.Builder(SignIn.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }
    private LoginRequestBody getLoginData(){
        return new LoginRequestBody(et_login.getText().toString(), et_password.getText().toString());
    }
}