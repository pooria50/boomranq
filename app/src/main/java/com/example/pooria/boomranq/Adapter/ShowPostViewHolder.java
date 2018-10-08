package com.example.pooria.boomranq.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pooria.boomranq.R;


public class ShowPostViewHolder extends RecyclerView.ViewHolder {

    ImageView img_post,img_like,img_comment;
    EditText edt_comment;
    TextView txt_like;


    public ShowPostViewHolder(@NonNull View itemView) {
        super(itemView);
        img_post = itemView.findViewById(R.id.img_post);
        img_like = itemView.findViewById(R.id.img_like);
        img_comment = itemView.findViewById(R.id.img_comment);
        edt_comment = itemView.findViewById(R.id.edt_comment);
        txt_like = itemView.findViewById(R.id.txt_like);


    }



}
