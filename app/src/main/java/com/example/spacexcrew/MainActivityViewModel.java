package com.example.spacexcrew;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;

import com.example.spacexcrew.dataClasses.Crew;
import com.example.spacexcrew.database.CrewDatabase;
import com.example.spacexcrew.network.SpaceXClient;
import com.example.spacexcrew.util.CrewDiffUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<List<Crew>> crewList = new MutableLiveData<>();
    MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>();
    MutableLiveData<String> showToast = new MutableLiveData<>();
    Boolean initialised = false;

    CrewDatabase database;
    Observer<List<Crew>> databaseObserver = crews -> {
        crewList.postValue(crews);
    };

    public void init(CrewDatabase database) {
        showToast.postValue("");
        if (initialised) return;
        initialised = true;
        this.database = database;
        database.crewDao().getCrew().observeForever(databaseObserver);
        refresh();
    }

    public void refresh() {
        isRefreshing.postValue(true);
        SpaceXClient.api.getCrew().enqueue(new Callback<List<Crew>>() {
            @Override
            public void onResponse(Call<List<Crew>> call, Response<List<Crew>> response) {
                if (response.code() / 100 == 2 && response.body() != null) {
                    updateDatabase(response.body());
                } else {
                    showToast.postValue("Error: " + response.code());
                }
                isRefreshing.postValue(false);
            }

            @Override
            public void onFailure(Call<List<Crew>> call, Throwable t) {
                t.printStackTrace();
                showToast.postValue("Unable to refresh");
                isRefreshing.postValue(false);
            }
        });
    }

    private void updateDatabase(List<Crew> newList) {
        CrewDiffUtil crewDiffUtil = new CrewDiffUtil(crewList.getValue(), newList);
        DiffUtil.calculateDiff(crewDiffUtil).dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                database.crewDao().addCrew(newList.subList(position, position + count).toArray(new Crew[0]));
            }

            @Override
            public void onRemoved(int position, int count) {
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
            }

            @Override
            public void onChanged(int position, int count, @Nullable Object payload) {
                for (int i = position; i < position + count; i++) {
                    database.crewDao().updateCrew(newList.get(position));
                }
            }
        });
    }

    public LiveData<List<Crew>> getCrewList() {
        return crewList;
    }

    public LiveData<Boolean> getIsRefreshing() {
        return isRefreshing;
    }

    public LiveData<String> getToast() {
        return showToast;
    }

    @Override
    protected void onCleared() {
        if (database != null) {
            database.crewDao().getCrew().removeObserver(databaseObserver);
        }
        super.onCleared();
    }
}
