package com.example.pooria.boomranq.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pooria.boomranq.Model.GetPost;
import com.example.pooria.boomranq.Model.Post_Inf;
import com.example.pooria.boomranq.R;
import com.example.pooria.boomranq.Retrofit.MyBoomranQAPI;
import com.example.pooria.boomranq.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPostAdapter extends RecyclerView.Adapter<ShowPostViewHolder> {

    Context context;
    List<GetPost>posts;
    List<Post_Inf>post;

    MyBoomranQAPI mService;
    private int clickcount=0;
    

    @NonNull
    @Override
    public ShowPostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_show_item_posts, null);
        Log.d("aaaaa", "1111111111");
        return new ShowPostViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowPostViewHolder holder, final int i) {
        Log.d("aaaaa", "22222222");


        Picasso.with(context)
                .load(Common.BASE_URL+posts.get(i).getLink())
                .into(holder.img_post);

        holder.img_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("view", " On View :  " + Common.BASE_URL+posts.get(i).getId());
            }
        });

        holder.img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edt_comment.setVisibility(View.VISIBLE);
            }
        });

        holder.txt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount++;
                holder.txt_like.setText(Integer.toString(clickcount));
               // holder.txt_like.setText(Integer.toString(0));
            }
        });




        holder.img_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String post_id =posts.get(i).getId() ;
                String text_like = holder.txt_like.getText().toString();
                String edt_comment = holder.edt_comment.getText().toString();
                String title =  posts.get(i).getTitle();
                //String post_link = posts.get(i).getLink();
                Log.d("view", " id : " + post_id+"  like : "+text_like+"  comment : "+edt_comment + " title : " + title + "link "  );
                mService = Common.getAPI();
                mService.upload_Inf_Post(post_id,title,text_like,edt_comment)
                        .enqueue(new Callback<Post_Inf>() {
                            @Override
                            public void onResponse(Call<Post_Inf> call, Response<Post_Inf> response) {
                                Log.d("view", response.toString());

                            }

                            @Override
                            public void onFailure(Call<Post_Inf> call, Throwable t) {
                                Log.d("view", t.toString());
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public ShowPostAdapter(Context context, List<GetPost> posts) {
        this.context = context;
        this.posts = posts;
    }



}
