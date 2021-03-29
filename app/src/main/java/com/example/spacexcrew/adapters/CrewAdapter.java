package com.example.spacexcrew.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacexcrew.DetailsActivity;
import com.example.spacexcrew.R;
import com.example.spacexcrew.dataClasses.Crew;
import com.example.spacexcrew.databinding.CrewItemViewBinding;
import com.example.spacexcrew.util.CrewDiffUtil;

import java.util.ArrayList;
import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private List<Crew> list = new ArrayList<>();

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrewViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.crew_item_view,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Crew> list) {
        CrewDiffUtil crewDiffUtil = new CrewDiffUtil(this.list, list);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(crewDiffUtil);
        this.list = list;
        result.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, @Nullable Object payload) {
                notifyItemRangeChanged(position, count, payload);
            }
        });
    }

    class CrewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CrewItemViewBinding binding;

        public CrewViewHolder(CrewItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setListener(this);
        }

        public void bind(Crew crew) {
            binding.setCrew(crew);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.crewDetailsLayout) {
                showDetailsActivity();
            } else if (v.getId() == R.id.ivWikipedia) {
                v.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(binding.getCrew().getWikipedia()))
                );
            }
        }

        private void showDetailsActivity() {
            Context context = itemView.getContext();
            String transitionNAme = context.getString(R.string.crew_image_transition);
            binding.ivCrewImage.setTransitionName(transitionNAme);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.CREW_DATA_KEY, binding.getCrew());
            ContextCompat.startActivity(
                    context,
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context, binding.ivCrewImage, transitionNAme).toBundle()
            );
        }
    }
}
