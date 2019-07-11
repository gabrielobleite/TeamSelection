package com.leite.gabriel.teamselection.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.leite.gabriel.teamselection.R;
import com.leite.gabriel.teamselection.model.Player;
import com.leite.gabriel.teamselection.model.Role;
import com.leite.gabriel.teamselection.viewmodel.PlayerListViewModel;

public class PlayerEditDialog extends AlertDialog.Builder {

    public PlayerEditDialog(final Activity activity, final Player player, final PlayerListViewModel playerListViewModel) {
        super(activity);
        this.setTitle("Edit Player");

        View view = activity.getLayoutInflater().inflate(R.layout.edit_player, null);

        final EditText editText = view.findViewById(R.id.txtEditName);
        editText.setText(player.getName());
        editText.setSelection(editText.getText().length());

        final Spinner spinner = view.findViewById(R.id.cmbRole);
        ArrayAdapter<Role> list = new ArrayAdapter<Role>(activity, android.R.layout.simple_list_item_1, Role.values());
        spinner.setAdapter(list);

        try {
            spinner.setSelection(list.getPosition(Role.valueOf(player.getRole())));
        } catch (Exception ex) {
            spinner.setSelection(list.getPosition(Role.GO));
        }

        this.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.setName(editText.getText().toString());
                player.setRole(((Role)spinner.getSelectedItem()).name());
                playerListViewModel.update(player);
            }
        });

        this.setNegativeButton("Cancel", null);

        this.setView(view);

        AlertDialog alertToShow = this.create();
        alertToShow.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertToShow.show();
    }
}
