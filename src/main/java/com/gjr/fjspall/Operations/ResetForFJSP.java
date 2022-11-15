package com.gjr.fjspall.Operations;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;

import java.util.Arrays;
import java.util.Random;


public class ResetForFJSP {
    int jobNum = InstancesReader.jobNum;
    int machineNum = InstancesReader.machineNum;
    int[][][] process = InstancesReader.process;
    int[][][] time = InstancesReader.time;
    int[] everyProcessNum = InstancesReader.everyProcessNum;
    int allProcess = InstancesReader.allProcess;


    public void resetOSRandom(int[] OS) {
        System.arraycopy(BaseMethod.randomDisturb(OS), 0, OS, 0, allProcess);
    }


    public void reset(int[] OS, int[] MS, int[] T) {
        resetOSRandom(OS);
        Random random = new Random();
        int i = random.nextInt(99);
        if (i < 10) {
            resetByAllMin(MS, T);
        } else if (i < 20) {
            resetByPartMin(MS, T);
        } else if (i >= 30 && i < 70) {
            resetByGS(MS, T);
        } else if (i >= 70 && i < 80) {
            resetByLS(MS, T);
        } else if (i >= 80) {
            resetByRS(MS, T);
        }
    }

    public void resetByRS(int[] MS, int[] T) {
        int processCount = 0;
        for (int k1 = 0; k1 < jobNum; ++k1) {
            for (int k2 = 0; k2 < everyProcessNum[k1]; k2++) {
                int tempPos = new Random().nextInt(time[k1][k2].length);
                MS[processCount] = process[k1][k2][tempPos];
                T[processCount] = time[k1][k2][tempPos];
                processCount++;
            }
        }
    }

    public void resetByGS(int[] MS, int[] T) {
        int[] machineAllLoad = new int[machineNum];
        int[] jobPositive = new int[jobNum];
        int[] tempMachineAllLoad = new int[machineNum];
        for (int i = 0; i < jobNum; i++) {
            jobPositive[i] = i + 1;
        }
        Arrays.fill(machineAllLoad, 0);
        int[] job = BaseMethod.randomDisturb(jobPositive);
        for (int i = 0; i < jobNum; i++) {
            for (int j = 0; j < everyProcessNum[job[i] - 1]; j++) {
                Arrays.fill(tempMachineAllLoad, Integer.MAX_VALUE);
                for (int k = 0; k < time[job[i] - 1][j].length; k++) {
                    tempMachineAllLoad[process[job[i] - 1][j][k] - 1] = machineAllLoad[process[job[i] - 1][j][k] - 1];
                    tempMachineAllLoad[process[job[i] - 1][j][k] - 1] += time[job[i] - 1][j][k];
                }
                int minCost = BaseMethod.min(tempMachineAllLoad);
                int machinePos = BaseMethod.indexOfMin(tempMachineAllLoad);
                int pos = BaseMethod.getIndex(process[job[i] - 1][j], machinePos + 1);
                machineAllLoad[machinePos] = minCost;
                if (job[i] - 1 == 0) {
                    MS[j] = machinePos + 1;
                    T[j] = time[job[i] - 1][j][pos];
                } else {
                    int beforeProcess = 0;
                    for (int z1 = 0; z1 < job[i] - 1; z1++) {
                        beforeProcess += everyProcessNum[z1];
                    }
                    MS[beforeProcess + j] = machinePos + 1;
                    T[beforeProcess + j] = time[job[i] - 1][j][pos];
                }
            }
        }
    }

    public void resetByLS(int[] MS, int[] T) {
        int[] machineAllLoad = new int[machineNum];
        int[] tempMachineAllLoad = new int[machineNum];
        int countProcess = 0;
        for (int k1 = 0; k1 < jobNum; k1++) {
            Arrays.fill(machineAllLoad, 0);
            for (int k2 = 0; k2 < everyProcessNum[k1]; k2++) {
                Arrays.fill(tempMachineAllLoad, Integer.MAX_VALUE);
                for (int k3 = 0; k3 < time[k1][k2].length; k3++) {
                    tempMachineAllLoad[process[k1][k2][k3] - 1] = machineAllLoad[process[k1][k2][k3] - 1];
                    tempMachineAllLoad[process[k1][k2][k3] - 1] += time[k1][k2][k3];
                }
                int minCost = BaseMethod.min(tempMachineAllLoad);
                int machinePos = BaseMethod.indexOfMin(tempMachineAllLoad);
                int pos = BaseMethod.getIndex(process[k1][k2], machinePos + 1);
                machineAllLoad[machinePos] = minCost;
                MS[countProcess] = machinePos + 1;
                T[countProcess] = time[k1][k2][pos];
                countProcess++;
            }
        }
    }

    public void resetByAllMin(int[] MS, int[] T) {
        int processCount = 0;
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            for (int j = 0; j < InstancesReader.everyProcessNum[i]; j++) {
                int pos = BaseMethod.indexOfMin(time[i][j]);
                MS[processCount] = process[i][j][pos];
                T[processCount] = time[i][j][pos];
                processCount++;
            }
        }
    }

    public void resetByPartMin(int[] MS, int[] T) {
        int processCount = 0;
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            for (int j = 0; j < InstancesReader.everyProcessNum[i]; j++) {
                if (Math.random() <= 0.5) {
                    int pos = BaseMethod.indexOfMin(time[i][j]);
                    MS[processCount] = process[i][j][pos];
                    T[processCount] = time[i][j][pos];
                } else {
                    int tempPos = new Random().nextInt(time[i][j].length);
                    MS[processCount] = process[i][j][tempPos];
                    T[processCount] = time[i][j][tempPos];
                }
                processCount++;
            }
        }
    }
}
