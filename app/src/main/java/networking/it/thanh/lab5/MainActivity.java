package networking.it.thanh.lab5;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import networking.it.thanh.lab5.adapter.MsgAdapter;
import networking.it.thanh.lab5.model.Chat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView avtss;
    TextView msgs;
    MsgAdapter msgAdapter;
    List<Chat> chatList;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;
    private LinearLayoutManager linearLayoutManager;

    private int page = 1;
    private int per_page = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        avtss = findViewById(R.id.avts);
        msgs = findViewById(R.id.show_msg);
        chatList = new ArrayList<>();
        msgAdapter = new MsgAdapter(this, chatList);
        recyclerView = findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(msgAdapter);
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                chatList.clear();
                getData(page, per_page);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getData(page + 1, per_page);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    public void getData(int page, int per_page) {

        PolyRetrofit.getInstance().getCategories(page, per_page).
                enqueue(new Callback<List<Chat>>() {
                    @Override
                    public void onResponse(Call<List<Chat>> call,
                                           Response<List<Chat>> response) {
                        swipe.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),response.body().size()+"",Toast.LENGTH_LONG).show();

                        if (response.body().size() == 0){
                            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                @Override
                                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                                }
                            });

                            msgAdapter.setOnLoadMore(false);

                        }

                        chatList.addAll(response.body());
                        msgAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<Chat>> call, Throwable t) {
                        swipe.setRefreshing(false);
                    }
                });

    }
}
