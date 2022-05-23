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
import com.example.needlework.Adapters.PatternAdapter;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.CategoriesOfPatternResponse;
import com.example.needlework.NetWork.Models.KnittingPatternResponse;
import com.example.needlework.NetWork.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class firstFragment extends Fragment {
    private CategoriesAdapter categoriesAdapter;
    private List<CategoriesOfPatternResponse> mCategories;
    private RecyclerView mCategoriesContainer;

    private PatternAdapter patternAdapter;
    private List<KnittingPatternResponse> mPattern;
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
            service.getCategoriesOfPattern().enqueue(new Callback<List<CategoriesOfPatternResponse>>() {
                @Override
                public void onResponse(Call<List<CategoriesOfPatternResponse>> call, Response<List<CategoriesOfPatternResponse>> response) {
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
                public void onFailure(Call<List<CategoriesOfPatternResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void getPatterns(){
        AsyncTask.execute(()->{
            service.getPopularKnittingPatterns().enqueue(new Callback<List<KnittingPatternResponse>>() {
                @Override
                public void onResponse(Call<List<KnittingPatternResponse>> call, Response<List<KnittingPatternResponse>> response) {
                    if(response.isSuccessful()){

                        mPattern = response.body();
                        patternAdapter = new PatternAdapter(mPattern, getContext());

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
                public void onFailure(Call<List<KnittingPatternResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}