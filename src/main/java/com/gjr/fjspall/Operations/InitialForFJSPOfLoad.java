package com.gjr.fjspall.Operations;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.normativeDesign.OperationForInitial;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Random;

@Getter
@Setter
public class InitialForFJSPOfLoad implements OperationForInitial {
    int pop;
    double[] initializationProportionMultipleObjects = {0.3, 0.2, 0.1, 0.1, 0.2};
    int[][] OS;
    int[][] T;
    int[][] MS;
    int jobNum = InstancesReader.jobNum;
    int machineNum = InstancesReader.machineNum;
    int[][][] process = InstancesReader.process;
    int[][][] time = InstancesReader.time;
    int[] everyProcessNum = InstancesReader.everyProcessNum;

    public InitialForFJSPOfLoad(int pop) {
        this.pop = pop;
    }

    @Override
    public void startMS(int num) {

    }

    @Override
    public void startOS(int num) {

    }

    public void initializationOSRandom() {
        OS = new int[pop][InstancesReader.allProcess];
        for (int i = 0; i < pop; i++) {
            int jobCount = 0;
            for (int k1 = 0; k1 < jobNum; k1++) {
                for (int k2 = 0; k2 < everyProcessNum[k1]; k2++) {
                    OS[i][jobCount] = k1 + 1;
                    jobCount++;
                }
            }
        }
        for (int i = 0; i < pop; i++) {
            OS[i] = BaseMethod.randomDisturb(OS[i]);
        }
    }

    public void initializationMultipleObject() {
        MS = new int[pop][InstancesReader.allProcess];
        T = new int[pop][InstancesReader.allProcess];
        int pos = 0;
        for (int i = 0; i < (int) (initializationProportionMultipleObjects[0] * pop); i++) {
            initializationGMinLoad(pos);
            pos++;
        }
        for (int i = (int) (initializationProportionMultipleObjects[0] * pop); i < (int) ((initializationProportionMultipleObjects[0] + initializationProportionMultipleObjects[1]) * pop); i++) {
            initializationLMinLoad(pos);
            pos++;
        }

        for (int i = (int) ((initializationProportionMultipleObjects[0] + initializationProportionMultipleObjects[1]) * pop); i < (int) ((initializationProportionMultipleObjects[0] + initializationProportionMultipleObjects[1] + initializationProportionMultipleObjects[2]) * pop); i++) {
            initializationGS(pos);
            pos++;
        }

        for (int i = (int) ((initializationProportionMultipleObjects[0] + initializationProportionMultipleObjects[1] + initializationProportionMultipleObjects[2]) * pop); i < (int) ((initializationProportionMultipleObjects[0] + initializationProportionMultipleObjects[1] + initializationProportionMultipleObjects[2] + initializationProportionMultipleObjects[3]) * pop); i++) {
            initializationLS(pos);
            pos++;
        }

        for (int i = (int) ((initializationProportionMultipleObjects[0] + initializationProportionMultipleObjects[1] + initializationProportionMultipleObjects[2] + initializationProportionMultipleObjects[3]) * pop); i < pop; i++) {
            initializationRS(pos);
            pos++;
        }

    }

    public void initializationGS(int initializationPos) {
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
                    MS[initializationPos][j] = machinePos + 1;
                    T[initializationPos][j] = time[job[i] - 1][j][pos];
                } else {
                    int beforeProcess = 0;
                    for (int z1 = 0; z1 < job[i] - 1; z1++) {
                        beforeProcess += everyProcessNum[z1];
                    }
                    MS[initializationPos][beforeProcess + j] = machinePos + 1;
                    T[initializationPos][beforeProcess + j] = time[job[i] - 1][j][pos];
                }
            }
        }
    }

    public void initializationLS(int initializationPos) {
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
                MS[initializationPos][countProcess] = machinePos + 1;
                T[initializationPos][countProcess] = time[k1][k2][pos];
                countProcess++;
            }
        }
    }

    public void initializationRS(int initializationPos) {
        int processCount = 0;
        for (int k1 = 0; k1 < jobNum; ++k1) {
            for (int k2 = 0; k2 < everyProcessNum[k1]; k2++) {
                int tempPos = new Random().nextInt(time[k1][k2].length);
                MS[initializationPos][processCount] = process[k1][k2][tempPos];
                T[initializationPos][processCount] = time[k1][k2][tempPos];
                processCount++;
            }
        }
    }

    public void initializationGMinLoad(int initializationPos) {
        int processCount = 0;
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            for (int j = 0; j < InstancesReader.everyProcessNum[i]; j++) {
                int pos = BaseMethod.indexOfMin(time[i][j]);
                MS[initializationPos][processCount] = process[i][j][pos];
                T[initializationPos][processCount] = time[i][j][pos];
                processCount++;
            }
        }
    }

    public void initializationLMinLoad(int initializationPos) {
        int processCount = 0;
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            for (int j = 0; j < InstancesReader.everyProcessNum[i]; j++) {
                if (Math.random() <= 0.5) {
                    int pos = BaseMethod.indexOfMin(time[i][j]);
                    MS[initializationPos][processCount] = process[i][j][pos];
                    T[initializationPos][processCount] = time[i][j][pos];
                } else {
                    int tempPos = new Random().nextInt(time[i][j].length);
                    MS[initializationPos][processCount] = process[i][j][tempPos];
                    T[initializationPos][processCount] = time[i][j][tempPos];
                }
                processCount++;
            }
        }
    }


}
