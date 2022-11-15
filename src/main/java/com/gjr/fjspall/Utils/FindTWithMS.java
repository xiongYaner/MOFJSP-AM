package com.gjr.fjspall.Utils;


public class FindTWithMS {
    public static int[][] correspondingT(int[][] MS) {
        int[][] newT = new int[MS.length][MS[0].length];
        for (int k = 0; k < MS.length; k++) {
            int processCount = 0;
            for (int i = 0; i < InstancesReader.jobNum; i++) {
                for (int j = 0; j < InstancesReader.everyProcessNum[i]; j++) {
                    int pos = BaseMethod.getIndex(InstancesReader.process[i][j], MS[k][processCount]);
                    newT[k][processCount] = InstancesReader.time[i][j][pos];
                    processCount++;
                }
            }
        }
        return newT;
    }

    public static int[] correspondingT(int[] MS) {
        int[] newT = new int[MS.length];
        int processCount = 0;
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            for (int j = 0; j < InstancesReader.everyProcessNum[i]; j++) {
                int pos = BaseMethod.getIndex(InstancesReader.process[i][j], MS[processCount]);
                newT[processCount] = InstancesReader.time[i][j][pos];
                processCount++;
            }
        }
        return newT;
    }

}
