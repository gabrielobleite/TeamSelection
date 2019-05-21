package com.leite.gabriel.teamselection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Model> ModelArrayList;

    public CustomAdapter(Context context) {

        this.context = context;
        this.ModelArrayList = Repository.getModelArrayList();
    }

    public void remove(int position) {
        try {
            Repository.removeModel(ModelArrayList.get(position));
            ModelArrayList = Repository.getModelArrayList();
            notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(context, "Erro ao tentar atualizar a Grid.", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(){
        try {
            ModelArrayList = Repository.getModelArrayList();
            notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(context, "Erro ao tentar atualizar a Grid.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        if (position < getViewTypeCount())
            return position;
        else
            return getViewTypeCount() - 1;
    }

    @Override
    public int getCount() {
        return ModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listitem, null, true);

                holder.lblName = convertView.findViewById(R.id.lblName);
                holder.imgCheck = convertView.findViewById(R.id.imgCheck);

                convertView.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ViewHolder) convertView.getTag();
            }

            holder.lblName.setText(Integer.toString(position+1) + " - " + ModelArrayList.get(position).getName());
            holder.imgCheck.setImageResource(ModelArrayList.get(position).getImage_drawable());

            return convertView;
        } catch (Exception e) {
            return convertView;
        }
    }

    private class ViewHolder {

        protected TextView lblName;
        private ImageView imgCheck;
    }

}
