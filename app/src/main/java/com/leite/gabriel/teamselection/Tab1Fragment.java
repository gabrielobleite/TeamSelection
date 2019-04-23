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

public class Tab1Fragment extends Fragment implements IObservable {
    private static final String TAG = "Tab1Fragment";

    private Button btnAdd;
    private EditText txtName;

    private ListView lstview;
    private CustomAdapter customAdapter;

    private ArrayList<IObservable> lstObservers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        lstview = (ListView) view.findViewById(R.id.listview);
        customAdapter = new CustomAdapter(getActivity());
        lstview.setAdapter(customAdapter);

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(lstview),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                if(Repository.getModel(position).getName().equals("Lista Vazia"))
                                    return false;
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                customAdapter.remove(position);
                            }
                        });

        lstview.setOnTouchListener(touchListener);
        lstview.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtName = view.findViewById(R.id.txtName);
        btnAdd = (Button) view.findViewById(R.id.btnTEST);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = new Model();
                model.setName(txtName.getText().toString());
                Repository.addModel(model);
                customAdapter.update();
                txtName.setText("");
                updateObserver();
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
