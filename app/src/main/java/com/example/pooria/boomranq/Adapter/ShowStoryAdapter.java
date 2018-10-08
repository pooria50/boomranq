package com.example.pooria.boomranq.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pooria.boomranq.Model.GetPost;
import com.example.pooria.boomranq.R;
import com.example.pooria.boomranq.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowStoryAdapter extends RecyclerView.Adapter<ShowStoryAdapter.ViewHolder> {

    private List<GetPost> posts = new ArrayList<>();
    private Context context;
    private OnItemclikListener mListener;


    public interface OnItemclikListener{
        void onItemClik(int position);
    }

    public void setOnItemClickListener(OnItemclikListener listener) {
        mListener = listener;
    }

    public ShowStoryAdapter(List<GetPost> posts, Context context, OnItemclikListener mListener) {
        this.posts = posts;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(Common.BASE_URL+posts.get(position).getLink()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image ;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClik(position);
                        }
                    }
                }
            });
        }
    }
}
