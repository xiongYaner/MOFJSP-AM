package com.gjr.fjspall.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import static java.lang.Integer.parseInt;


public class InstancesReader {
    public static int machineNum;
    public static int jobNum;
    public static int[] everyProcessNum;
    public static int averageFlexible;
    public static int averageProcess;
    public static int[][][] time;
    public static int[][][] process;
    public static int allSelectMachine;
    public static int allProcess;


    public void txt2String(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            int lineNum = 0;
            while ((s = br.readLine()) != null) {
                int count = 0, j = 0;
                int[] a = new int[s.length()];
                if (lineNum == 0) {
                    s = s.replaceAll("\t", " ");
                    s = s.replaceAll(" +", " ");
                    s = s.trim();
                    s = s + " ";
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == ' ') {
                            String string = s.substring(count, i);
                            a[j++] = parseInt(string);
                            count = i + 1;
                        }
                    }
                    machineNum = a[1];
                    jobNum = a[0];
                    time = new int[jobNum][][];
                    everyProcessNum = new int[jobNum];
                    process = new int[jobNum][][];
                } else {
                    s = s.replaceAll("\\s+", " ");
                    s = s.trim();
                    s = s + " ";
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == ' ') {
                            String string = s.substring(count, i);
                            a[j++] = Integer.parseInt(string);
                            count = i + 1;
                        }
                    }
                    everyProcessNum[lineNum - 1] = a[0];
                    time[lineNum - 1] = new int[everyProcessNum[lineNum - 1]][];
                    process[lineNum - 1] = new int[everyProcessNum[lineNum - 1]][];
                    int pos = 1;
                    for (int i = 0; i < everyProcessNum[lineNum - 1]; i++) {
                        int flexible = a[pos];
                        time[lineNum - 1][i] = new int[flexible];
                        process[lineNum - 1][i] = new int[flexible];
                        allSelectMachine += flexible;
                        for (int k = 0; k < flexible; k++) {
                            process[lineNum - 1][i][k] = a[pos + k * 2 + 1];
                            time[lineNum - 1][i][k] = a[pos + k * 2 + 2];
                        }
                        pos += 2 * flexible + 1;
                    }
                }
                lineNum++;
            }
            br.close();
            allProcess = Arrays.stream(everyProcessNum).sum();
            averageProcess = allProcess / jobNum;
            averageFlexible = allSelectMachine / allProcess;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
