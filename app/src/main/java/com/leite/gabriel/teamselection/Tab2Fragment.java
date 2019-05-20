package com.leite.gabriel.teamselection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class Tab2Fragment extends Fragment implements IObservable {
    private static final String TAG = "Tab2Fragment";

    private Button btnAdd;
    private EditText txtQtd;

    private ListView lstview;
    private CustomAdapter customAdapter;
    private ArrayList<IObservable> lstObservers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        lstview = (ListView) view.findViewById(R.id.lstSorteio);
        customAdapter = new CustomAdapter(getActivity());
        lstview.setAdapter(customAdapter);

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Repository.toggle(position);
                    customAdapter.update();
                    updateObserver();
                    System.out.println("Position " + position);
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                    Toast.makeText(getActivity(), "Erro ao tentar atualizar Observers.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtQtd = view.findViewById(R.id.txtQtd);
        btnAdd = view.findViewById(R.id.btnSortear);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qtd = txtQtd.getText().toString();
                if(qtd.matches("\\d+(?:\\.\\d+)?")) {
                    try {
                        System.out.println("Matches");
                        Repository.selectRandom(Integer.parseInt(qtd));
                        customAdapter.update();
                        updateObserver();
                    } catch (Exception e) {
                        // This will catch any exception, because they are all descended from Exception
                        System.out.println("Error " + e.getMessage());
                        Toast.makeText(getActivity(), "Erro ao tentar sortear.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    System.out.println("No Match");
                    Toast.makeText(getActivity(), "Selecione o número de jogadores.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public void addObserver(IObservable observable){
        if(lstObservers == null)
            lstObservers = new ArrayList<>();
        lstObservers.add(observable);
    }

    public void updateObserver(){
        for(int i = 0; i < lstObservers.size(); i++){
            lstObservers.get(i).update();
        }
    }

    public void update(){
        customAdapter.update();
    }
}
