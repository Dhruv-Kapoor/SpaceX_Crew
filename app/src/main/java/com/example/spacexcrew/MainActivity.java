package com.example.spacexcrew;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacexcrew.database.CrewDatabase;
import com.example.spacexcrew.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private CrewDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.toolbar);

        database = CrewDatabase.getInstance(this);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityViewModel.class);

        binding.setViewModel(viewModel);
        binding.setListener(this);
        binding.executePendingBindings();

        viewModel.init(database);

        binding.rvCrew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstVisiblePosition = lm.findFirstVisibleItemPosition();
                    if (firstVisiblePosition > 0) {
                        binding.fabScrollUp.setVisibility(View.VISIBLE);
                    } else {
                        binding.fabScrollUp.setVisibility(View.GONE);
                    }
                }
            }
        });

        viewModel.getToast().observe(this, s -> {
            if (s.length() > 0)
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemDelete) {
            showDeleteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete Everything")
                .setMessage("All data will be deleted and will be loaded on next refresh.")
                .setNegativeButton("Cancel", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .setPositiveButton("Delete", (dialog1, which) -> {
                    database.crewDao().deleteAllCrewData();
                })
                .create();
        dialog.show();
    }

    @Override
    public void finishAfterTransition() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabScrollUp) {
            binding.rvCrew.smoothScrollToPosition(0);
        }
    }
}