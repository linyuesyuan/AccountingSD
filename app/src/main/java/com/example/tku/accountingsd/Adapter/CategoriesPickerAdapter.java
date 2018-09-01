package com.example.tku.accountingsd.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.ImageData;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPickerAdapter extends RecyclerView.Adapter<CategoriesPickerAdapter.ViewHolder> {

    private List<ImageData> mImageDataList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private CardView cardView;

        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout=v;
            image = (ImageView) v.findViewById(R.id.image);
            cardView = (CardView)v.findViewById(R.id.cardView);
            cardView.setRadius(50);
        }
    }

    public CategoriesPickerAdapter(List<ImageData> myDataSet, Context context, RecyclerView recyclerView){
        mImageDataList = myDataSet;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @NonNull
    @Override
    public CategoriesPickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.categories_image_select_single_row, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesPickerAdapter.ViewHolder viewHolder, int i) {
        final ImageData imageData = mImageDataList.get(i);
        viewHolder.image.setImageResource(R.drawable.accessories);
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
