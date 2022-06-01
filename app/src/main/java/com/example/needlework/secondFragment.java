package com.example.needlework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.DiscussionAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.Discussions.AddDiscussions;
import com.example.needlework.Discussions.ChooseDiscussions;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.GetAllKnittingPatterns;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;
import com.example.needlework.common.Constants;

import java.nio.file.AtomicMoveNotSupportedException;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class secondFragment extends Fragment {
    private CategoriesAdapter categoriesAdapter;
    private List<CategoriesOfPatternResponseBody> mCategories;
    private RecyclerView recyclerView;

    private DiscussionAdapter popularDiscussionAdapter;
    private List<DiscussionsResponseBody> mPopularDiscussions;
    private RecyclerView recyclerViewPopularDisc;

    private DiscussionAdapter newDiscussionAdapter;
    private List<DiscussionsResponseBody> mNewDiscussions;
    private RecyclerView recyclerViewNewDisc;

    private DiscussionAdapter allDiscussionsAdapter;
    private List<DiscussionsResponseBody> mAllDisc;
    private RecyclerView recyclerViewAllDisc;

    Button btn_createDisc, btnSearch;

    EditText etSearch;

    String token;

    private ApiService service = ApiHandler.getmInstance().getService();

    public secondFragment() {
    }

    public static secondFragment newInstance(String param1, String param2) {
        secondFragment fragment = new secondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = view.findViewById(R.id.rv_categoriesDisc);
        recyclerViewPopularDisc = view.findViewById(R.id.rv_popularDisc);
        //recyclerViewNewDisc = view.findViewById(R.id.rv_newDisc);
        recyclerViewAllDisc = view.findViewById(R.id.rv_AllDisc);
        etSearch = view.findViewById(R.id.etSearch);

        btn_createDisc = view.findViewById(R.id.btn_createDisc);
        btnSearch = view.findViewById(R.id.btnSearch);
        token = getContext().getSharedPreferences(Constants.storageName, Context.MODE_PRIVATE).getString("token", "");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch = view.findViewById(R.id.etSearch);
                if(etSearch.getText().toString().equals("")){
                    getAllDisc();
                } else {
                    searchItem(etSearch.getText().toString());
                }
            }
        });

        btn_createDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddDiscussions.class));
            }
        });

        getCategories();
        getPopularDiscussions();
        getAllDisc();
        return view;
    }

    public void searchItem(String textToSearch){
        for (Iterator<DiscussionsResponseBody> it = mAllDisc.iterator(); it.hasNext();) {
            if (!it.next().getTheme().toLowerCase().contains(textToSearch))
                it.remove();
        }
        allDiscussionsAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        AsyncTask.execute(()->{
            service.getCategoriesOfDiscussion().enqueue(new Callback<List<CategoriesOfPatternResponseBody>>() {
                @Override
                public void onResponse(Call<List<CategoriesOfPatternResponseBody>> call, Response<List<CategoriesOfPatternResponseBody>> response) {
                    if(response.isSuccessful()){
                        mCategories = response.body();
                        categoriesAdapter = new CategoriesAdapter(mCategories, getContext());

                        SnapHelper snapHelper = new PagerSnapHelper();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(categoriesAdapter);
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }
                @Override
                public void onFailure(Call<List<CategoriesOfPatternResponseBody>> call, Throwable t) {
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void getAllDisc(){
        AsyncTask.execute(()->{
            service.getAllDisc().enqueue(new Callback<List<DiscussionsResponseBody>>() {
                @Override
                public void onResponse(Call<List<DiscussionsResponseBody>> call, Response<List<DiscussionsResponseBody>> response) {
                    if(response.isSuccessful()){
                        mAllDisc = response.body();
                        allDiscussionsAdapter = new DiscussionAdapter(mAllDisc, getContext(), new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(getContext(), ChooseDiscussions.class);
                                intent.putExtra("discussionId", mAllDisc.get(position).getId());
                                startActivity(intent);
                            }
                        }, token);

                        SnapHelper snapHelper = new PagerSnapHelper();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewAllDisc.setLayoutManager(manager);
                        recyclerViewAllDisc.setAdapter(allDiscussionsAdapter);
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DiscussionsResponseBody>> call, Throwable t) {
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void getPopularDiscussions(){
        AsyncTask.execute(()->{
            service.getDiscussionsByCriterion("popular").enqueue(new Callback<GetDiscussionByCritetionResponseBody>() {
                @Override
                public void onResponse(Call<GetDiscussionByCritetionResponseBody> call, Response<GetDiscussionByCritetionResponseBody> response) {
                    if(response.isSuccessful()){
                        mPopularDiscussions = response.body().getDiscussions();
                        popularDiscussionAdapter = new DiscussionAdapter(mPopularDiscussions, getContext(), new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(getContext(), ChooseDiscussions.class);
                                intent.putExtra("discussionId", mPopularDiscussions.get(position).getId());
                                startActivity(intent);
                            }
                        }, token);

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewPopularDisc.setLayoutManager(manager);
                        recyclerViewPopularDisc.setAdapter(popularDiscussionAdapter);
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDiscussionByCritetionResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void getNewDiscussions() {
        AsyncTask.execute(()->{
            service.getDiscussionsByCriterion("recent").enqueue(new Callback<GetDiscussionByCritetionResponseBody>() {
                @Override
                public void onResponse(Call<GetDiscussionByCritetionResponseBody> call, Response<GetDiscussionByCritetionResponseBody> response) {
                    if(response.isSuccessful()){
                        mNewDiscussions = response.body().getDiscussions();
                        newDiscussionAdapter = new DiscussionAdapter(mNewDiscussions, getContext(), new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(getContext(), ChooseDiscussions.class);
                                intent.putExtra("discussionId", mNewDiscussions.get(position).getId());
                                startActivity(intent);
                            }
                        }, token);

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewNewDisc.setLayoutManager(manager);
                        recyclerViewNewDisc.setAdapter(newDiscussionAdapter);
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDiscussionByCritetionResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }
}