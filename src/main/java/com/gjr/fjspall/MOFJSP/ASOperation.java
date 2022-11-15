package com.gjr.fjspall.MOFJSP;


import com.gjr.fjspall.Utils.BaseMethod;


public class ASOperation {

    public static void update(AS AS) {
        int[][] temp = new int[AS.getWm().size()][3];
        int count = 0;
        int len = AS.getWm().size();
        for (int i = 0; i < len; i++) {
            int[] fitnessTemp = {AS.getMs().get(i), AS.getWt().get(i), AS.getWm().get(i)};
            if (BaseMethod.lineIfRepeat(fitnessTemp, temp)) {
                AS.getMs().remove(i);
                AS.getWt().remove(i);
                AS.getWm().remove(i);
                AS.getMS().remove(i);
                AS.getOS().remove(i);
                AS.getT().remove(i);
                --i;
                --len;
            } else {
                temp[count] = fitnessTemp;
                count++;
            }
        }
        int[][] fitness = new int[3][AS.getWm().size()];
        for (int i = 0; i < AS.getWm().size(); i++) {
            fitness[0][i] = AS.getMs().get(i);
            fitness[1][i] = AS.getWt().get(i);
            fitness[2][i] = AS.getWm().get(i);
        }
        int[] level = Pareto.paretoSort(fitness);
        len = AS.getWm().size();
        int countLevel = 0;
        for (int i = 0; i < len; i++) {
            if (level[countLevel] != 1) {
                AS.getMs().remove(i);
                AS.getWt().remove(i);
                AS.getWm().remove(i);
                AS.getMS().remove(i);
                AS.getOS().remove(i);
                AS.getT().remove(i);
                --i;
                --len;
            }
            countLevel++;
        }
    }

    public static void joinAS(AS AS, int[][] MS, int[][] OS, int[][] T, int[][] fitness, int[] level) {
        int[][] temp = new int[OS.length][3];
        int count = 0;
        for (int i = 0; i < level.length; i++) {
            if (level[i] == 1) {
                int[] fitnessTemp = {fitness[0][i], fitness[1][i], fitness[2][i]};
                if (!BaseMethod.lineIfRepeat(fitnessTemp, temp)) {
                    BaseMethod.arrayListGive(AS.getMS(), MS[i]);
                    BaseMethod.arrayListGive(AS.getOS(), OS[i]);
                    BaseMethod.arrayListGive(AS.getT(), T[i]);
                    AS.getMs().add(fitness[0][i]);
                    AS.getWt().add(fitness[1][i]);
                    AS.getWm().add(fitness[2][i]);
                    temp[count][0] = fitness[0][i];
                    temp[count][1] = fitness[1][i];
                    temp[count][2] = fitness[2][i];
                    count++;
                }
            }
        }
    }

}
