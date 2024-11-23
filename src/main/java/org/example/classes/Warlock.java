package org.example.classes;

import org.example.model.GameClass;

public class Warlock extends GameClass {

    private int invocations;

    public Warlock(int level) {
        super(level);
    }

    public int getInvocations() {
        return invocations;
    }

    public void setInvocations(int invocations) {
        this.invocations = invocations;
    }
}