package com.gjr.fjspall.MOFJSP;


import com.gjr.fjspall.Utils.BaseMethod;

import java.util.ArrayList;
import java.util.Arrays;


public class Pareto {

    public static int[] paretoSort(int[][] fitness1) {
        int[][] fitness = new int[fitness1[0].length][fitness1.length];
        for (int i = 0; i < fitness.length; i++) {
            for (int j = 0; j < fitness[0].length; j++) {
                fitness[i][j] = fitness1[j][i];
            }
        }
        int[] level = new int[fitness.length];
        int font = 1;
        while (BaseMethod.isMember(0, level)) {
            ArrayList<Integer> challenge = new ArrayList<>();
            for (int i = 0; i < fitness.length; i++) {
                if (level[i] == 0) {
                    challenge.add(i);
                    break;
                }
            }
            for (int i = challenge.get(0) + 1; i < fitness.length; i++) {
                int[] oneZero = new int[challenge.size()];
                if (level[i] == 0) {
                    for (int k = 0; k < challenge.size(); k++) {
                        oneZero[k] = BaseMethod.ifDominate(fitness[i], fitness[challenge.get(k)]);
                    }
                    if (!BaseMethod.isMember(-1, Arrays.stream(oneZero).toArray())) {
                        if (BaseMethod.isMember(1, Arrays.stream(oneZero).toArray())) {
                            int len = oneZero.length;
                            int kkk = 0;
                            for (int kk = 0; kk < len; kk++) {
                                if (oneZero[kkk] == 1) {
                                    challenge.remove(kk);
                                    --kk;
                                    --len;
                                }
                                kkk++;
                            }
                        }
                        challenge.add(i);
                    }
                }
            }
            for (Integer integer : challenge) {
                level[integer] = font;
            }
            font++;
        }
        return level;
    }

}
