package com.gjr.fjspall.Operations;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.normativeDesign.OperationForDecode;

import java.util.Arrays;


public class OrdinaryDecodeForFJSP implements OperationForDecode {
    @Override
    public void decode(int[][] OS, int[][] MS, int[][][] T) {

    }

    private int[][][] jobSequence;
    private int[][][] processSequence;
    private int[][][] startSequence;
    private int[][][] endSequence;
    private int[] makespan;
    private int[][] jobSequenceOne;
    private int[][] processSequenceOne;
    private int[][] startSequenceOne;
    private int[][] endSequenceOne;
    private int makespanOne;

    public int[][][] getJobSequence() {
        return jobSequence;
    }

    public void setJobSequence(int[][][] jobSequence) {
        this.jobSequence = jobSequence;
    }

    public int[][][] getProcessSequence() {
        return processSequence;
    }

    public void setProcessSequence(int[][][] processSequence) {
        this.processSequence = processSequence;
    }

    public int[][][] getStartSequence() {
        return startSequence;
    }

    public void setStartSequence(int[][][] startSequence) {
        this.startSequence = startSequence;
    }

    public int[][][] getEndSequence() {
        return endSequence;
    }

    public void setEndSequence(int[][][] endSequence) {
        this.endSequence = endSequence;
    }

    public int[] getMakespan() {
        return makespan;
    }

    public void setMakespan(int[] makespan) {
        this.makespan = makespan;
    }

    public int[][] getJobSequenceOne() {
        return jobSequenceOne;
    }

    public void setJobSequenceOne(int[][] jobSequenceOne) {
        this.jobSequenceOne = jobSequenceOne;
    }

    public int[][] getProcessSequenceOne() {
        return processSequenceOne;
    }

    public void setProcessSequenceOne(int[][] processSequenceOne) {
        this.processSequenceOne = processSequenceOne;
    }

    public int[][] getStartSequenceOne() {
        return startSequenceOne;
    }

    public void setStartSequenceOne(int[][] startSequenceOne) {
        this.startSequenceOne = startSequenceOne;
    }

    public int[][] getEndSequenceOne() {
        return endSequenceOne;
    }

    public void setEndSequenceOne(int[][] endSequenceOne) {
        this.endSequenceOne = endSequenceOne;
    }

    public int getMakespanOne() {
        return makespanOne;
    }

    public void setMakespanOne(int makespanOne) {
        this.makespanOne = makespanOne;
    }

    public void decode(int[][] OS, int[][] MS, int[][] T) {
        jobSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        processSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        startSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        endSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        makespan = new int[MS.length];
        for (int i = 0; i < OS.length; i++) {
            int[][] jobStart = new int[InstancesReader.jobNum][];
            int[][] jobEnd = new int[InstancesReader.jobNum][];
            for (int z = 0; z < InstancesReader.jobNum; z++) {
                jobStart[z] = new int[InstancesReader.everyProcessNum[z]];
                jobEnd[z] = new int[InstancesReader.everyProcessNum[z]];
            }
            int[] processCount = new int[InstancesReader.jobNum];
            int[] machineCount = new int[InstancesReader.machineNum];
            Arrays.fill(processCount, 1);
            for (int j = 0; j < InstancesReader.allProcess; j++) {
                int machineNum;
                int processTime;
                int jobNum = OS[i][j];
                if (jobNum == 1) {
                    machineNum = MS[i][processCount[jobNum - 1] - 1];
                    processTime = T[i][processCount[jobNum - 1] - 1];
                } else {
                    int beforeProcess = 0;
                    for (int i1 = 0; i1 < jobNum - 1; i1++) {
                        beforeProcess += InstancesReader.everyProcessNum[i1];
                    }
                    machineNum = MS[i][beforeProcess + processCount[jobNum - 1] - 1];
                    processTime = T[i][beforeProcess + processCount[jobNum - 1] - 1];
                }
                jobSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                processSequence[i][machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                if (machineCount[machineNum - 1] == 0) {
                    if (processCount[jobNum - 1] == 1) {
                        startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = 0;
                    } else {
                        startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2];
                    }
                } else {
                    if (processCount[jobNum - 1] == 1) {
                        startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = endSequence[i][machineNum - 1][machineCount[machineNum - 1] - 1];
                    } else {
                        startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = Math.max(endSequence[i][machineNum - 1][machineCount[machineNum - 1] - 1], jobEnd[jobNum - 1][processCount[jobNum - 1] - 1 - 1]);
                    }
                }
                endSequence[i][machineNum - 1][machineCount[machineNum - 1]] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]] + processTime;
                jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = jobStart[jobNum - 1][processCount[jobNum - 1] - 1] + processTime;
                processCount[jobNum - 1]++;
                machineCount[machineNum - 1]++;
            }
            makespan[i] = BaseMethod.maxTwo(endSequence[i]);
        }
    }

    public void decode(int[] OS, int[] MS, int[] T) {
        jobSequenceOne = new int[InstancesReader.machineNum][InstancesReader.allProcess];
        processSequenceOne = new int[InstancesReader.machineNum][InstancesReader.allProcess];
        startSequenceOne = new int[InstancesReader.machineNum][InstancesReader.allProcess];
        endSequenceOne = new int[InstancesReader.machineNum][InstancesReader.allProcess];

        int[][] jobStart = new int[InstancesReader.jobNum][];
        int[][] jobEnd = new int[InstancesReader.jobNum][];
        for (int z = 0; z < InstancesReader.jobNum; z++) {
            jobStart[z] = new int[InstancesReader.everyProcessNum[z]];
            jobEnd[z] = new int[InstancesReader.everyProcessNum[z]];
        }
        int[] processCount = new int[InstancesReader.jobNum];
        int[] machineCount = new int[InstancesReader.machineNum];
        Arrays.fill(processCount, 1);
        for (int j = 0; j < InstancesReader.allProcess; j++) {
            int machineNum;
            int processTime;
            int jobNum = OS[j];
            if (jobNum == 1) {
                machineNum = MS[processCount[jobNum - 1] - 1];
                processTime = T[processCount[jobNum - 1] - 1];
            } else {
                int beforeProcess = 0;
                for (int i1 = 0; i1 < jobNum - 1; i1++) {
                    beforeProcess += InstancesReader.everyProcessNum[i1];
                }
                machineNum = MS[beforeProcess + processCount[jobNum - 1] - 1];
                processTime = T[beforeProcess + processCount[jobNum - 1] - 1];
            }
            jobSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobNum;
            processSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
            if (machineCount[machineNum - 1] == 0) {
                if (processCount[jobNum - 1] == 1) {
                    startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = 0;
                } else {
                    startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2];
                }
            } else {
                if (processCount[jobNum - 1] == 1) {
                    startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - 1];
                } else {
                    startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = Math.max(endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - 1], jobEnd[jobNum - 1][processCount[jobNum - 1] - 1 - 1]);
                }
            }
            endSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] + processTime;
            jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
            jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = jobStart[jobNum - 1][processCount[jobNum - 1] - 1] + processTime;
            processCount[jobNum - 1]++;
            machineCount[machineNum - 1]++;
        }
        makespanOne = BaseMethod.maxTwo(endSequenceOne);
    }

}
