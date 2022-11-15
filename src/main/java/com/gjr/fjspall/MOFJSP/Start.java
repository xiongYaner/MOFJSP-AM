package com.gjr.fjspall.MOFJSP;

import com.gjr.fjspall.Constant.InstanceConstant;

import java.util.Arrays;


public class Start {
    public static void main(String[] args) {
        String[] handleInstance = {"Ge12-5", "Ge15-8", "Ge20-10", "Ge40-8", "Ge50-15", "Ge80-5", "Ge100-8", "Ge150-15", "Ge200-10", "Ge300-20"};
        long[] runtime = new long[handleInstance.length];
        InstanceConstant.INSTANCE_LIST.addAll(Arrays.asList(handleInstance));
        for (int i = 0; i < handleInstance.length; i++) {
            Main main = new Main(handleInstance[i]);
            main.run();
            runtime[i]=main.getRuntime();
        }
    }


}
class Test{
    public static void main(String[] args) {
        Main main = new Main("Ge12-5");
        main.run();
    }
}