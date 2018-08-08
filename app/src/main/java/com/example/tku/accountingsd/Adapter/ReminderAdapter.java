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

import com.example.tku.accountingsd.DBHelper.ReminderDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Reminder;
import com.example.tku.accountingsd.ui.reminders.UpdateReminderActivity;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<Reminder> mReminderList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitle;
        public TextView tvStartDate;
        public TextView tvDateCycle;
        public TextView tvMoney;
        private CardView cardView;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvTitle = (TextView)v.findViewById(R.id.tvTitle);
            tvStartDate = (TextView)v.findViewById(R.id.tvStartDate);
            tvDateCycle = (TextView)v.findViewById(R.id.tvDateCycle);
            tvMoney = (TextView)v.findViewById(R.id.tvMoney);
            cardView = (CardView)v.findViewById(R.id.cardView);

            cardView.setCardBackgroundColor(Color.TRANSPARENT);
        }
    }
    public void add(int position, Reminder reminder) {
        mReminderList.add(position, reminder);
        notifyItemInserted(position);
    }

    public ReminderAdapter(List<Reminder> myDataset, Context context, RecyclerView recyclerView) {
        mReminderList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    public void updateReminders(List<Reminder> mReminderList) {
        this.mReminderList = mReminderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.reminder_single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, final int position) {
        final Reminder reminder = mReminderList.get(position);
        holder.tvTitle.setText("Title: " + reminder.getTitle());
        holder.tvStartDate.setText("Start Date: " + reminder.getStartDate());
        holder.tvDateCycle.setText("Date Cycle: " + reminder.getDateCycle());
        holder.tvMoney.setText("Money: " + reminder.getMoney());

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete reminder?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(reminder.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReminderDBHelper dbHelper = new ReminderDBHelper(mContext);
                        dbHelper.deleteReminderRecord(reminder.getId(), mContext);

                        mReminderList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mReminderList.size());
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

    private void goToUpdateActivity(long reminderId){
        Intent goToUpdate = new Intent(mContext, UpdateReminderActivity.class);
        goToUpdate.putExtra("REMINDER_ID", reminderId);
        mContext.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return mReminderList.size();
    }
}
