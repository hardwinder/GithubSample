package com.example.dapurmasak08.githubsample.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dapurmasak08.githubsample.ProfileActivity;
import com.example.dapurmasak08.githubsample.R;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.http.params.Order;
import com.rejasupotaro.octodroid.http.params.Sort;
import com.rejasupotaro.octodroid.models.SearchResult;
import com.rejasupotaro.octodroid.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

/**
 * Created by dapurmasak08 on 1/23/15.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemViewHolder>  {

    private List<User> dataset = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchResultAdapter() {
    }

    public void update(String query) {
        GitHub.client().searchUsers(query, Sort.FOLLOWERS, Order.DESC)
                .subscribe(new Action1<Response<SearchResult<User>>>() {
                    @Override
                    public void call(Response<SearchResult<User>> r) {
                        List<User> users = r.entity().getItems();
                        dataset.clear();
                        for (User user : users) {
                            dataset.add(user);
                        }
                        notifyDataSetChanged();
                    }
                });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        return SearchResultItemViewHolder.create(parent);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SearchResultItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        User user = dataset.get(position);
        ((SearchResultItemViewHolder) holder).bind(user);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class SearchResultItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        @InjectView(R.id.text) TextView textView;
        @InjectView(R.id.imageView) ImageView imageView;

        public static SearchResultItemViewHolder create(ViewGroup parent) {
            // create a new view
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search_result, parent, false);
            return new SearchResultItemViewHolder(itemView);
        }

        private SearchResultItemViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(User user) {
            textView.setText(user.getLogin());
            Picasso.with(context)
                    .load(user.getAvatarUrl())
                    .into(imageView);
            Log.d("Debug User Image URL", user.getAvatarUrl());
        }

        @Override
        public void onClick(View view) {
            Log.d("debug click ", "onClick " + getPosition() + " " + textView.getText());
            Intent intent = new Intent(context, ProfileActivity.class );
            context.startActivity(intent);
        }
    }
}