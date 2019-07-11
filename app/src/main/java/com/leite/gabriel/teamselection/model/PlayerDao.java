package com.leite.gabriel.teamselection.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert
    void insert(Player player);

    @Delete
    void delete(Player player);

    @Update
    void update(Player player);

    @Query("select * from Player order by image DESC, name COLLATE NOCASE")
    LiveData<List<Player>> getAllPlayers();

    @Query("select * from Player where image <> 0 order by name COLLATE NOCASE")
    List<Player> getSelectedPlayers();
}
