package com.gjr.fjspall.Operations;


import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.normativeDesign.OperationForOS;


import java.util.ArrayList;
import java.util.Random;


public class SwapTwoJobs implements OperationForOS {
    double probability = 1;

    @Override
    public int[][] run(int[][] OS, double probabilityIn) {
        this.probability = probabilityIn;
        int[][] newOS = new int[OS.length][InstancesReader.allProcess];
        BaseMethod.arrayCopy(OS, newOS);
        for (int i = 0; i < OS.length; i++) {
            if (Math.random() <= probability) {
                int job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                int job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                while (InstancesReader.everyProcessNum[job1] > InstancesReader.everyProcessNum[job2] || job1 == job2) {
                    job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                    job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                }
                ArrayList<Integer> pos1 = new ArrayList<>();
                ArrayList<Integer> pos2 = new ArrayList<>();
                for (int k = 0; k < newOS[i].length; k++) {
                    if (newOS[i][k] == job1) {
                        pos1.add(k);
                        continue;
                    }
                    if (newOS[i][k] == job2) {
                        pos2.add(k);
                    }
                }
                int[] temp = new int[pos1.size() + pos2.size()];
                int count = 0;
                for (int i1 = 0; i1 < pos2.size(); i1++) {
                    temp[count] = job2;
                    count++;
                }
                for (int i1 = 0; i1 < pos1.size(); i1++) {
                    temp[count] = job1;
                    count++;
                }
                for (int k = 0; k < pos2.size(); k++) {
                    pos1.add(pos2.get(k));
                }
                for (int k = 0; k < temp.length; k++) {
                    newOS[i][pos1.get(k)] = temp[k];
                }
            }
        }
        return newOS;
    }

    @Override
    public int[][] run(int[][] OS) {
        int[][] newOS = new int[OS.length][InstancesReader.allProcess];
        BaseMethod.arrayCopy(OS, newOS);
        for (int i = 0; i < OS.length; i++) {
            if (Math.random() <= probability) {
                int job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                int job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                while (InstancesReader.everyProcessNum[job1] > InstancesReader.everyProcessNum[job2] || job1 == job2) {
                    job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                    job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                }
                ArrayList<Integer> pos1 = new ArrayList<>();
                ArrayList<Integer> pos2 = new ArrayList<>();
                for (int k = 0; k < newOS[i].length; k++) {
                    if (newOS[i][k] == job1) {
                        pos1.add(k);
                        continue;
                    }
                    if (newOS[i][k] == job2) {
                        pos2.add(k);
                    }
                }
                int[] temp = new int[pos1.size() + pos2.size()];
                int count = 0;
                for (int i1 = 0; i1 < pos2.size(); i1++) {
                    temp[count] = job2;
                    count++;
                }
                for (int i1 = 0; i1 < pos1.size(); i1++) {
                    temp[count] = job1;
                    count++;
                }
                for (int k = 0; k < pos2.size(); k++) {
                    pos1.add(pos2.get(k));
                }
                for (int k = 0; k < temp.length; k++) {
                    newOS[i][pos1.get(k)] = temp[k];
                }
            }
        }
        return newOS;
    }

    @Override
    public int[] run(int[] OS, double probabilityIn) {
        this.probability = probabilityIn;
        int[] newOS = new int[InstancesReader.allProcess];
        System.arraycopy(OS, 0, newOS, 0, InstancesReader.allProcess);
        if (Math.random() <= probability) {
            int job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
            int job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
            while (InstancesReader.everyProcessNum[job1] > InstancesReader.everyProcessNum[job2] || job1 == job2) {
                job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
            }
            ArrayList<Integer> pos1 = new ArrayList<>();
            ArrayList<Integer> pos2 = new ArrayList<>();
            for (int k = 0; k < newOS.length; k++) {
                if (newOS[k] == job1) {
                    pos1.add(k);
                    continue;
                }
                if (newOS[k] == job2) {
                    pos2.add(k);
                }
            }
            int[] temp = new int[pos1.size() + pos2.size()];
            int count = 0;
            for (int i1 = 0; i1 < pos2.size(); i1++) {
                temp[count] = job2;
                count++;
            }
            for (int i1 = 0; i1 < pos1.size(); i1++) {
                temp[count] = job1;
                count++;
            }
            for (int k = 0; k < pos2.size(); k++) {
                pos1.add(pos2.get(k));
            }
            for (int k = 0; k < temp.length; k++) {
                newOS[pos1.get(k)] = temp[k];
            }
        }
        return newOS;
    }

    @Override
    public int[] run(int[] OS) {
        int[] newOS = new int[InstancesReader.allProcess];
        System.arraycopy(OS, 0, newOS, 0, InstancesReader.allProcess);
        if (Math.random() <= probability) {
            int job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
            int job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
            while (InstancesReader.everyProcessNum[job1] > InstancesReader.everyProcessNum[job2] || job1 == job2) {
                job1 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
                job2 = new Random().nextInt(InstancesReader.jobNum - 1) + 1;
            }
            ArrayList<Integer> pos1 = new ArrayList<>();
            ArrayList<Integer> pos2 = new ArrayList<>();
            for (int k = 0; k < newOS.length; k++) {
                if (newOS[k] == job1) {
                    pos1.add(k);
                    continue;
                }
                if (newOS[k] == job2) {
                    pos2.add(k);
                }
            }
            int[] temp = new int[pos1.size() + pos2.size()];
            int count = 0;
            for (int i1 = 0; i1 < pos2.size(); i1++) {
                temp[count] = job2;
                count++;
            }
            for (int i1 = 0; i1 < pos1.size(); i1++) {
                temp[count] = job1;
                count++;
            }
            for (int k = 0; k < pos2.size(); k++) {
                pos1.add(pos2.get(k));
            }
            for (int k = 0; k < temp.length; k++) {
                newOS[pos1.get(k)] = temp[k];
            }
        }
        return newOS;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
