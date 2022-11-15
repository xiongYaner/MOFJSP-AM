package com.gjr.fjspall.Operations;

import com.gjr.fjspall.Utils.BaseMethod;
import com.gjr.fjspall.Utils.InstancesReader;
import com.gjr.fjspall.normativeDesign.OperationForDecode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class LeftInsertForFJSP implements OperationForDecode {
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


    public void decode(int[][] OS, int[][] MS, int[][] T) {
        jobSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        processSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        startSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        endSequence = new int[MS.length][InstancesReader.machineNum][InstancesReader.allProcess];
        makespan = new int[MS.length];
        for (int i = 0; i < MS.length; i++) {
            int[][] jobStart = new int[InstancesReader.jobNum][];//工件开始时间
            int[][] jobEnd = new int[InstancesReader.jobNum][];//工件结束时间
            for (int z = 0; z < InstancesReader.jobNum; z++) {
                jobStart[z] = new int[InstancesReader.everyProcessNum[z]];
                jobEnd[z] = new int[InstancesReader.everyProcessNum[z]];
            }
            int[] processCount = new int[InstancesReader.jobNum];//每个工件工序计数器
            Arrays.fill(processCount, 1);
            int[] machineCount = new int[InstancesReader.machineNum];//机器已经完成的工件计数
            for (int j = 0; j < InstancesReader.allProcess; j++) {
                boolean insertSuccess = false;//判断是否完成插入
                int interspaceNum = 0;//空隙数
                int machineNum;//机器号
                int processTime;//加工时间
                //加工信息提取
                int jobNum = OS[i][j];//工件
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
                if (machineCount[machineNum - 1] == 0) {//本机器上之前不存在其他工序
                    if (processCount[jobNum - 1] == 1) {//如果是该工件的第一道工序，从0开始
                        jobSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                        processSequence[i][machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                        startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = 0;
                        endSequence[i][machineNum - 1][machineCount[machineNum - 1]] = processTime;
                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                    } else {//不是第一道工序
                        jobSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                        processSequence[i][machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                        startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2];
                        endSequence[i][machineNum - 1][machineCount[machineNum - 1]] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]] + processTime;
                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                    }
                } else {//本机器上有完成的工件
                    ArrayList<Integer> interspaceStart = new ArrayList<>();//空闲开始时间
                    ArrayList<Integer> interspaceEnd = new ArrayList<>();//空闲开始时间
                    //空闲扫描
                    if (startSequence[i][machineNum - 1][0] >= processTime) {//判断是否有首端空闲
                        interspaceStart.add(0);
                        interspaceEnd.add(startSequence[i][machineNum - 1][0]);
                        interspaceNum++;
                    }
                    if (machineCount[machineNum - 1] >= 2) {//存在已经完成加工的两个工序才会可能出现空隙
                        for (int i1 = 0; i1 < machineCount[machineNum - 1] - 1; i1++) {
                            if (startSequence[i][machineNum - 1][i1 + 1] - endSequence[i][machineNum - 1][i1] >= processTime) {
                                interspaceStart.add(endSequence[i][machineNum - 1][i1]);
                                interspaceEnd.add(startSequence[i][machineNum - 1][i1 + 1]);
                                interspaceNum++;
                            }
                        }
                    }
                    //从左到右扫描
                    if (interspaceNum != 0) {
                        for (int i1 = 0; i1 < interspaceStart.size(); i1++) {
                            if (insertSuccess) {//插入完成即刻停止
                                break;
                            } else {
                                if (processCount[jobNum - 1] == 1) {//如果是本工件的第一道工序
                                    if (interspaceStart.get(i1) == 0) {//存在首端空闲，直接插入
                                        //将此机器上的工序往后移动
                                        for (int i2 = 0; i2 < machineCount[machineNum - 1]; i2++) {
                                            jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        }
                                        jobSequence[i][machineNum - 1][0] = jobNum;
                                        processSequence[i][machineNum - 1][0] = processCount[jobNum - 1];
                                        startSequence[i][machineNum - 1][0] = 0;
                                        endSequence[i][machineNum - 1][0] = processTime;
                                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = 0;
                                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = processTime;
                                        insertSuccess = true;
                                    } else {//不存在首端空闲
                                        int pos = BaseMethod.getIndex(endSequence[i][machineNum - 1], interspaceStart.get(i1));//获取空闲起点的原位置
                                        for (int i2 = 0; i2 < machineCount[machineNum - 1] - pos - 1; i2++) {
                                            jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        }
                                        jobSequence[i][machineNum - 1][pos + 1] = jobNum;
                                        processSequence[i][machineNum - 1][pos + 1] = processCount[jobNum - 1];
                                        startSequence[i][machineNum - 1][pos + 1] = interspaceStart.get(i1);
                                        endSequence[i][machineNum - 1][pos + 1] = interspaceStart.get(i1) + processTime;
                                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][pos + 1];
                                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][pos + 1];
                                        insertSuccess = true;
                                    }
                                } else {//不是本工件的第一道工序
                                    if (interspaceStart.get(i1) == 0) {//首端空闲
                                        if (jobEnd[jobNum - 1][processCount[jobNum - 1] - 2] + processTime <= interspaceEnd.get(i1)) {//满足插入条件，开始时间为上一道工序的结束时间
                                            for (int i2 = 0; i2 < machineCount[machineNum - 1]; i2++) {
                                                jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                                processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                                startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                                endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            }
                                            jobSequence[i][machineNum - 1][0] = jobNum;
                                            processSequence[i][machineNum - 1][0] = processCount[jobNum - 1];
                                            startSequence[i][machineNum - 1][0] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2];
                                            endSequence[i][machineNum - 1][0] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2] + processTime;
                                            jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][0];
                                            jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][0];
                                            insertSuccess = true;
                                        }
                                    } else {//不存在首端空闲
                                        if (Math.max(jobEnd[jobNum - 1][processCount[jobNum - 1] - 2], interspaceStart.get(i1)) + processTime <= interspaceEnd.get(i1)) {//插入条件判断
                                            int pos = BaseMethod.getIndex(endSequence[i][machineNum - 1], interspaceStart.get(i1));//获取空闲起点的原位置
                                            for (int i2 = 0; i2 < machineCount[machineNum - 1] - pos - 1; i2++) {
                                                jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                                processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = processSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                                startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = startSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                                endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2] = endSequence[i][machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            }
                                            jobSequence[i][machineNum - 1][pos + 1] = jobNum;
                                            processSequence[i][machineNum - 1][pos + 1] = processCount[jobNum - 1];
                                            startSequence[i][machineNum - 1][pos + 1] = Math.max(jobEnd[jobNum - 1][processCount[jobNum - 1] - 2], interspaceStart.get(i1));
                                            endSequence[i][machineNum - 1][pos + 1] = startSequence[i][machineNum - 1][pos + 1] + processTime;
                                            jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][pos + 1];
                                            jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][pos + 1];
                                            insertSuccess = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!insertSuccess) {//无法进行插入
                        if (processCount[jobNum - 1] == 1) {
                            jobSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                            processSequence[i][machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                            startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = endSequence[i][machineNum - 1][machineCount[machineNum - 1] - 1];
                            endSequence[i][machineNum - 1][machineCount[machineNum - 1]] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]] + processTime;
                            jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                            jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][machineCount[machineNum - 1]];

                        } else {
                            jobSequence[i][machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                            processSequence[i][machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                            startSequence[i][machineNum - 1][machineCount[machineNum - 1]] = Math.max(jobEnd[jobNum - 1][processCount[jobNum - 1] - 2], endSequence[i][machineNum - 1][machineCount[machineNum - 1] - 1]);
                            endSequence[i][machineNum - 1][machineCount[machineNum - 1]] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]] + processTime;
                            jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                            jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequence[i][machineNum - 1][machineCount[machineNum - 1]];
                        }
                    }
                }
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
        int[][] jobStart = new int[InstancesReader.jobNum][];//工件开始时间
        int[][] jobEnd = new int[InstancesReader.jobNum][];//工件结束时间
        for (int z = 0; z < InstancesReader.jobNum; z++) {
            jobStart[z] = new int[InstancesReader.everyProcessNum[z]];
            jobEnd[z] = new int[InstancesReader.everyProcessNum[z]];
        }
        int[] processCount = new int[InstancesReader.jobNum];//每个工件工序计数器
        Arrays.fill(processCount, 1);
        int[] machineCount = new int[InstancesReader.machineNum];//机器已经完成的工件计数
        for (int j = 0; j < InstancesReader.allProcess; j++) {
            boolean insertSuccess = false;//判断是否完成插入
            int interspaceNum = 0;//空隙数
            int machineNum;//机器号
            int processTime;//加工时间
            //加工信息提取
            int jobNum = OS[j];//工件
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
            if (machineCount[machineNum - 1] == 0) {//本机器上之前不存在其他工序
                if (processCount[jobNum - 1] == 1) {//如果是该工件的第一道工序，从0开始
                    jobSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                    processSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                    startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = 0;
                    endSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = processTime;
                    jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                    jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                } else {//不是第一道工序
                    jobSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                    processSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                    startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2];
                    endSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] + processTime;
                    jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                    jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                }
            } else {//本机器上有完成的工件
                ArrayList<Integer> interspaceStart = new ArrayList<>();//空闲开始时间
                ArrayList<Integer> interspaceEnd = new ArrayList<>();//空闲开始时间
                //空闲扫描
                if (startSequenceOne[machineNum - 1][0] >= processTime) {//判断是否有首端空闲
                    interspaceStart.add(0);
                    interspaceEnd.add(startSequenceOne[machineNum - 1][0]);
                    interspaceNum++;
                }
                if (machineCount[machineNum - 1] >= 2) {//存在已经完成加工的两个工序才会可能出现空隙
                    for (int i1 = 0; i1 < machineCount[machineNum - 1] - 1; i1++) {
                        if (startSequenceOne[machineNum - 1][i1 + 1] - endSequenceOne[machineNum - 1][i1] >= processTime) {
                            interspaceStart.add(endSequenceOne[machineNum - 1][i1]);
                            interspaceEnd.add(startSequenceOne[machineNum - 1][i1 + 1]);
                            interspaceNum++;
                        }
                    }
                }
                //从左到右扫描
                if (interspaceNum != 0) {
                    for (int i1 = 0; i1 < interspaceStart.size(); i1++) {
                        if (insertSuccess) {//插入完成即刻停止
                            break;
                        } else {
                            if (processCount[jobNum - 1] == 1) {//如果是本工件的第一道工序
                                if (interspaceStart.get(i1) == 0) {//存在首端空闲，直接插入
                                    //将此机器上的工序往后移动
                                    for (int i2 = 0; i2 < machineCount[machineNum - 1]; i2++) {
                                        jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                    }
                                    jobSequenceOne[machineNum - 1][0] = jobNum;
                                    processSequenceOne[machineNum - 1][0] = processCount[jobNum - 1];
                                    startSequenceOne[machineNum - 1][0] = 0;
                                    endSequenceOne[machineNum - 1][0] = processTime;
                                    jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = 0;
                                    jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = processTime;
                                    insertSuccess = true;
                                } else {//不存在首端空闲
                                    int pos = BaseMethod.getIndex(endSequenceOne[machineNum - 1], interspaceStart.get(i1));//获取空闲起点的原位置
                                    for (int i2 = 0; i2 < machineCount[machineNum - 1] - pos - 1; i2++) {
                                        jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                    }
                                    jobSequenceOne[machineNum - 1][pos + 1] = jobNum;
                                    processSequenceOne[machineNum - 1][pos + 1] = processCount[jobNum - 1];
                                    startSequenceOne[machineNum - 1][pos + 1] = interspaceStart.get(i1);
                                    endSequenceOne[machineNum - 1][pos + 1] = interspaceStart.get(i1) + processTime;
                                    jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][pos + 1];
                                    jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][pos + 1];
                                    insertSuccess = true;
                                }
                            } else {//不是本工件的第一道工序
                                if (interspaceStart.get(i1) == 0) {//首端空闲
                                    if (jobEnd[jobNum - 1][processCount[jobNum - 1] - 2] + processTime <= interspaceEnd.get(i1)) {//满足插入条件，开始时间为上一道工序的结束时间
                                        for (int i2 = 0; i2 < machineCount[machineNum - 1]; i2++) {
                                            jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        }
                                        jobSequenceOne[machineNum - 1][0] = jobNum;
                                        processSequenceOne[machineNum - 1][0] = processCount[jobNum - 1];
                                        startSequenceOne[machineNum - 1][0] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2];
                                        endSequenceOne[machineNum - 1][0] = jobEnd[jobNum - 1][processCount[jobNum - 1] - 2] + processTime;
                                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][0];
                                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][0];
                                        insertSuccess = true;
                                    }
                                } else {//不存在首端空闲
                                    if (Math.max(jobEnd[jobNum - 1][processCount[jobNum - 1] - 2], interspaceStart.get(i1)) + processTime <= interspaceEnd.get(i1)) {//插入条件判断
                                        int pos = BaseMethod.getIndex(endSequenceOne[machineNum - 1], interspaceStart.get(i1));//获取空闲起点的原位置
                                        for (int i2 = 0; i2 < machineCount[machineNum - 1] - pos - 1; i2++) {
                                            jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = jobSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = processSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                            endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - i2 - 1];
                                        }
                                        jobSequenceOne[machineNum - 1][pos + 1] = jobNum;
                                        processSequenceOne[machineNum - 1][pos + 1] = processCount[jobNum - 1];
                                        startSequenceOne[machineNum - 1][pos + 1] = Math.max(jobEnd[jobNum - 1][processCount[jobNum - 1] - 2], interspaceStart.get(i1));
                                        endSequenceOne[machineNum - 1][pos + 1] = startSequenceOne[machineNum - 1][pos + 1] + processTime;
                                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][pos + 1];
                                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][pos + 1];
                                        insertSuccess = true;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!insertSuccess) {//无法进行插入
                    if (processCount[jobNum - 1] == 1) {
                        jobSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                        processSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                        startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - 1];
                        endSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] + processTime;
                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1]];

                    } else {
                        jobSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = jobNum;
                        processSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = processCount[jobNum - 1];
                        startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = Math.max(jobEnd[jobNum - 1][processCount[jobNum - 1] - 2], endSequenceOne[machineNum - 1][machineCount[machineNum - 1] - 1]);
                        endSequenceOne[machineNum - 1][machineCount[machineNum - 1]] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]] + processTime;
                        jobStart[jobNum - 1][processCount[jobNum - 1] - 1] = startSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                        jobEnd[jobNum - 1][processCount[jobNum - 1] - 1] = endSequenceOne[machineNum - 1][machineCount[machineNum - 1]];
                    }
                }
            }
            processCount[jobNum - 1]++;
            machineCount[machineNum - 1]++;
        }
        makespanOne = BaseMethod.maxTwo(endSequenceOne);
    }


}

