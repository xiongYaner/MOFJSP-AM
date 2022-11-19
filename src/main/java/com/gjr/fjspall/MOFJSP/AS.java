package com.gjr.fjspall.MOFJSP;

import java.util.ArrayList;

public class AS {
    private ArrayList<int[]> MS = new ArrayList<int[]>();
    private ArrayList<int[]> OS = new ArrayList<int[]>();
    private ArrayList<int[]> T = new ArrayList<int[]>();
    private ArrayList<Integer> ms = new ArrayList<>();
    private ArrayList<Integer> wt = new ArrayList<>();
    private ArrayList<Integer> wm = new ArrayList<>();

    public AS() {
    }

    public ArrayList<int[]> getMS() {
        return MS;
    }

    public void setMS(ArrayList<int[]> MS) {
        this.MS = MS;
    }

    public ArrayList<int[]> getOS() {
        return OS;
    }

    public void setOS(ArrayList<int[]> OS) {
        this.OS = OS;
    }

    public ArrayList<int[]> getT() {
        return T;
    }

    public void setT(ArrayList<int[]> t) {
        T = t;
    }

    public ArrayList<Integer> getMs() {
        return ms;
    }

    public void setMs(ArrayList<Integer> ms) {
        this.ms = ms;
    }

    public ArrayList<Integer> getWt() {
        return wt;
    }

    public void setWt(ArrayList<Integer> wt) {
        this.wt = wt;
    }

    public ArrayList<Integer> getWm() {
        return wm;
    }

    public void setWm(ArrayList<Integer> wm) {
        this.wm = wm;
    }

    @Override
    public String toString() {
        return "makespan=" + ms +
                ", Total machine load=" + wt +
                ", Maximum machine load=" + wm
                ;
    }
}
