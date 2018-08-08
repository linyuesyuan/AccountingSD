package com.example.tku.accountingsd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Cate;

import java.util.List;

public class CateAdapter extends RecyclerView.Adapter<CateAdapter.ViewHolder>  {

    private List<Cate> mCateList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder {


        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout=v;
        }
    }

    public void add(int position, Cate cate) {
        mCateList.add(position, cate);
        notifyItemInserted(position);
    }

    public CateAdapter(List<Cate> myDataset, Context context, RecyclerView recyclerView) {
        mCateList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }


    @NonNull
    @Override
    public CateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.cate_single_row, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CateAdapter.ViewHolder viewHolder, final int position) {
        final Cate cate = mCateList.get(position);
        //viewHolder.button.setBackgroundResource(R.drawable.clothes2);
        //viewHolder.button.setText(R.string.clothes);

        //listen to single view layout click
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCateList.size();    }

}
