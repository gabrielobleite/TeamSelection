package com.leite.gabriel.teamselection;

public interface IObservable {
    public void addObserver(IObservable observable);
    public void updateObserver();
    public void update();
}
