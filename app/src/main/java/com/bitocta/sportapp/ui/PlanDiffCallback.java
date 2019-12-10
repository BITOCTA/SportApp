package com.bitocta.sportapp.ui;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.bitocta.sportapp.db.entity.Training;

import java.util.List;

public class PlanDiffCallback extends DiffUtil.Callback {

    private final List<Training> mOldProductsList;
    private final List<Training> mNewProductsList;

    public PlanDiffCallback(List<Training> oldProductsList, List<Training> newProductsList) {
        this.mOldProductsList = oldProductsList;
        this.mNewProductsList = newProductsList;
    }

    @Override
    public int getOldListSize() {
        return mOldProductsList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewProductsList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldProductsList.get(oldItemPosition).tid == mNewProductsList.get(
                newItemPosition).tid;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Training oldProduct = mOldProductsList.get(oldItemPosition);
        final Training newProduct = mNewProductsList.get(newItemPosition);


        return oldProduct.getName().equals(newProduct.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
