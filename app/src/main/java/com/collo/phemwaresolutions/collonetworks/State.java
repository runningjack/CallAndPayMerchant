package com.collo.phemwaresolutions.collonetworks;

/**
 * Created by EntralogIT on 2017-02-28.
 */

public class State {
    private String states;
    private String id;


    public State(String i, String name) {
        states = name;
        id = i;
    }

    public State() {

    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
