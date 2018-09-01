package com.example.tku.accountingsd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Categories;
import com.example.tku.accountingsd.model.Record;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class NewRecordAdapter extends RecyclerView.Adapter<NewRecordAdapter.ViewHolder>  {

    private List<Record> mRecordList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private CategoriesDBHelper categoriesDBHelper;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathReference;
    final long ONE_MEGABYTE = 1024 * 1024;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitle;
        public TextView tvMoney;
        public ImageView image;
        private CardView cardView;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvTitle = (TextView)v.findViewById(R.id.tvTitle);
            tvMoney = (TextView)v.findViewById(R.id.tvMoney);
            image = (ImageView) v.findViewById(R.id.image);
            cardView = (CardView)v.findViewById(R.id.cardView);
            cardView.setRadius(50);
        }
    }

    public void add(int position, Record record) {
        mRecordList.add(position, record);
        notifyItemInserted(position);
    }

    public NewRecordAdapter(List<Record> myDataset, Context context, RecyclerView recyclerView) {
        mRecordList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }


    @NonNull
    @Override
    public NewRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.record_single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewRecordAdapter.ViewHolder holder, final int position) {
        final Record record = mRecordList.get(position);
        categoriesDBHelper = new CategoriesDBHelper(mContext);
        List<Categories> categoriesList = categoriesDBHelper.categoriesList();
        int id = (int) record.getType()-1;
        final Categories categories = categoriesList.get(id);

        pathReference = storageRef.child(categories.getFileName());

        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                if(bytes == null){
                    Log.d("bytes are null", "!!!!");
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0 , bytes.length);
                holder.image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("load_Failure", exception.getMessage());
            }
        });

        //holder.image.setImageBitmap(bitmap);
        holder.tvTitle.setText(record.getTitle());
        holder.tvMoney.setText("$" + record.getMoney());

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("請選擇");
                builder.setMessage("更新或刪除?");
                /*
                builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity

                    }
                });
                */
                builder.setNeutralButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewRecordDBHelper dbHelper = new NewRecordDBHelper(mContext);
                        dbHelper.deleteRecord(record.getId(), mContext);



                        mRecordList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mRecordList.size());
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
        return mRecordList.size();
    }
}
