package com.gjr.fjspall.Operations;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.Utils.SwitchOfPosAndJob;
import com.gjr.fjspall.normativeDesign.OperationForMS;

import java.util.Random;

/**
 * OneRandomPos
 *
 * @Author: Ge JiaRong
 * @Date: 2021/10/11/21:21
 * @Description:
 */
public class OneRandomPos implements OperationForMS {
    double probability = 0.999;

    @Override
    public int[][] run(int[][] MS, double probabilityIn) {
        probability = probabilityIn;
        int[][] newMS = new int[MS.length][InstancesReader.allProcess];
        BaseMethod.arrayCopy(MS, newMS);
        for (int i = 0; i < MS.length; i++) {
            int pos = new Random().nextInt(InstancesReader.allProcess);
            int[] back = SwitchOfPosAndJob.back(pos);
            newMS[i][pos] = InstancesReader.process[back[0] - 1][back[1] - 1][new Random().nextInt(InstancesReader.process[back[0] - 1][back[1] - 1].length)];


        }
        return newMS;
    }

    @Override
    public int[][] run(int[][] MS) {
        int[][] newMS = new int[MS.length][InstancesReader.allProcess];
        BaseMethod.arrayCopy(MS, newMS);
        for (int i = 0; i < MS.length; i++) {
            int pos = new Random().nextInt(InstancesReader.allProcess);
            int[] back = SwitchOfPosAndJob.back(pos);
            int choice = new Random().nextInt(InstancesReader.process[back[0] - 1][back[1] - 1].length);
            newMS[i][pos] = InstancesReader.process[back[0] - 1][back[1] - 1][choice];
        }
        return newMS;
    }

    @Override
    public int[] run(int[] MS) {
        int[] newMS = new int[InstancesReader.allProcess];
        System.arraycopy(MS, 0, newMS, 0, InstancesReader.allProcess);
        int pos = new Random().nextInt(InstancesReader.allProcess);
        int[] back = SwitchOfPosAndJob.back(pos);
        newMS[pos] = InstancesReader.process[back[0] - 1][back[1] - 1][new Random().nextInt(InstancesReader.process[back[0] - 1][back[1] - 1].length)];
        return newMS;
    }

    public int[] mulPoint(int[] MS, int num) {
        int[] newMS = new int[InstancesReader.allProcess];
        System.arraycopy(MS, 0, newMS, 0, InstancesReader.allProcess);
        for (int i = 0; i < num; i++) {
            int pos = new Random().nextInt(InstancesReader.allProcess);
            int[] back = SwitchOfPosAndJob.back(pos);
            newMS[pos] = InstancesReader.process[back[0] - 1][back[1] - 1][new Random().nextInt(InstancesReader.process[back[0] - 1][back[1] - 1].length)];
        }
        return newMS;
    }

    @Override
    public int[] run(int[] MS, double probabilityIn) {
        probability = probabilityIn;
        int[] newMS = new int[InstancesReader.allProcess];
        System.arraycopy(MS, 0, newMS, 0, InstancesReader.allProcess);
        int pos = new Random().nextInt(InstancesReader.allProcess);
        int[] back = SwitchOfPosAndJob.back(pos);
        newMS[pos] = InstancesReader.process[back[0] - 1][back[1] - 1][new Random().nextInt(InstancesReader.process[back[0] - 1][back[1] - 1].length)];
        return newMS;
    }
}
