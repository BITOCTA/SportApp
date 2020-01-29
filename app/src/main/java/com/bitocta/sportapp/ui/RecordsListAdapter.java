package com.bitocta.sportapp.ui;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.Training;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordsListAdapter extends RecyclerView.Adapter<RecordsListAdapter.ViewHolder> {

    ArrayList<Pair<String, Date>> records = new ArrayList<Pair<String, Date>>();
    private View.OnClickListener mOnItemClickListener;
    DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG);




    public void updateRecords(ArrayList<Pair<String,Date>> records) {
        this.records.clear();
        this.records.addAll(records);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String,Date> record = records.get(position);
        TextView name = holder.recordName;
        TextView date = holder.recordDate;

        name.setText(record.first);
        date.setText(sdf.format(record.second));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recordName;
        public TextView recordDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recordName = itemView.findViewById(R.id.record_name);
            recordDate = itemView.findViewById(R.id.record_date);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);


        }
    }
}
