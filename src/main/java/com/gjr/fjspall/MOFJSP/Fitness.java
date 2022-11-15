package com.gjr.fjspall.MOFJSP;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;

import java.util.Arrays;


public class Fitness {

    public static int[] WM(int[][] MS, int[][] T) {
        int[] WM = new int[MS.length];
        int[] machineLoad = new int[InstancesReader.machineNum];
        for (int i = 0; i < MS.length; i++) {
            Arrays.fill(machineLoad, 0);
            for (int j = 0; j < MS[0].length; j++) {
                machineLoad[MS[i][j] - 1] += T[i][j];
            }
            WM[i] = BaseMethod.max(machineLoad);
        }
        return WM;
    }

    public static int[] WT(int[][] T) {
        int[] WT = new int[T.length];
        for (int i = 0; i < T.length; i++) {
            WT[i] = Arrays.stream(T[i]).sum();
        }
        return WT;
    }

}
