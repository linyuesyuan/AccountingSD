package com.example.tku.accountingsd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>  {

    private List<Categories> mCategoriesList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    String TAG = "id";


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxt;
        public ImageView image;
        private CardView cardView;

        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout=v;
            mTxt = (TextView) v.findViewById(R.id.txt);
            image = (ImageView) v.findViewById(R.id.image);
            cardView = (CardView)v.findViewById(R.id.cardView);
            cardView.setCardBackgroundColor(Color.TRANSPARENT);

        }

    }

    public void add(int position, Categories categories) {
        mCategoriesList.add(position, categories);
        notifyItemInserted(position);
    }

    public CategoriesAdapter(List<Categories> myDataset, Context context, RecyclerView recyclerView) {
        mCategoriesList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }


    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
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
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder viewHolder, final int position) {
        final Categories categories = mCategoriesList.get(position);

        viewHolder.image.setImageBitmap(categories.getImageDataInBitmap());
        viewHolder.mTxt.setText(categories.getTitle());

        //listen to single view layout click
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("請選擇");
                builder.setMessage("更新或刪除?");
                builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity

                    }
                });
                builder.setNeutralButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoriesDBHelper dbHelper = new CategoriesDBHelper(mContext);
                        dbHelper.deleteCategories(categories.getId(), mContext);
                        Log.d(TAG, Integer.toString(categories.getId()));

                        mCategoriesList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mCategoriesList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return mCategoriesList.size();
    }

}
