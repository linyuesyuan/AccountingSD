package com.example.tku.accountingsd.ui.categories;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tku.accountingsd.Adapter.CategoriesAdapter;
import com.example.tku.accountingsd.BaseFragment;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.ui.homeScreen.homeScreenFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment{

    FloatingActionButton floatingActionButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CategoriesDBHelper dbHelper;
    private CategoriesAdapter adapter;


    ImageView imageView;



    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        floatingActionButton = (FloatingActionButton)v.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_main, new CategoryEditFragment()).commit();
            }
        });
        imageView = (ImageView) v.findViewById(R.id.imageView);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);

        populateRecyclerView();
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("類別編輯");
    }


    private void populateRecyclerView() {
        dbHelper = new CategoriesDBHelper(getActivity());
        adapter = new CategoriesAdapter(dbHelper.categoriesList(), getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        getFragmentManager().beginTransaction().replace(R.id.content_main, new homeScreenFragment()).commit();
    }
}
