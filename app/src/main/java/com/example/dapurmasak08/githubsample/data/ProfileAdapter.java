package com.example.dapurmasak08.githubsample.data;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dapurmasak08.githubsample.R;
import com.rejasupotaro.octodroid.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private User user = new User();
    private List<User> dataset = new ArrayList<>();
    // Create new views (invoked by the layout manager)
    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProfileViewHolder.create(parent);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        User user = dataset.get(position);
        ((ProfileViewHolder) holder).bind(user);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        @InjectView(R.id.text)
        TextView textView;
        @InjectView(R.id.imageView)
        ImageView imageView;

        public static ProfileViewHolder create(ViewGroup parent) {
            // create a new view
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_profile, parent, false);
            return new ProfileViewHolder(itemView);
        }

        private ProfileViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.inject(this, itemView);
        }

        public void bind(final User user) {
            textView.setText(user.getLogin());
            Picasso.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .into(imageView);

            Log.d("Debug User Image URL", user.getAvatarUrl());
        }
    }
}
