package com.leite.gabriel.teamselection.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.leite.gabriel.teamselection.model.Player;
import com.leite.gabriel.teamselection.model.Role;

public class PlayerViewModel extends BaseObservable {
    private Player player;
    private String positionNo;

    public PlayerViewModel(Player player, int position) {
        this.player = player;
        this.positionNo = Integer.toString(position + 1) + " - ";
    }

    public void setUp() {
        // perform set up tasks, such as adding listeners
    }

    public void tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    public String getName() {
        return !TextUtils.isEmpty(player.getName()) ? player.getName() : "";
    }

    @Bindable
    public String getRole() {
        return !TextUtils.isEmpty(player.getRole()) ? Role.valueOf(player.getRole()).toString() : "";
    }

    @Bindable
    public int getImage() {
        return player.getImage();
    }

    @Bindable
    public String getPositionNo() {
        return positionNo;
    }
}
