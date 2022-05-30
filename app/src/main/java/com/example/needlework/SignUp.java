package com.example.needlework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.needlework.Discussions.AddDiscussions;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.user.RegistrationRequestBody;
import com.example.needlework.NetWork.Models.user.RegistrationResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.common.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText et_login,et_nickname,et_password,et_confirm;
    Button btn_signUp;
    private static final String TAG = "Registration";

    ApiService service = ApiHandler.getmInstance().getService();
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editor = getSharedPreferences(Constants.storageName, MODE_PRIVATE).edit();

        et_login = findViewById(R.id.etLogin);
        et_nickname = findViewById(R.id.etNickName);
        et_password = findViewById(R.id.etPassword);
        et_confirm = findViewById(R.id.etRepeatPassword);

        btn_signUp = findViewById(R.id.btn_signUp);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegistration();
            }
        });
    }

    private void doRegistration(){
        AsyncTask.execute(()->{
            service.doRegistration(getRegistationData()).enqueue(new Callback<RegistrationResponseBody>() {
                @Override
                public void onResponse(Call<RegistrationResponseBody> call, Response<RegistrationResponseBody> response) {
                    if(response.isSuccessful()) {
                        editor.putString("token", response.body().getToken()).apply();
                        editor.putLong("userId", response.body().getUser().getId()).apply();
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(SignUp.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(SignUp.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(SignUp.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    public RegistrationRequestBody getRegistationData(){
        return new RegistrationRequestBody(et_nickname.getText().toString(), et_login.getText().toString(), et_password.getText().toString(), et_confirm.getText().toString());
    }
}