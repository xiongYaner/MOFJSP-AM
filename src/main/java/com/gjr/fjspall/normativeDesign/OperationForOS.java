package com.gjr.fjspall.normativeDesign;


public interface OperationForOS extends Operation {

    int[][] run(int[][] OS, double probabilityIn);

    int[][] run(int[][] OS);

    int[] run(int[] OS, double probabilityIn);

    int[] run(int[] OS);
}
