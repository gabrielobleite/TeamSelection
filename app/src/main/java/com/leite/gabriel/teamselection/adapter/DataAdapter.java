package com.leite.gabriel.teamselection.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leite.gabriel.teamselection.R;
import com.leite.gabriel.teamselection.databinding.ItemDataBinding;
import com.leite.gabriel.teamselection.model.Player;
import com.leite.gabriel.teamselection.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private static final String TAG = "DataAdapter";
    private List<Player> data;
    Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public DataAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,
                new FrameLayout(parent.getContext()), false);

        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Player player = data.get(position);
        holder.setViewModel(new PlayerViewModel(player, position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public void onViewAttachedToWindow(DataViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(DataViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<Player> data) {
        if (data == null || data.isEmpty()) {
            this.data.clear();
        } else {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    public interface Callback {
        void onClick(View v, Player player);
        void onDelete(View v, Player player);
        void onEdit(View v, Player player);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        ItemDataBinding binding;

        DataViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = getAdapterPosition();
                    final Player player = data.get(i);
                    callback.onClick(v, player);
                }
            });

            ImageButton btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = getAdapterPosition();
                    final Player player = data.get(i);
                    callback.onDelete(v, player);
                }
            });

            ImageButton btnEdit = itemView.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = getAdapterPosition();
                    final Player player = data.get(i);
                    callback.onEdit(v, player);
                }
            });

            bind();

            TextView txtName = itemView.findViewById(R.id.title_tv);
            String name = getAdapterPosition() + " - " + txtName.getText();
            txtName.setText(name);

        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(PlayerViewModel viewModel) {
            if (binding != null) {
                binding.setViewModel(viewModel);
            }
        }
    }
}
