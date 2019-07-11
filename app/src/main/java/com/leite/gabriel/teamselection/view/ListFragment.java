package com.leite.gabriel.teamselection.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leite.gabriel.teamselection.R;
import com.leite.gabriel.teamselection.adapter.DataAdapter;
import com.leite.gabriel.teamselection.databinding.FragmentListBinding;
import com.leite.gabriel.teamselection.model.Player;
import com.leite.gabriel.teamselection.viewmodel.PlayerListViewModel;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements DataAdapter.Callback {
    private PlayerListViewModel playerListViewModel;
    Callback callback;

    public ListFragment() {

    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);

        playerListViewModel = ViewModelProviders.of(this).get(PlayerListViewModel.class);

        binding.setViewModel(playerListViewModel);
        View view = binding.getRoot();

        RecyclerView recyclerView = view.findViewById(R.id.data_recycler_view);
        final DataAdapter dataAdapter = new DataAdapter();
        dataAdapter.setCallback(this);
        playerListViewModel.getData().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                dataAdapter.updateData(players);
            }
        });

        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), VERTICAL));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        playerListViewModel.setUp();
    }

    @Override
    public void onPause() {
        super.onPause();
        //playerListViewModel.tearDown();
    }

    public interface Callback {
        void onClick(View v, Player player);
        void onDelete(View v, Player player);
        void onEdit(View v, Player player);
    }

    @Override
    public void onClick(View v, Player player) {
        callback.onClick(v, player);
    }

    @Override
    public void onDelete(View v, Player player) {
        callback.onDelete(v, player);
    }

    @Override
    public void onEdit(View v, Player player) {
        callback.onEdit(v, player);
    }
}
