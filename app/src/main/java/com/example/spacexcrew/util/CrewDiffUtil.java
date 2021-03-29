package com.example.spacexcrew.util;

import androidx.recyclerview.widget.DiffUtil;

import com.example.spacexcrew.dataClasses.Crew;

import java.util.List;

public class CrewDiffUtil extends DiffUtil.Callback {

    private final List<Crew> oldList;
    private final List<Crew> newList;

    public CrewDiffUtil(List<Crew> oldList, List<Crew> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
