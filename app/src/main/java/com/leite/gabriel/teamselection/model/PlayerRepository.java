package com.leite.gabriel.teamselection.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.List;

public class PlayerRepository {
    private final PlayerDao playerDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private MutableLiveData<Integer> updateResult = new MutableLiveData<>();
    private MutableLiveData<Integer> deleteResult = new MutableLiveData<>();
    private LiveData<List<Player>> listPlayers = new MutableLiveData<>();

    public PlayerRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        playerDao = db.playerDao();
    }

    public void insert(Player player) {
        insertAsync(player);
    }

    public void delete(Player player) {
        deleteAsync(player);
    }

    public void update(Player player) {
        updateAsync(player);
    }

    public LiveData<List<Player>> getListPlayers() {
        return playerDao.getAllPlayers();
    }

    public List<Player> getSelectedPlayers() {
        return playerDao.getSelectedPlayers();
    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    private void insertAsync(final Player player) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    playerDao.insert(player);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }
            }
        }).start();
    }

    private void updateAsync(final Player player) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    playerDao.update(player);
                    updateResult.postValue(1);
                } catch (Exception e) {
                    updateResult.postValue(0);
                }
            }
        }).start();
    }

    public MutableLiveData<Integer> getDeleteResult() {
        return deleteResult;
    }

    private void deleteAsync(final Player player) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    playerDao.delete(player);
                    deleteResult.postValue(1);
                } catch (Exception e) {
                    deleteResult.postValue(0);
                }
            }
        }).start();
    }
}
