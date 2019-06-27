package com.supagorn.devpractice.customs.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.supagorn.devpractice.model.home.Post;


public class PostViewHolder extends RecyclerView.ViewHolder {

	public PostViewHolder(View itemView) {
		super(itemView);
	}

	public void bindToPost(Post post, View.OnClickListener starClickListener) {
//		titleView.setText(post.title);
//		authorView.setText(post.author);
//		numStarsView.setText(String.valueOf(post.likeCount));
//		bodyView.setText(post.body);
//		starView.setOnClickListener(starClickListener);
	}
}