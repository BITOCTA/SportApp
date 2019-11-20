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
import com.bitocta.sportapp.db.entity.Training;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlansListAdapter extends RecyclerView.Adapter<PlansListAdapter.ViewHolder> {



    private View.OnClickListener mOnItemClickListener;
    private ArrayList<Training> trainings = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plans_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Training training = trainings.get(position);

        TextView title = holder.planTitle;
        ImageView image = holder.planImage;

        title.setText(training.getName());
        Glide.with(PlansFragment.context).load(training.getImage_path()).centerCrop().into(image);


    }

    public void updateTrainingListItems(List<Training> trainings) {
        final PlanDiffCallback diffCallback = new PlanDiffCallback(this.trainings, trainings);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.trainings.clear();
        this.trainings.addAll(trainings);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView planTitle;
        public ImageView planImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            planTitle = itemView.findViewById(R.id.planTitle);
            planImage = itemView.findViewById(R.id.planImage);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);


        }
    }
}
