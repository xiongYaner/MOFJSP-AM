package com.gjr.fjspall.normativeDesign;


public interface OperationForMS extends Operation {
    int[][] run(int[][] MS, double probabilityIn);

    int[][] run(int[][] MS);

    int[] run(int[] MS);

    int[] run(int[] MS, double probabilityIn);
}
