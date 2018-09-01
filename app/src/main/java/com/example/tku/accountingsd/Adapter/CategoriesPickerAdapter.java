package com.example.tku.accountingsd.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.ImageData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPickerAdapter extends RecyclerView.Adapter<CategoriesPickerAdapter.ViewHolder> {

    private List<ImageData> mImageDataList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathReference;
    final long ONE_MEGABYTE = 1024 * 1024;

    private AdapterView.OnItemClickListener onItemClickListener = null;


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
    public void onBindViewHolder(@NonNull final CategoriesPickerAdapter.ViewHolder viewHolder, final int position) {
        final ImageData imageData = mImageDataList.get(position);
        pathReference = storageRef.child(imageData.getName());

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


}
