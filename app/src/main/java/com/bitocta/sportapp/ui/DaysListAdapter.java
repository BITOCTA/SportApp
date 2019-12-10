package com.bitocta.sportapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bitocta.sportapp.R;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DaysListAdapter extends RecyclerView.Adapter<DaysListAdapter.ViewHolder> {


    private View.OnClickListener mOnItemClickListener;
    private ArrayList<ArrayList<Plan>> days = new ArrayList<>();
    private final int EXERCISES_IN_DESC_NUMBER = 3;
    private User user;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.days_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String exercises = " ";

        for(int i = 0; i<EXERCISES_IN_DESC_NUMBER; i++){
            if (i != 2) {
                exercises += days.get(position).get(i).getExercise().getName() + ", ";
            }
            else {
                exercises += days.get(position).get(i).getExercise().getName();
            }
        }




        TextView title = holder.dayTitle;
        TextView desc = holder.dayDesc;
        ImageView image = holder.dayImage;

        if(position+1<=user.getDay()) {
            Glide.with(ProgressFragment.context).
                    load(ProgressFragment.context.getDrawable(R.drawable.day_done)).
                    into(image);
        }
        else {
            Glide.with(ProgressFragment.context).
                    load(ProgressFragment.context.getDrawable(R.drawable.day_not_done)).
                    into(image);

        }


        int dayNumber = position+1;
        String sb = ProgressFragment.context.getResources().getString(R.string.day) +
                " " +
                dayNumber;
        title.setText(sb);
        desc.setText(exercises);



    }

    public void setOnItemClickListener(View.OnClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void updateDaysListItems(List<ArrayList<Plan>> days, User user){
        this.days.clear();
        this.days.addAll(days);
        this.user=user;
    }

    /*
    public void updateTrainingListItems(List<ArrayList<Plan>> days) {
        final DaysDiffCallback diffCallback = new DaysDiffCallback(this.days, days);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.days.clear();
        this.days.addAll(days);
        diffResult.dispatchUpdatesTo(this);
    }*/

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dayTitle;
        public TextView dayDesc;
        public ImageView dayImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTitle = itemView.findViewById(R.id.day_item_title);
            dayImage = itemView.findViewById(R.id.days_item_image);
            dayDesc = itemView.findViewById(R.id.day_item_desc);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
