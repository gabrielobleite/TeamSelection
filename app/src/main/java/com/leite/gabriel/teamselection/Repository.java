package com.leite.gabriel.teamselection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class Repository {
    private static Context context;
    private static SharedPreferences settings;
    private static int imageCheck = R.drawable.check;
    private static int imageSelected = R.drawable.selected;
    private static ArrayList<Model> modelArrayList;
    private static Random randomGenerator = new Random();

    public static void setContext(Context _context){
        context = _context;
    }

    private static void SaveList() {
        settings = context.getSharedPreferences("REPOSITORY", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        try {
            String lst = ObjectSerializer.serialize(modelArrayList);
            editor.putString("LIST", lst);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    private static void GetList() {
        settings = context.getSharedPreferences("REPOSITORY", MODE_PRIVATE);

        try {
            String lst = settings.getString("LIST", ObjectSerializer.serialize(new ArrayList<Model>()));
            modelArrayList = (ArrayList<Model>) ObjectSerializer.deserialize(lst);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Model> getModelArrayList() {
        if(modelArrayList == null) GetList();
        if(modelArrayList.size() == 0) {
            Model md = new Model();
            md.setName("Lista Vazia");
            modelArrayList.add(md);
        }
        return modelArrayList;
    }

    public static Model getModel(int position) {
        getModelArrayList();
        return modelArrayList.get(position);
    }

    public static void addModel(Model modelItem) {
        getModelArrayList();
        if(modelArrayList.size() == 1)
            if(modelArrayList.get(0).getName().equals("Lista Vazia"))
                modelArrayList.remove(0);
        modelArrayList.add(modelItem);

        SaveList();
    }

    public static void removeModel(Model modelItem) {
        getModelArrayList();
        modelArrayList.remove(modelItem);
        if(modelArrayList.size() == 0)
        {
            Model md = new Model();
            md.setName("Lista Vazia");
            modelArrayList.add(md);
        }

        SaveList();
    }

    public static void toggle(int position) {
        if(modelArrayList == null) return;
        if(modelArrayList.size() <= position) return;
        if(modelArrayList.size() == 1)
            if(modelArrayList.get(0).getName().equals("Lista Vazia"))
                return;

        if(modelArrayList.get(position).getImage_drawable() == 0)
            modelArrayList.get(position).setImage_drawable(imageCheck);
        else
            modelArrayList.get(position).setImage_drawable(0);

        SaveList();
    }

    public static void selectRandom(int numSelect)
    {
        int totalCheck = 0;

        for(int i = 0; i < modelArrayList.size(); i++)
            if(modelArrayList.get(i).getImage_drawable() == imageSelected)
                modelArrayList.get(i).setImage_drawable(imageCheck);

        for(int i = 0; i < modelArrayList.size(); i++)
            if(modelArrayList.get(i).getImage_drawable() == imageCheck)
                totalCheck++;

        if(totalCheck < numSelect) return;

        for(int i = 0; i < numSelect; i++) {
            Model model = new Model();
            while (model.getImage_drawable() == 0 || model.getImage_drawable() == imageSelected) {
                int index = randomGenerator.nextInt(modelArrayList.size());
                model = modelArrayList.get(index);
            }
            model.setImage_drawable(imageSelected);
        }

        SaveList();
    }
}
