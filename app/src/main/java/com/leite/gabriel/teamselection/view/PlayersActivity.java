package com.leite.gabriel.teamselection.view;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leite.gabriel.teamselection.R;
import com.leite.gabriel.teamselection.model.Player;
import com.leite.gabriel.teamselection.viewmodel.PlayerListViewModel;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PlayersActivity extends AppCompatActivity implements ListFragment.Callback {

    private PlayerListViewModel playerListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players_activity);
        if (savedInstanceState == null) {
            ListFragment listFragment = new ListFragment();
            listFragment.setCallback(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, listFragment)
                    .commitNow();
        }

        final EditText txtName = findViewById(R.id.txtName);
        final Button btnAdd = findViewById(R.id.btnAdd);
        final Button btnSort = findViewById(R.id.btnSort);
        final TextView txtPlayers = findViewById(R.id.lblPlayers);

        playerListViewModel = ViewModelProviders.of(this).get(PlayerListViewModel.class);

        playerListViewModel.getInsertResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer result) {
                if (result == 1) {
                    Toast.makeText(PlayersActivity.this, "Player successfully saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlayersActivity.this, "Error saving player", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerListViewModel.getDeleteResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer result) {
                if (result == 1) {
                    Toast.makeText(PlayersActivity.this, "Player successfully removed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlayersActivity.this, "Error removing player", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerListViewModel.getData().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> playerList) {
                int total = playerList.size();
                int selected = 0;

                for(Player player: playerList) {
                    if(player.getImage() != 0)
                        selected++;
                }

                txtPlayers.setText(selected + " / " + total);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] names = txtName.getText().toString().split("\n");
                for(String name: names) {
                    String finalName = name;
                    if(name.contains("-")) {
                        String[] str = name.split("-");
                        finalName = str[str.length - 1];
                    }

                    Player player = new Player();
                    player.setName(StringUtils.capitalize(finalName.trim()));
                    if(StringUtils.isNotEmpty(player.getName()))
                        playerListViewModel.insert(player);
                }
                txtName.setText("");
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            playerListViewModel.sortTeams();
                        } catch (Exception e) {

                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public void onClick(View v, Player player) {
        playerListViewModel.select(player);
    }

    @Override
    public void onDelete(View v, Player player) {
        final Player _player = player;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerListViewModel.delete(_player);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure?");

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onEdit(View v, Player player) {
        PlayerEditDialog playerEditDialog = new PlayerEditDialog(this, player, playerListViewModel);
    }
}
