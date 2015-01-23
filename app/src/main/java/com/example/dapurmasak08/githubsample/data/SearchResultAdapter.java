package com.example.dapurmasak08.githubsample.data;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import com.example.dapurmasak08.githubsample.R;
import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.http.params.Order;
import com.rejasupotaro.octodroid.http.params.Sort;
import com.rejasupotaro.octodroid.models.SearchResult;
import com.rejasupotaro.octodroid.models.User;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by dapurmasak08 on 1/23/15.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemViewHolder>  {

    private List<String> dataset = new ArrayList<>();

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
                            dataset.add(user.getLogin());
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
        String item = dataset.get(position);
        ((SearchResultItemViewHolder) holder).bind(item);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class SearchResultItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public static SearchResultItemViewHolder create(ViewGroup parent) {
            // create a new view
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search_result, parent, false);
            return new SearchResultItemViewHolder(itemView);
        }

        private SearchResultItemViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text);
            // set the view's size, margins, paddings and layout parameters
        }

        public void bind(String item) {
            textView.setText(item);
        }
    }
}
