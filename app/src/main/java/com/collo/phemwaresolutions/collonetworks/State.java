package com.collo.phemwaresolutions.collonetworks;

/**
 * Created by EntralogIT on 2017-02-28.
 */

public class State {
    public State(String i, String name) {
        state = name;
        id = i;
    }

    public State() {

    }


    public String getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String state;
    private String id;
}
