package com.anupcowkur.mvpsample.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anupcowkur.mvpsample.R;
import com.anupcowkur.mvpsample.model.pojo.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PostsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> posts;


    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;

    public PostsListAdapter() {
        this.posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void remove()
    {
        Log.i("Load More pb","dismissed");
        posts.remove(posts.size()-1);
        notifyDataSetChanged();
    }



    public void addPosts(List<Post> newPosts) {

        posts.addAll(newPosts);
        Log.i("Size in adapter",""+posts.size());
        notifyDataSetChanged();
    }

    public void add(Post o) {
        posts.add(o);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        if(position==VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row_item, parent, false);
            RecyclerView.ViewHolder  viewHolder = new ViewHolder(v);
            return viewHolder;
        }
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_load_more, parent, false);

            RecyclerView.ViewHolder viewHolder =new ProgressViewHolder(v);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


        if(viewHolder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) viewHolder;
            Log.i("Adapter title ",""+posts.get(position).getTitle());
            vh.postTitle.setText(posts.get(position).getTitle());
            vh.postBody.setText(posts.get(position).getBody());
        }else
        {
            ((ProgressViewHolder)viewHolder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_title)
        TextView postTitle;
        @Bind(R.id.post_body)
        TextView postBody;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.progressBar) ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }


}
