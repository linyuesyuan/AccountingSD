package com.example.tku.accountingsd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.ImageDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Categories;
import com.example.tku.accountingsd.model.ImageData;
import com.example.tku.accountingsd.ui.categories.CategoryEditActivity;
import com.example.tku.accountingsd.ui.categories.CategoryEditFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>  {

    private List<ImageData> mCategoriesList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private ImageDBHelper imageDBHelper;


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathReference;
    final long ONE_MEGABYTE = 1024 * 1024;

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
            cardView.setRadius(50);
        }

    }
/*
    public void add(int position, Categories categories) {
        mCategoriesList.add(position, categories);
        notifyItemInserted(position);
    }
*/

    public CategoriesAdapter(List<ImageData> myDataset, Context context, RecyclerView recyclerView) {
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
    public void onBindViewHolder(@NonNull final CategoriesAdapter.ViewHolder viewHolder, final int position) {
        //final Categories categories = mCategoriesList.get(position);
        final ImageData imageData = mCategoriesList.get(position);
        pathReference = storageRef.child(imageData.getName());

        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                if(bytes == null){
                    Log.d("bytes are null", "!!!!");
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0 , bytes.length);
                viewHolder.image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("load_Failure", exception.getMessage());
            }
        });


        /*
        viewHolder.mTxt.setText(categories.getTitle());
        viewHolder.mTxt.setBackgroundColor(categories.getColor());

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
*/
    }

    @Override
    public int getItemCount() {
        return mCategoriesList.size();
    }

}
