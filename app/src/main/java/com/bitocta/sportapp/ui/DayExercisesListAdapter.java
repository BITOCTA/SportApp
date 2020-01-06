package com.bitocta.sportapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitocta.sportapp.R;

import com.bitocta.sportapp.db.entity.Plan;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class DayExercisesListAdapter extends RecyclerView.Adapter<DayExercisesListAdapter.ViewHolder> {


    private View.OnClickListener mOnItemClickListener;
    private ArrayList<Plan> plans = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView title = holder.exerciseTitle;
        TextView reps = holder.exerciseReps;
        ImageView image = holder.exerciseImage;

        Plan plan = plans.get(position);

        title.setText(plan.getExercise().getName());



        reps.setText(plan.getSeconds()/60 + " minutes");



        if (plan.getExercise().getImagePath() != null) {
            Glide.with(ProgressFragment.context).load(plan.getExercise().getImagePath()).centerCrop().into(image);
        } else {
            Glide.with(ProgressFragment.context).load(ProgressFragment.context.getDrawable(R.drawable.default_exercise)).centerCrop().into(image);
        }

    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void updateDaysListItems(ArrayList<Plan> plans) {
      this.plans.clear();
        this.plans.addAll(plans);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView exerciseTitle;
        public TextView exerciseReps;
        public ImageView exerciseImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.exercise_title);
            exerciseReps = itemView.findViewById(R.id.exercise_reps);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
