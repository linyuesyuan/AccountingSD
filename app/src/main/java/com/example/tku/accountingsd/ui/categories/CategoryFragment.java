package com.example.tku.accountingsd.ui.categories;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tku.accountingsd.Adapter.CategoriesAdapter;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.ImageDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.ImageData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment{

    FloatingActionButton floatingActionButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CategoriesAdapter adapter;

    long i;
    ImageData imageData;
    List<ImageData> mImageDataList = new LinkedList<>();

    ImageView imageView;
    byte[] getImage ;



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
        loadImageData();

        populateRecyclerView();
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("類別編輯");
    }

    private void loadImageData(){
        String[] imageName = getResources().getStringArray(R.array.image_name);
        i=1;
        for(String name:imageName){
            imageData = new ImageData();
            imageData.setName(name);
            imageData.setId(i);
            mImageDataList.add(imageData);
            i++;
        }
    }

    private void populateRecyclerView() {
        adapter = new CategoriesAdapter(mImageDataList, getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

}
