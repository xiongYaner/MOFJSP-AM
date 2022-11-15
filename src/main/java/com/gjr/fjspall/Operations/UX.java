package com.gjr.fjspall.Operations;


import com.gjr.fjspall.MOFJSP.AS;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.normativeDesign.OperationForMS;

import java.util.Arrays;
import java.util.Random;

/**
 * UX
 *
 * @Author: Ge JiaRong
 * @Date: 2021/09/20/14:22
 * @Description:
 */
public class UX implements OperationForMS {
    double probability = 0.8;

    @Override
    public int[][] run(int[][] MS, double probabilityIn) {
        this.probability = probabilityIn;
        int[][] newMS = new int[MS.length][InstancesReader.allProcess];
        int numberCount = 0;
        while (numberCount < MS.length) {
            if (Math.random() <= probability && numberCount != MS.length - 1) {
                int[] child1 = new int[MS[0].length];
                int[] child2 = new int[MS[0].length];
                int[] oneZero = new int[MS[0].length];
                int[] father1 = new int[MS[0].length];
                int[] father2 = new int[MS[0].length];
                int a = new Random().nextInt(MS.length);
                int b = new Random().nextInt(MS.length);
                System.arraycopy(MS[a], 0, father1, 0, MS[a].length);
                System.arraycopy(MS[b], 0, father2, 0, MS[b].length);
                int count = 0;
                while (Arrays.equals(father1, father2) && count < 3) {
                    a = new Random().nextInt(MS.length);
                    b = new Random().nextInt(MS.length);
                    System.arraycopy(MS[a], 0, father1, 0, MS[a].length);
                    System.arraycopy(MS[b], 0, father2, 0, MS[b].length);
                    count++;
                }
                Arrays.fill(child1, 0);
                Arrays.fill(child2, 0);
                Arrays.fill(oneZero, 0);
                for (int i = 0; i < oneZero.length; i++) {
                    if (Math.random() <= 0.5) {
                        oneZero[i] = 1;
                    }
                }
                for (int i = 0; i < father1.length; i++) {
                    if (oneZero[i] == 1) {
                        child1[i] = father1[i];
                        child2[i] = father2[i];
                    } else {
                        child2[i] = father1[i];
                        child1[i] = father2[i];
                    }
                }
                for (int i = 0; i < newMS[0].length; i++) {
                    newMS[numberCount][i] = child1[i];
                    newMS[numberCount + 1][i] = child2[i];
                }

                numberCount += 2;
            } else {
                int[] father = MS[new Random().nextInt(MS.length)];
                System.arraycopy(father, 0, newMS[numberCount], 0, newMS[0].length);
                numberCount++;
            }
        }
        return newMS;
    }

    @Override
    public int[][] run(int[][] MS) {
        int[][] newMS = new int[MS.length][InstancesReader.allProcess];
        int numberCount = 0;
        while (numberCount < MS.length) {
            if (Math.random() <= probability && numberCount != MS.length - 1) {
                int[] child1 = new int[MS[0].length];
                int[] child2 = new int[MS[0].length];
                int[] oneZero = new int[MS[0].length];
                int[] father1 = new int[MS[0].length];
                int[] father2 = new int[MS[0].length];
                int a = new Random().nextInt(MS.length);
                int b = new Random().nextInt(MS.length);
                System.arraycopy(MS[a], 0, father1, 0, MS[a].length);
                System.arraycopy(MS[b], 0, father2, 0, MS[b].length);
                int count = 0;
                while (Arrays.equals(father1, father2) && count < 3) {
                    a = new Random().nextInt(MS.length);
                    b = new Random().nextInt(MS.length);
                    System.arraycopy(MS[a], 0, father1, 0, MS[a].length);
                    System.arraycopy(MS[b], 0, father2, 0, MS[b].length);
                    count++;
                }
                Arrays.fill(child1, 0);
                Arrays.fill(child2, 0);
                Arrays.fill(oneZero, 0);
                for (int i = 0; i < oneZero.length; i++) {
                    if (Math.random() <= 0.5) {
                        oneZero[i] = 1;
                    }
                }
                for (int i = 0; i < father1.length; i++) {
                    if (oneZero[i] == 1) {
                        child1[i] = father1[i];
                        child2[i] = father2[i];
                    } else {
                        child2[i] = father1[i];
                        child1[i] = father2[i];
                    }
                }
                for (int i = 0; i < newMS[0].length; i++) {
                    newMS[numberCount][i] = child1[i];
                    newMS[numberCount + 1][i] = child2[i];
                }

                numberCount += 2;
            } else {
                int[] father = MS[new Random().nextInt(MS.length)];
                System.arraycopy(father, 0, newMS[numberCount], 0, newMS[0].length);
                numberCount++;
            }
        }
        return newMS;
    }

    public int[][] runWithAS(AS AS, int[][] MS) {
        int[][] newMS = new int[MS.length][InstancesReader.allProcess];
        int numberCount = 0;
        while (numberCount < MS.length) {
            if (Math.random() <= probability && numberCount != MS.length - 1) {
                int[] child1 = new int[MS[0].length];
                int[] child2 = new int[MS[0].length];
                int[] oneZero = new int[MS[0].length];
                int[] father1 = new int[MS[0].length];
                int[] father2 = new int[MS[0].length];
                int a = new Random().nextInt(AS.getMS().size());
                int b = new Random().nextInt(MS.length);
                System.arraycopy(AS.getMS().get(a), 0, father1, 0, MS[a].length);
                System.arraycopy(MS[b], 0, father2, 0, MS[b].length);
                int count = 0;
                while (Arrays.equals(father1, father2) && count < 3) {
                    a = new Random().nextInt(MS.length);
                    b = new Random().nextInt(MS.length);
                    System.arraycopy(MS[a], 0, father1, 0, MS[a].length);
                    System.arraycopy(MS[b], 0, father2, 0, MS[b].length);
                    count++;
                }
                Arrays.fill(child1, 0);
                Arrays.fill(child2, 0);
                Arrays.fill(oneZero, 0);
                for (int i = 0; i < oneZero.length; i++) {
                    if (Math.random() <= 0.5) {
                        oneZero[i] = 1;
                    }
                }
                for (int i = 0; i < father1.length; i++) {
                    if (oneZero[i] == 1) {
                        child1[i] = father1[i];
                        child2[i] = father2[i];
                    } else {
                        child2[i] = father1[i];
                        child1[i] = father2[i];
                    }
                }
                for (int i = 0; i < newMS[0].length; i++) {
                    newMS[numberCount][i] = child1[i];
                    newMS[numberCount + 1][i] = child2[i];
                }

                numberCount += 2;
            } else {
                int[] father = MS[new Random().nextInt(MS.length)];
                System.arraycopy(father, 0, newMS[numberCount], 0, newMS[0].length);
                numberCount++;
            }
        }
        return newMS;
    }

    @Override
    public int[] run(int[] MS) {
        return new int[0];
    }

    @Override
    public int[] run(int[] MS, double probabilityIn) {
        return new int[0];
    }

    public void runWithTwo(int[] father1, int[] father2) {
        int[] child1 = new int[InstancesReader.allProcess];
        int[] child2 = new int[InstancesReader.allProcess];
        int[] oneZero = new int[InstancesReader.allProcess];
        for (int i = 0; i < oneZero.length; i++) {
            if (Math.random() <= 0.5) {
                oneZero[i] = 1;
            }
        }
        for (int i = 0; i < father1.length; i++) {
            if (oneZero[i] == 1) {
                child1[i] = father1[i];
                child2[i] = father2[i];
            } else {
                child2[i] = father1[i];
                child1[i] = father2[i];
            }
        }
        System.arraycopy(child1, 0, father1, 0, InstancesReader.allProcess);
        System.arraycopy(child2, 0, father2, 0, InstancesReader.allProcess);
    }

    public void runWithTwo(int[] father1, int[] father2, double probabilityIn) {
        if (Math.random() <= probabilityIn) {
            int[] child1 = new int[InstancesReader.allProcess];
            int[] child2 = new int[InstancesReader.allProcess];
            int[] oneZero = new int[InstancesReader.allProcess];
            for (int i = 0; i < oneZero.length; i++) {
                if (Math.random() <= 0.5) {
                    oneZero[i] = 1;
                }
            }
            for (int i = 0; i < father1.length; i++) {
                if (oneZero[i] == 1) {
                    child1[i] = father1[i];
                    child2[i] = father2[i];
                } else {
                    child2[i] = father1[i];
                    child1[i] = father2[i];
                }
            }
            System.arraycopy(child1, 0, father1, 0, InstancesReader.allProcess);
            System.arraycopy(child2, 0, father2, 0, InstancesReader.allProcess);
        }
    }
}
