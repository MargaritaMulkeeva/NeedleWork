package com.example.needlework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.needlework.Adapters.AllPatternAdapter;
import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.Adapters.PatternAdapter;
import com.example.needlework.Discussions.AddDiscussions;
import com.example.needlework.Discussions.ChooseDiscussions;
import com.example.needlework.Lessons.Lessons;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.GetAllKnittingPatterns;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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

    private AllPatternAdapter allPatternAdapter;
    private List<GetAllKnittingPatterns> mAllPattern;
    private RecyclerView mAllPatternContainer;


    EditText etSearch;

    Button btn_go;
    ImageButton btnSearch;

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
        mAllPatternContainer = view.findViewById(R.id.allPatternRecycler);
        btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch = view.findViewById(R.id.etSearch);
                if (etSearch.getText().toString().equals("")) {
                    getAllPatterns();
                } else {
                    searchItem(etSearch.getText().toString());
                }
            }
        });

        btn_go = view.findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Lessons.class);
                startActivity(intent);
            }
        });


        getCategories();
        getPatterns();
        getAllPatterns();
        return view;
    }

    public void searchItem(String textToSearch) {
        for (Iterator<GetAllKnittingPatterns> it = mAllPattern.iterator(); it.hasNext(); ) {
            if (!it.next().getName().toLowerCase().contains(textToSearch))
                it.remove();
        }
        allPatternAdapter.notifyDataSetChanged();
    }

    private void getCategories() {
        AsyncTask.execute(() -> {
            service.getCategoriesOfPattern().enqueue(new Callback<List<CategoriesOfPatternResponseBody>>() {
                @Override
                public void onResponse(Call<List<CategoriesOfPatternResponseBody>> call, Response<List<CategoriesOfPatternResponseBody>> response) {
                    if (response.isSuccessful()) {
                        mCategories = response.body();
                        categoriesAdapter = new CategoriesAdapter(mCategories, getContext(), new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                getAllPatternsInMainThread();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CategoriesOfPatternResponseBody category = mCategories.get(position);
                                        for (Iterator<GetAllKnittingPatterns> it = mAllPattern.iterator(); it.hasNext(); ) {
                                            if (it.next().getCategoryOfPatternId() != category.getId())
                                                it.remove();
                                        }
                                        allPatternAdapter.notifyDataSetChanged();
                                    }
                                }, 1000);
                            }
                        });

                        SnapHelper snapHelper = new PagerSnapHelper();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        mCategoriesContainer.setLayoutManager(manager);
                        mCategoriesContainer.setAdapter(categoriesAdapter);
                    } else {
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

    private void getPatterns() {
        AsyncTask.execute(() -> {
            service.getPopularKnittingPatterns().enqueue(new Callback<List<KnittingPatternResponseBody>>() {
                @Override
                public void onResponse(Call<List<KnittingPatternResponseBody>> call, Response<List<KnittingPatternResponseBody>> response) {
                    if (response.isSuccessful()) {
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

                    } else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<List<KnittingPatternResponseBody>> call, Throwable t) {
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void getAllPatterns() {
        AsyncTask.execute(() -> {
            service.getAllPatterns().enqueue(new Callback<List<GetAllKnittingPatterns>>() {
                @Override
                public void onResponse(Call<List<GetAllKnittingPatterns>> call, Response<List<GetAllKnittingPatterns>> response) {
                    if (response.isSuccessful()) {
                        mAllPattern = response.body();
                        allPatternAdapter = new AllPatternAdapter(mAllPattern, getContext(), new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(getContext(), ChoosePattern.class);
                                intent.putExtra("patternId", mAllPattern.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        mAllPatternContainer.setLayoutManager(manager);
                        mAllPatternContainer.setAdapter(allPatternAdapter);
                    } else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GetAllKnittingPatterns>> call, Throwable t) {
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void getAllPatternsInMainThread() {
        service.getAllPatterns().enqueue(new Callback<List<GetAllKnittingPatterns>>() {
            @Override
            public void onResponse(Call<List<GetAllKnittingPatterns>> call, Response<List<GetAllKnittingPatterns>> response) {
                if (response.isSuccessful()) {
                    mAllPattern = response.body();
                    allPatternAdapter = new AllPatternAdapter(mAllPattern, getContext(), new OnAdapterItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(getContext(), ChoosePattern.class);
                            intent.putExtra("patternId", mAllPattern.get(position).getId());
                            startActivity(intent);
                        }
                    });

                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    mAllPatternContainer.setLayoutManager(manager);
                    mAllPatternContainer.setAdapter(allPatternAdapter);
                } else {
                    String message = ErrorUtils.error(response).getError();
                    new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetAllKnittingPatterns>> call, Throwable t) {
                new AlertDialog.Builder(getContext()).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }
}