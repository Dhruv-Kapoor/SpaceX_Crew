package com.example.spacexcrew;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.spacexcrew.dataClasses.Crew;
import com.example.spacexcrew.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    public static final String CREW_DATA_KEY = "crewData";
    public ActivityDetailsBinding binding;
    private Crew crew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crew = getIntent().getParcelableExtra(CREW_DATA_KEY);
        if(crew==null){
            throw new RuntimeException("Crew data must be sent through intent");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        binding.setCrew(crew);

        WebViewClient client = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressBar.setVisibility(View.GONE);
            }
        };

        binding.setClient(client);
        binding.executePendingBindings();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemOpenBrowser){
            startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(crew.getWikipedia()))
            );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}