package com.gjr.fjspall.Operations;


import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.Utils.SwitchOfPosAndJob;

import java.util.ArrayList;
import java.util.Random;

public class HSMForFJSP {
    double probability = 1;



    public int[][] runWithT(int[][] MS, int[][] T, int[] fit) {
        int[][] newMS = new int[MS.length][InstancesReader.allProcess];
        BaseMethod.arrayCopy(MS, newMS);
        int[][] newT = new int[MS.length][InstancesReader.allProcess];
        BaseMethod.arrayCopy(T, newT);
        int[][][] process = InstancesReader.process;
        int[][][] time = InstancesReader.time;
        for (int i = 0; i < MS.length; i++) {
            if (Math.random() <= probability) {
                /*计算各机器负载率*/
                int[] machineLoad = new int[InstancesReader.machineNum];
                for (int j = 0; j < MS[0].length; j++) {
                    machineLoad[MS[i][j] - 1] += T[i][j];
                }
                double[] loadRate = new double[InstancesReader.machineNum];
                for (int j = 0; j < InstancesReader.machineNum; j++) {
                    loadRate[j] = (double) machineLoad[j] / (double) fit[i];
                }
                int highestMachine = BaseMethod.indexOfMax(loadRate) + 1;
                ArrayList<Integer> pos = new ArrayList<>();
                pos = BaseMethod.findAll(MS[i], highestMachine);
                int machineChooseProcess = new Random().nextInt(pos.size());
                int[] jobAndProcess = SwitchOfPosAndJob.back(pos.get(machineChooseProcess));
                while (pos.size() != 0 && process[jobAndProcess[0] - 1][jobAndProcess[1] - 1].length == 1) {
                    pos.remove(machineChooseProcess);
                    if (pos.size() != 0) {
                        machineChooseProcess = new Random().nextInt(pos.size());
                        jobAndProcess = SwitchOfPosAndJob.back(pos.get(machineChooseProcess));
                    }
                }
                if (pos.size() == 0) {
                    continue;
                }
                int jobNum = jobAndProcess[0];
                int processNum = jobAndProcess[1];
                int minPos = BaseMethod.indexOfMin(time[jobNum - 1][processNum - 1]);
                int machineNum = process[jobNum - 1][processNum - 1][minPos];
                int timeNum = time[jobNum - 1][processNum - 1][minPos];
                newMS[i][pos.get(machineChooseProcess)] = machineNum;
                newT[i][pos.get(machineChooseProcess)] = timeNum;
            }
        }
        return newMS;
    }
}
