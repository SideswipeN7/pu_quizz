package whynot.com.quizzapp;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import whynot.com.adapters.TopTenAdapter;
import whynot.com.app.App;

public class TopTenActivity extends AppCompatActivity {

    private TopTenAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_top_ten);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        LinearLayout mContentView = findViewById(R.id.layoutTopTen);
        swipeRefreshLayout = mContentView.findViewById(R.id.topTenRefresh);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            App.getInstance().getTopTen(this);
        });

        Button btnBack = mContentView.findViewById(R.id.goBackBtn);
        btnBack.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(new Intent(this, MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        });

        adapter = new TopTenAdapter(this, App.getInstance().getTopTen());
        ListView list = mContentView.findViewById(R.id.topTenList);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void refreshList() {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setList(App.getInstance().getTopTen());
        adapter.notifyDataSetChanged();
    }
}// class TopTenActivity
