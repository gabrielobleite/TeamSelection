package com.leite.gabriel.teamselection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.leite.gabriel.teamselection.R;
import com.leite.gabriel.teamselection.model.Player;
import com.leite.gabriel.teamselection.model.PlayerRepository;
import com.leite.gabriel.teamselection.model.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.stream.Stream;

public class PlayerListViewModel extends AndroidViewModel {
    private PlayerRepository playerRepository;
    private LiveData<Integer> insertResult;
    private LiveData<Integer> deleteResult;
    private LiveData<List<Player>> data;
    private static Random randomGenerator = new Random();

    public PlayerListViewModel(@NonNull Application application) {
        super(application);
        playerRepository = new PlayerRepository(application);
        insertResult = playerRepository.getInsertResult();
        deleteResult = playerRepository.getDeleteResult();
        data = playerRepository.getListPlayers();
    }

    public void setUp() {

    }

    public void select(Player player) {
        if(player.getImage()==0) {
            player.setImage(R.drawable.check);
        } else {
          player.setImage(0);
        }
        playerRepository.update(player);
    }

    public void update(Player player) {
        playerRepository.update(player);
    }

    public void sortTeams() {
        int totalCheck = 0;
        int imageSelected = R.drawable.selected;
        int imageCheck = R.drawable.check;

        List<Player> lst = playerRepository.getSelectedPlayers();

        int numSelect = lst.size() / 2;

        for(int i = 0; i < lst.size(); i++)
            if(lst.get(i).getImage() == imageSelected) {
                lst.get(i).setImage(imageCheck);
                playerRepository.update(lst.get(i));
            }

        for(int i = 0; i < lst.size(); i++)
            if(lst.get(i).getImage() == imageCheck)
                totalCheck++;

        if(totalCheck < numSelect) return;

        ListSearch filter = new ListSearch();

        List<Player> lstSort = new ArrayList<>();
        for(Role r: Role.values()) {
            lstSort.addAll(filter.ByRole(lst, r.name()));
            int totSort = lstSort.size() / 2;
            for(int i = 0; i < totSort; i++) {
                Player model = new Player();

                // Selected Team
                int index = randomGenerator.nextInt(lstSort.size());
                model = lstSort.remove(index);
                model.setImage(imageSelected);
                playerRepository.update(model);

                // Check Team
                index = randomGenerator.nextInt(lstSort.size());
                lstSort.remove(index);
            }
        }
    }

    public void insert(Player player) {
        playerRepository.insert(player);
    }

    public void delete(Player player) {
        playerRepository.delete(player);
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<Integer> getDeleteResult() {
        return deleteResult;
    }

    public LiveData<List<Player>> getData() {
        return data;
    }
}
