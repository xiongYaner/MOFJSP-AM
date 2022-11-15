package com.gjr.fjspall.Operations;


import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.normativeDesign.OperationForOS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class IPOX implements OperationForOS {
    double probability = 1;

    @Override
    public int[][] run(int[][] OS, double probabilityIn) {
        this.probability = probabilityIn;
        int[][] newOS = new int[OS.length][InstancesReader.allProcess];
        int i = 0;
        int[] child1 = new int[InstancesReader.allProcess];
        int[] child2 = new int[InstancesReader.allProcess];
        int[] father1 = new int[InstancesReader.allProcess];
        int[] father2 = new int[InstancesReader.allProcess];
        while (i < OS.length) {
            if (Math.random() <= probability && i != OS.length - 1) {
                int a = new Random().nextInt(OS.length);
                int b = new Random().nextInt(OS.length);
                System.arraycopy(OS[a], 0, father1, 0, OS[a].length);
                System.arraycopy(OS[b], 0, father2, 0, OS[b].length);
                Arrays.fill(child1, 0);
                Arrays.fill(child2, 0);
                int[] job = new int[InstancesReader.jobNum];
                for (int k = 0; k < job.length; k++) {
                    job[k] = k + 1;
                }
                int[] jobDisturb = BaseMethod.randomDisturb(job);
                int breakpoint = new Random().nextInt(job.length - 1);
                int[] job1 = new int[breakpoint + 1];
                int[] job2 = new int[InstancesReader.jobNum - breakpoint - 1];
                System.arraycopy(jobDisturb, 0, job1, 0, breakpoint + 1);
                for (int k = 0; k < job2.length; k++) {
                    job2[k] = jobDisturb[breakpoint + 1 + k];
                }
                ArrayList<Integer> surplusFather1 = new ArrayList<>();
                ArrayList<Integer> surplusFather2 = new ArrayList<>();
                for (int k = 0; k < father1.length; k++) {
                    if (BaseMethod.isMember(father1[k], job1)) {
                        child1[k] = father1[k];
                    }
                    if (BaseMethod.isMember(father2[k], job2)) {
                        child2[k] = father2[k];
                    }
                }
                for (int k = 0; k < father1.length; k++) {
                    if (BaseMethod.isMember(father1[k], job1)) {
                        surplusFather1.add(father1[k]);
                    }
                    if (BaseMethod.isMember(father2[k], job2)) {
                        surplusFather2.add(father2[k]);
                    }
                }
                int surplusCount1 = 0;
                int surplusCount2 = 0;
                for (int k = 0; k < child1.length; k++) {
                    if (child1[k] == 0) {
                        child1[k] = surplusFather2.get(surplusCount2);
                        surplusCount2++;
                    }
                    if (child2[k] == 0) {
                        child2[k] = surplusFather1.get(surplusCount1);
                        surplusCount1++;
                    }
                }
                for (int i1 = 0; i1 < newOS[0].length; i1++) {
                    newOS[i][i1] = child1[i1];
                    newOS[i + 1][i1] = child2[i1];
                }
                i++;
                i++;
            } else {
                int[] father = OS[new Random().nextInt(OS.length)];
                System.arraycopy(father, 0, newOS[i], 0, newOS[0].length);
                i++;
            }
        }
        return newOS;

    }

    @Override
    public int[][] run(int[][] OS) {
        int[][] newOS = new int[OS.length][InstancesReader.allProcess];
        int i = 0;
        int[] child1 = new int[InstancesReader.allProcess];
        int[] child2 = new int[InstancesReader.allProcess];//子代个体
        int[] father1 = new int[InstancesReader.allProcess];
        int[] father2 = new int[InstancesReader.allProcess];
        while (i < OS.length) {
            if (Math.random() <= probability && i != OS.length - 1) {
                int a = new Random().nextInt(OS.length);
                int b = new Random().nextInt(OS.length);
                System.arraycopy(OS[a], 0, father1, 0, OS[a].length);
                System.arraycopy(OS[b], 0, father2, 0, OS[b].length);
                Arrays.fill(child1, 0);
                Arrays.fill(child2, 0);
                int[] job = new int[InstancesReader.jobNum];
                for (int k = 0; k < job.length; k++) {
                    job[k] = k + 1;
                }
                int[] jobDisturb = BaseMethod.randomDisturb(job);
                int breakpoint = new Random().nextInt(job.length - 1);
                int[] job1 = new int[breakpoint + 1];
                int[] job2 = new int[InstancesReader.jobNum - breakpoint - 1];
                System.arraycopy(jobDisturb, 0, job1, 0, breakpoint + 1);
                for (int k = 0; k < job2.length; k++) {
                    job2[k] = jobDisturb[breakpoint + 1 + k];
                }
                /*子代1保存job1的内容，子代2保存job2的内容*/
                ArrayList<Integer> surplusFather1 = new ArrayList<>();
                ArrayList<Integer> surplusFather2 = new ArrayList<>();//剩余内容
                for (int k = 0; k < father1.length; k++) {
                    if (BaseMethod.isMember(father1[k], job1)) {
                        child1[k] = father1[k];
                    }
                    if (BaseMethod.isMember(father2[k], job2)) {
                        child2[k] = father2[k];
                    }
                }
                for (int k = 0; k < father1.length; k++) {
                    if (BaseMethod.isMember(father1[k], job1)) {
                        surplusFather1.add(father1[k]);
                    }
                    if (BaseMethod.isMember(father2[k], job2)) {
                        surplusFather2.add(father2[k]);
                    }
                }
                /*将剩余染色体赋值给子代*/
                int surplusCount1 = 0;
                int surplusCount2 = 0;
                for (int k = 0; k < child1.length; k++) {
                    if (child1[k] == 0) {
                        child1[k] = surplusFather2.get(surplusCount2);
                        surplusCount2++;
                    }
                    if (child2[k] == 0) {
                        child2[k] = surplusFather1.get(surplusCount1);
                        surplusCount1++;
                    }
                }
                for (int i1 = 0; i1 < newOS[0].length; i1++) {
                    newOS[i][i1] = child1[i1];
                    newOS[i + 1][i1] = child2[i1];
                }
                i++;
                i++;
            } else {
                int[] father = OS[new Random().nextInt(OS.length)];
                System.arraycopy(father, 0, newOS[i], 0, newOS[0].length);
                i++;
            }
        }
        return newOS;

    }

    @Override
    public int[] run(int[] OS, double probabilityIn) {
        return new int[0];
    }

    @Override
    public int[] run(int[] OS) {
        return new int[0];
    }


}
