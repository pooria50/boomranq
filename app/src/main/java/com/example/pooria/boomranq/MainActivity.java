package com.example.pooria.boomranq;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.pooria.boomranq.Adapter.ShowPostAdapter;
import com.example.pooria.boomranq.Model.GetPost;
import com.example.pooria.boomranq.Model.Post_Inf;
import com.example.pooria.boomranq.Model.SendPost;
import com.example.pooria.boomranq.Retrofit.MyBoomranQAPI;
import com.example.pooria.boomranq.Utils.Common;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private TabHost host;
    private ImageView img_insert_post;
    private EditText edt_post_title;
    private Button btn_save_post;
    private RecyclerView recycler_showPost;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    private MyBoomranQAPI mService;
    private List<GetPost> posts = new ArrayList<>();
    private List<Post_Inf> post = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind_controls();
        fireBaseNotification();
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
        host.addTab(spec);
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                getPosts();
            }
        });

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                selectImage();
            }
        });
        btn_save_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_Post();
            }
        });
        host.addTab(spec);


        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

    }

    private void bind_controls() {
        host = findViewById(R.id.tabHost);
        img_insert_post = findViewById(R.id.img_insert_post);
        edt_post_title = findViewById(R.id.edt_post_title);
        btn_save_post = findViewById(R.id.btn_save_post);
        recycler_showPost = findViewById(R.id.recycler_showPost);
    }
    private void fireBaseNotification() {
        FirebaseInstanceId.getInstance().getToken();

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMG_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img_insert_post.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imgByte = outputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void upload_Post() {
        mService = Common.getAPI();
        String title = edt_post_title.getText().toString();
        String link = imageToString();
        mService.upload_Post(title, link).enqueue(new Callback<SendPost>() {
            @Override
            public void onResponse(Call<SendPost> call, Response<SendPost> response) {
                SendPost body = response.body();
                Log.d("log", response.toString());
            }
            @Override
            public void onFailure(Call<SendPost> call, Throwable t) {
                Log.d("log", t.toString());
            }
        });
    }


    private void getPosts() {
        /*mService.show_Posts().enqueue(new Callback<List<GetPost>>() {
            @Override
            public void onResponse(Call<List<GetPost>> call, Response<List<GetPost>> response) {
                List<GetPost> body = response.body();
                for (int i = 0 ; i < body.size() ; i++) {
                    recycler_showPost.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                    recycler_showPost.setHasFixedSize(true);
                    GetPost getPost = new GetPost();
                    getPost.setId(body.get(i).getId());
                    getPost.setTitle(body.get(i).getTitle());
                    getPost.setLink(body.get(i).getLink());
                    posts.add(getPost);

                    displayPosts();
                }

                for (int i = 0 ; i < body.size();i++) {
                    Log.d("date", body.get(i).getId() + " " + body.get(i).getTitle() + " " + body.get(i).getLink());
                }
            }
            @Override
            public void onFailure(Call<List<GetPost>> call, Throwable t) {
                Log.d("date", t.toString());
            }
        });*/

        mService = Common.getAPI();
        mService.show_Posts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<List<GetPost>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<GetPost> response) {
                        //List<GetPost> body = response.;
                        for (int i = 0 ; i < response.size() ; i++) {
                            recycler_showPost.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                            recycler_showPost.setHasFixedSize(true);
                            GetPost getPost = new GetPost();
                            getPost.setId(response.get(i).getId());
                            getPost.setTitle(response.get(i).getTitle());
                            getPost.setLink(response.get(i).getLink());
                            posts.add(getPost);

                            displayPosts();
                        }

                        for (int i = 0 ; i < response.size();i++) {
                            Log.d("date", response.get(i).getId() + " " + response.get(i).getTitle() + " " + response.get(i).getLink());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("date", e.toString());
                    }
                });


        /*mService = Common.getAPI();
        mService.show_PostsWitDates().enqueue(new Callback<List<Post_Inf>>() {
            @Override
            public void onResponse(Call<List<Post_Inf>> call, Response<List<Post_Inf>> response) {
                List<Post_Inf> body = response.body();
                for (int i = 0 ; i < body.size() ; i++) {
                    recycler_showPost.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                    recycler_showPost.setHasFixedSize(true);
                    Post_Inf inf = new Post_Inf();
                    inf.setId(body.get(i).getId());
                    inf.setTitle(body.get(i).getTitle());
                    inf.setLike(body.get(i).getLike());
                    inf.setPostid(body.get(i).getPostid());
                    post.add(inf);
                    displayPosts();
                }
            }

            @Override
            public void onFailure(Call<List<Post_Inf>> call, Throwable t) {

            }
        });*/
    }

    private void displayPosts() {
        ShowPostAdapter adapter = new ShowPostAdapter(this, posts);
        adapter.notifyDataSetChanged();
        recycler_showPost.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reload,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload:
                Log.d("log", "relad clcik");
                posts.clear();
                getPosts();
                Log.d("token", FirebaseInstanceId.getInstance().getToken());

        }
        return super.onOptionsItemSelected(item);
    }

}
