package com.example.needlework;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.user.ChangeAvatarRequestBody;
import com.example.needlework.NetWork.Models.user.ChangeLoginRequestBody;
import com.example.needlework.NetWork.Models.user.ChangeNickNameRequestBody;
import com.example.needlework.NetWork.Models.user.ChangePasswordRequestBody;
import com.example.needlework.NetWork.Models.user.GetUserResponseBody;
import com.example.needlework.NetWork.Models.user.SignOutRequestBody;
import com.example.needlework.NetWork.Models.user.UserResponseBody;
import com.example.needlework.NetWork.Models.user.UserUpdateResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Profile.UserBookMark;
import com.example.needlework.Profile.UserDiscussions;
import com.example.needlework.common.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class thirdFragment extends Fragment {

    ApiService service = ApiHandler.getmInstance().getService();
    private SharedPreferences storage;

    private static UserResponseBody currentUserData;

    private ImageView avatar;
    private TextView nickNameText;
    private EditText nickNameEdit;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private EditText loginEdit;

    ImageButton btnBookMark, btnDisc;
    Button editAvatarButton;

    private final int Pick_image = 1;

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
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        avatar = view.findViewById(R.id.avatarImageView);
        nickNameText = view.findViewById(R.id.tv_nickname);
        nickNameEdit = view.findViewById(R.id.et_newNickname);
        oldPassword = view.findViewById(R.id.et_oldPassword);
        newPassword = view.findViewById(R.id.et_newPassword);
        confirmPassword = view.findViewById(R.id.et_repeatPassword);
        loginEdit = view.findViewById(R.id.et_newLogin);

        btnDisc = view.findViewById(R.id.btn_bookmark);
        btnDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserDiscussions.class);
                startActivity(intent);
            }
        });
        btnBookMark = view.findViewById(R.id.btn_userDisc);
        btnBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserBookMark.class);
                startActivity(intent);
            }
        });

        storage = getContext().getSharedPreferences(Constants.storageName, getContext().MODE_PRIVATE);

        getUserInfo();

        Button exitButton = view.findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignOut();
            }
        });

        editAvatarButton = view.findViewById(R.id.btn_changePhoto);
        editAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar();
            }
        });
        Button editNicknameButton = view.findViewById(R.id.btn_changeNickname);
        editNicknameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNickname();
            }
        });
        Button editPasswordButton = view.findViewById(R.id.btn_changePassword);
        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        Button editLoginButton = view.findViewById(R.id.btn_changeLogin);
        editLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLogin();
            }
        });

        return view;
    }

    private void getUserInfo() {
        String token = storage.getString("token", "");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getUser(token).enqueue(new Callback<GetUserResponseBody>() {
                    @Override
                    public void onResponse(Call<GetUserResponseBody> call, Response<GetUserResponseBody> response) {
                        if (response.isSuccessful()) {
                            currentUserData = response.body().getUser();
                            Picasso.with(getContext())
                                    .load(currentUserData.getAvatar())
                                    .placeholder(R.drawable.avatar_placeholder)
                                    .into(avatar);
                            nickNameText.setText(currentUserData.getNickName());
                            nickNameEdit.setText(currentUserData.getNickName());
                            loginEdit.setText(currentUserData.getLogin());
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUserResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void changeAvatar() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    service.changeAvatar(new ChangeAvatarRequestBody(currentUserData.getId(), imageUri.toString())).enqueue(new Callback<UserUpdateResponseBody>() {
                        @Override
                        public void onResponse(Call<UserUpdateResponseBody> call, Response<UserUpdateResponseBody> response) {
                            if (response.isSuccessful()) {
                                avatar.setImageURI(imageUri);
                                new AlertDialog.Builder(getContext()).setTitle("??????????????").setMessage(response.body().getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                            } else {
                                String message = ErrorUtils.error(response).getError();
                                new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserUpdateResponseBody> call, Throwable t) {
                            new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    });
                }
            });
        }
    }

    private void changeNickname() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.changeNickName(new ChangeNickNameRequestBody(currentUserData.getId(), nickNameEdit.getText().toString())).enqueue(new Callback<UserUpdateResponseBody>() {
                    @Override
                    public void onResponse(Call<UserUpdateResponseBody> call, Response<UserUpdateResponseBody> response) {
                        if (response.isSuccessful()) {
                            nickNameText.setText(nickNameEdit.getText().toString());
                            new AlertDialog.Builder(getContext()).setTitle("??????????????").setMessage(response.body().getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserUpdateResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void changePassword() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.changePassword(new ChangePasswordRequestBody(
                        currentUserData.getId(),
                        oldPassword.getText().toString(),
                        newPassword.getText().toString(),
                        confirmPassword.getText().toString()))
                        .enqueue(new Callback<UserUpdateResponseBody>() {
                            @Override
                            public void onResponse(Call<UserUpdateResponseBody> call, Response<UserUpdateResponseBody> response) {
                                if (response.isSuccessful()) {
                                    new AlertDialog.Builder(getContext()).setTitle("??????????????").setMessage(response.body().getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                                } else {
                                    String message = ErrorUtils.error(response).getError();
                                    new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserUpdateResponseBody> call, Throwable t) {
                                new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                            }
                        });
            }
        });
    }

    private void changeLogin() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.changeLogin(new ChangeLoginRequestBody(
                        currentUserData.getId(),
                        loginEdit.getText().toString()))
                        .enqueue(new Callback<UserUpdateResponseBody>() {
                            @Override
                            public void onResponse(Call<UserUpdateResponseBody> call, Response<UserUpdateResponseBody> response) {
                                if (response.isSuccessful()) {
                                    new AlertDialog.Builder(getContext()).setTitle("??????????????").setMessage(response.body().getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                                } else {
                                    String message = ErrorUtils.error(response).getError();
                                    new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserUpdateResponseBody> call, Throwable t) {
                                new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                            }
                        });
            }
        });
    }

    private void doSignOut() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String token = storage.getString("token", "");
                service.doSignOut(new SignOutRequestBody(token)).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            SharedPreferences.Editor editor = storage.edit();
                            editor.remove("token").apply();

                            Intent intent = new Intent(getContext(), Authorization.class);

                            startActivity(intent);
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(getContext()).setTitle("????????????").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
            }
        });
    }
}