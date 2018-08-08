package com.example.tku.accountingsd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tku.accountingsd.DBHelper.NewRecordDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Record;
import com.example.tku.accountingsd.ui.newRecord.UpdateRecordActivity;

import java.util.List;

public class NewRecordAdapter extends RecyclerView.Adapter<NewRecordAdapter.ViewHolder>  {
    private List<Record> mRecordList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitle;
        public TextView tvDate;
        public TextView tvMoney;
        public TextView tvType;
        private CardView cardView;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvTitle = (TextView)v.findViewById(R.id.tvTitle);
            tvDate = (TextView)v.findViewById(R.id.tvDate);
            tvMoney = (TextView)v.findViewById(R.id.tvMoney);
            tvType = (TextView)v.findViewById(R.id.tvType);
            cardView = (CardView)v.findViewById(R.id.cardView);

            cardView.setCardBackgroundColor(Color.TRANSPARENT);
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
    public void onBindViewHolder(@NonNull NewRecordAdapter.ViewHolder holder, final int position) {
        final Record record = mRecordList.get(position);
        holder.tvTitle.setText("花費項目： " + record.getTitle());
        holder.tvDate.setText("日期： " + record.getDate());
        holder.tvMoney.setText("金額： " + record.getMoney());
        holder.tvType.setText("項目類別： " + record.getType());

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete record?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(record.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
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
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void goToUpdateActivity(long recordId){
        Intent goToUpdate = new Intent(mContext, UpdateRecordActivity.class);
        goToUpdate.putExtra("RECORD_ID", recordId);
        mContext.startActivity(goToUpdate);
    }


    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}
