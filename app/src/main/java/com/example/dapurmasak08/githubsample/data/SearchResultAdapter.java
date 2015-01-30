package com.example.dapurmasak08.githubsample.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

/**
 * Created by dapurmasak08 on 1/23/15.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemViewHolder> {
    private List<User> dataset = new ArrayList<>();
    private final Context context;
    private Subscription subscription = Subscriptions.empty();

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void update(String query) {
        subscription.unsubscribe();
        subscription = GitHub.client().searchUsers(query, Sort.FOLLOWERS, Order.DESC)
                .subscribe(new Action1<Response<SearchResult<User>>>() {
                    @Override
                    public void call(Response<SearchResult<User>> r) {
                        List<User> users = r.entity().getItems();
                        dataset.clear();
                        for (User user : users) {
                            dataset.add(user);
                        }
                        notifyDataSetChanged();
                        checkIfEmpty();
                    }
                });
    }

    private void checkIfEmpty() {
        if (getItemCount() <= 0) {
            Toast.makeText(context, R.string.no_search_result, Toast.LENGTH_LONG).show();
            Intent profile = new Intent(context, ProfileActivity.class);
            context.startActivity(profile);
        }
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

    public static class SearchResultItemViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        @InjectView(R.id.text)
        TextView textView;
        @InjectView(R.id.imageView)
        ImageView imageView;

        public static SearchResultItemViewHolder create(ViewGroup parent) {
            // create a new view
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search_result, parent, false);
            return new SearchResultItemViewHolder(itemView);
        }

        private SearchResultItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.inject(this, itemView);
        }

        public void bind(final User user) {
            textView.setText(user.getLogin());
            Picasso.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                    // serialize
                    String serializedUser = GsonProvider.get().toJson(user);
                    intent.putExtra("user", serializedUser);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void destroy() {
        subscription.unsubscribe();
    }
}
