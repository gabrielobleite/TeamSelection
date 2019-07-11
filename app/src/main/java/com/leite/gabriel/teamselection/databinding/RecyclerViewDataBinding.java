package com.leite.gabriel.teamselection.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.leite.gabriel.teamselection.adapter.DataAdapter;
import com.leite.gabriel.teamselection.model.Player;

import java.util.List;

public class RecyclerViewDataBinding {
    /**
     * Binds the data to the {@link android.support.v7.widget.RecyclerView.Adapter}, sets the
     * recycler view on the adapter, and performs some other recycler view initialization.
     *
     * @param recyclerView passed in automatically with the data binding
     * @param adapter      must be explicitly passed in
     * @param data         must be explicitly passed in
     */
    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(RecyclerView recyclerView, DataAdapter adapter, List<Player> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }
}
