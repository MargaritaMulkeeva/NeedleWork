package com.example.needlework;

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
import android.widget.Toast;

import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.DiscussionAdapter;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionRequestBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class secondFragment extends Fragment {
    private CategoriesAdapter categoriesAdapter;
    private List<CategoriesOfPatternResponseBody> mCategories;
    private RecyclerView recyclerView;

    private DiscussionAdapter discussionAdapter;
    private List<DiscussionsResponseBody> mDisc;
    private RecyclerView recyclerViewDisc;

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
        getCategories();
        getDisc();
        return view;
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
                    else  if(response.code()==400){
                        String error = ErrorUtils.error(response).getError();
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Не удалось вывести категории обсуждений", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CategoriesOfPatternResponseBody>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void getDisc(){
        AsyncTask.execute(()->{
            service.getDiscussionsByCritetion(new GetDiscussionByCritetionRequestBody("popular")).enqueue(new Callback<GetDiscussionByCritetionResponseBody>() {
                @Override
                public void onResponse(Call<GetDiscussionByCritetionResponseBody> call, Response<GetDiscussionByCritetionResponseBody> response) {
                    if(response.isSuccessful()){
                        mDisc = (List<DiscussionsResponseBody>) response.body().getDiscussions();
                        discussionAdapter = new DiscussionAdapter(mDisc, getContext());

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewDisc.setLayoutManager(manager);
                        recyclerViewDisc.setAdapter(discussionAdapter);
                    }
                    else if(response.code()==400){
                        String error = ErrorUtils.error(response).getError();
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Не удалось вывести категории схем", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDiscussionByCritetionResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}