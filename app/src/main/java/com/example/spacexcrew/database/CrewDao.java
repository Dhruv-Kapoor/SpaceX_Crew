package com.example.spacexcrew.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spacexcrew.dataClasses.Crew;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface CrewDao {

    @Query("select * from Crew")
    LiveData<List<Crew>> getCrew();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<Void> addCrew(Crew... crew);

    @Update
    ListenableFuture<Void> updateCrew(Crew crew);

    @Delete
    ListenableFuture<Void> deleteCrewDetail(Crew crew);

    @Query("delete from Crew")
    ListenableFuture<Void> deleteAllCrewData();
}
