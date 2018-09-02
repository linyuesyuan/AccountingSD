package com.example.tku.accountingsd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.ImageData;
import com.example.tku.accountingsd.ui.categories.CategoryEditFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPickerAdapter extends RecyclerView.Adapter<CategoriesPickerAdapter.ViewHolder> {

    public int mSelectedItem = -1;
    private List<ImageData> mImageDataList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathReference;
    final long ONE_MEGABYTE = 1024 * 1024;
    String fileName;

    private OnItemClick mCallback;


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private RadioButton radioButton;
        private CardView cardView;

        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout=v;
            image = (ImageView) v.findViewById(R.id.image);
            radioButton = (RadioButton) v.findViewById(R.id.radioButton);
            cardView = (CardView)v.findViewById(R.id.cardView);
            cardView.setRadius(50);

            View.OnClickListener clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                    if(mSelectedItem!=RecyclerView.NO_POSITION){
                        ImageData clickDataItem = mImageDataList.get(mSelectedItem);
                        fileName = clickDataItem.getName();
                        Toast.makeText(v.getContext(), "You clicked " + clickDataItem.getName(), Toast.LENGTH_SHORT).show();
                        mCallback.onClick(clickDataItem.getName());
                        CategoryEditFragment categoryEditFragment=new CategoryEditFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("fileName", fileName);
                        categoryEditFragment.setArguments(bundle);
                        String test = bundle.getString("fileName");

                        Log.d("fileName", test);
                    }
                }
            };
            itemView.setOnClickListener(clickListener);
            radioButton.setOnClickListener(clickListener);
        }

    }

    public CategoriesPickerAdapter(List<ImageData> myDataSet, Context context, RecyclerView recyclerView, OnItemClick listener){
        mImageDataList = myDataSet;
        mContext = context;
        mRecyclerV = recyclerView;
        this.mCallback = listener;
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
    public void onBindViewHolder(@NonNull final CategoriesPickerAdapter.ViewHolder viewHolder, final int position) {
        final ImageData imageData = mImageDataList.get(position);
        pathReference = storageRef.child(imageData.getName());
        viewHolder.radioButton.setChecked(position == mSelectedItem);

        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0 , bytes.length);
                viewHolder.image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("load_Failure", exception.getMessage());
            }
        });
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cardView.setBackgroundColor(1297410206);
            }

        });

    }

    @Override
    public int getItemCount() {
        return mImageDataList.size();
    }

    public interface OnItemClick {
        void onClick(String value);
    }


}
