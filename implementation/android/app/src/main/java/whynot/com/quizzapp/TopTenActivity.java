package whynot.com.quizzapp;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import whynot.com.adapters.TopTenAdapter;
import whynot.com.dto.DtoGameData;

public class TopTenActivity extends AppCompatActivity {

    TopTenAdapter adapter;

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
        SwipeRefreshLayout mContentView = findViewById(R.id.layoutTopTen);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        // Adding Listener
        mContentView.setOnRefreshListener(() -> {
            // Your code here
//            Toast.makeText(getApplicationContext(), "Works!", Toast.LENGTH_LONG).show();
            // To keep animation for 4 seconds
            new Handler().postDelayed(() -> {
                // Stop animation (This will be after 3 seconds)
                mContentView.setRefreshing(false);
            }, 4000); // Delay in millis
        });

        Button btnBack  = mContentView.findViewById(R.id.goBackBtn);
        btnBack.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(new Intent(this, MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        });


        //to delete
        List<DtoGameData> temp = new ArrayList<>(Arrays.asList(
                new DtoGameData("Pierwszy", 1122323),
                new DtoGameData("Drugi", 12331231),
                new DtoGameData("Trzeci", 1212313),
                new DtoGameData("Czwarty", 112323),
                new DtoGameData("Piąty", 123346),
                new DtoGameData("Szósty", 123656),
                new DtoGameData("Siudmy", 12433),
                new DtoGameData("Ósmy", 123),
                new DtoGameData("Dziewiąty", 123),
                new DtoGameData("Dziesiąty", 123)
        ));


        adapter = new TopTenAdapter(this, temp);
        ListView list = mContentView.findViewById(R.id.topTenList);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
