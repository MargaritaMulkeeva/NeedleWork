package com.example.needlework.Discussions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.needlework.Adapters.DiscussionAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiscussions extends AppCompatActivity {

    ImageButton img_back;

    private DiscussionAdapter popularDiscussionAdapter;
    private List<DiscussionsResponseBody> mPopularDiscussions;
    private RecyclerView recyclerViewPopularDisc;

    RecyclerView rvDisc;
    String token;

    private ApiService service = ApiHandler.getmInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussions);

        img_back = findViewById(R.id.btn_back);
        recyclerViewPopularDisc = findViewById(R.id.rv_popular);

        token = getSharedPreferences(Constants.storageName, Context.MODE_PRIVATE).getString("token", "");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDiscussions.this, ChooseDiscussions.class);
                startActivity(intent);
            }
        });

        getDisc();
    }

    private void getDisc(){
        AsyncTask.execute(()->{
            service.getDiscussionsByCriterion("popular").enqueue(new Callback<GetDiscussionByCritetionResponseBody>() {
                @Override
                public void onResponse(Call<GetDiscussionByCritetionResponseBody> call, Response<GetDiscussionByCritetionResponseBody> response) {
                    if(response.isSuccessful()){
                        mPopularDiscussions = response.body().getDiscussions();
                        popularDiscussionAdapter = new DiscussionAdapter(mPopularDiscussions, AddDiscussions.this, new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(AddDiscussions.this, ChooseDiscussions.class);
                                intent.putExtra("discussionId", mPopularDiscussions.get(position).getId());
                                startActivity(intent);
                            }
                        },token);

                        LinearLayoutManager manager = new LinearLayoutManager(AddDiscussions.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewPopularDisc.setLayoutManager(manager);
                        recyclerViewPopularDisc.setAdapter(popularDiscussionAdapter);
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDiscussionByCritetionResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }
}