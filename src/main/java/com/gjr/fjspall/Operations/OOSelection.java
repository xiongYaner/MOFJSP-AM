package com.gjr.fjspall.Operations;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.normativeDesign.OperationForSelection;


import java.util.Random;

public class OOSelection implements OperationForSelection {
    int[][] newMS;
    int[][] newOS;
    int[][] newT;
    int[] newFit;

    public void select(int[][] OS, int[][] MS, int[][] T, int[] fit) {
        newMS = new int[OS.length][OS[0].length];
        newOS = new int[OS.length][OS[0].length];
        newT = new int[OS.length][OS[0].length];
        newFit = new int[fit.length];
        int player1;
        int player2;
        int player3;
        int[] all;
        int pos;
        int playPos = 0;
        for (int i = 0; i < OS.length; i++) {
            player1 = new Random().nextInt(OS.length);
            player2 = new Random().nextInt(OS.length);
            player3 = new Random().nextInt(OS.length);
            all = new int[]{fit[player1], fit[player2], fit[player3]};
            pos = BaseMethod.indexOfMin(all);
            switch (pos + 1) {
                case 1:
                    playPos = player1;
                    break;
                case 2:
                    playPos = player2;
                    break;
                case 3:
                    playPos = player3;
                    break;
            }
            System.arraycopy(OS[playPos], 0, newOS[i], 0, newOS[i].length);
            System.arraycopy(MS[playPos], 0, newMS[i], 0, newMS[i].length);
            System.arraycopy(T[playPos], 0, newT[i], 0, newT[i].length);
            newFit[i] = fit[playPos];
        }
    }

    @Override
    public void select() {

    }

    public int[][] getNewMS() {
        return newMS;
    }

    public void setNewMS(int[][] newMS) {
        this.newMS = newMS;
    }

    public int[][] getNewOS() {
        return newOS;
    }

    public void setNewOS(int[][] newOS) {
        this.newOS = newOS;
    }

    public int[][] getNewT() {
        return newT;
    }

    public void setNewT(int[][] newT) {
        this.newT = newT;
    }

    public int[] getNewFit() {
        return newFit;
    }

    public void setNewFit(int[] newFit) {
        this.newFit = newFit;
    }
}
