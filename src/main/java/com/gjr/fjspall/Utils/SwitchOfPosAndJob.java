package com.gjr.fjspall.Utils;


public class SwitchOfPosAndJob {


    public static int[] back(int pos) {
        int[] information = new int[2];
        int processSequenceAll[] = new int[InstancesReader.jobNum];
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            if (i == 0) {
                processSequenceAll[i] = InstancesReader.everyProcessNum[i];
            } else {
                processSequenceAll[i] = processSequenceAll[i - 1] + InstancesReader.everyProcessNum[i];
            }
        }
        for (int i = 0; i < InstancesReader.jobNum; i++) {
            if (pos + 1 > processSequenceAll[i]) {
            } else {
                information[0] = i + 1;
                if (i == 0) {
                    information[1] = pos + 1;
                } else {
                    information[1] = pos - processSequenceAll[i - 1] + 1;
                }
                break;
            }
        }
        return information;
    }


    public static int posBack(int job, int process) {
        int pos;
        if (job == 1) {
            pos = process - 1;
        } else {
            pos = 0;
            for (int j = 0; j < job - 1; j++) {
                pos += InstancesReader.everyProcessNum[j];
            }
            pos += process - 1;
        }
        return pos;
    }
}
