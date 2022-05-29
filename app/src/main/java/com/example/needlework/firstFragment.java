package com.example.needlework;

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
import android.widget.Toast;

import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.Adapters.PatternAdapter;
import com.example.needlework.Discussions.ChooseDiscussions;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class firstFragment extends Fragment {
    private CategoriesAdapter categoriesAdapter;
    private List<CategoriesOfPatternResponseBody> mCategories;
    private RecyclerView mCategoriesContainer;

    private PatternAdapter patternAdapter;
    private List<KnittingPatternResponseBody> mPattern;
    private RecyclerView mPatternContainer;

    private ApiService service = ApiHandler.getmInstance().getService();


    public firstFragment() {
    }
    public static firstFragment newInstance(String param1, String param2) {
        return new firstFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        mCategoriesContainer = view.findViewById(R.id.recyclerCategories);
        mPatternContainer = view.findViewById(R.id.patternRecycler);


        getCategories();
        getPatterns();
        return view;
    }

    private void getCategories(){
        AsyncTask.execute(()->{
            service.getCategoriesOfPattern().enqueue(new Callback<List<CategoriesOfPatternResponseBody>>() {
                @Override
                public void onResponse(Call<List<CategoriesOfPatternResponseBody>> call, Response<List<CategoriesOfPatternResponseBody>> response) {
                    if(response.isSuccessful()){
                        mCategories = response.body();
                        categoriesAdapter = new CategoriesAdapter(mCategories, getContext());

                        SnapHelper snapHelper = new PagerSnapHelper();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        mCategoriesContainer.setLayoutManager(manager);
                        mCategoriesContainer.setAdapter(categoriesAdapter);
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
                public void onFailure(Call<List<CategoriesOfPatternResponseBody>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void getPatterns(){
        AsyncTask.execute(()->{
            service.getPopularKnittingPatterns().enqueue(new Callback<List<KnittingPatternResponseBody>>() {
                @Override
                public void onResponse(Call<List<KnittingPatternResponseBody>> call, Response<List<KnittingPatternResponseBody>> response) {
                    if(response.isSuccessful()) {
                        mPattern = response.body();
                        patternAdapter = new PatternAdapter(mPattern, getContext(), new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(getContext(), ChoosePattern.class);
                                intent.putExtra("patternId", mPattern.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        mPatternContainer.setLayoutManager(manager);
                        mPatternContainer.setAdapter(patternAdapter);

                    }
                    else if(response.code()==400){
                        String error = ErrorUtils.error(response).getError();
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Не удалось вывести схемы", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<KnittingPatternResponseBody>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}