package com.gjr.fjspall.MOFJSP;

import com.gjr.fjspall.Operations.*;
import com.gjr.fjspall.Operations.ResetForFJSP;
import com.gjr.fjspall.Utils.FindTWithMS;
import com.gjr.fjspall.Utils.InstancesReader;

import java.io.File;


public class Main {
    String instance = "Ge12-5";
    int maxThreshold = 2 * (InstancesReader.jobNum + InstancesReader.machineNum);

    long runtime = 0;

    public Main() {
    }

    public Main(String instance) {
        this.instance = instance;
    }

    public String getInstance() {
        return instance;
    }

    public long getRuntime() {
        return runtime;
    }

    public void run() {
        new InstancesReader().txt2String(new File(System.getProperty("user.dir") + "/src/main/resources/Data/FJSP/" + instance + ".txt"));
        InitialForFJSPOfLoad initial = new InitialForFJSPOfLoad(Parameter.populationSize);
        LeftInsertForFJSP decode = new LeftInsertForFJSP();
        SwapTwoJobs swapTwoJobs = new SwapTwoJobs();
        IPOX ipox = new IPOX();
        UX ux = new UX();
        OOSelection selection = new OOSelection();
        OneRandomPos oneRandomPos = new OneRandomPos();
        HTLForFJSP htl = new HTLForFJSP();
        HSMForFJSP hsm = new HSMForFJSP();
        OTLForFJSP otl = new OTLForFJSP();
        ResetForFJSP resetForFJSP = new ResetForFJSP();
        AS AS = new AS();
        initial.initializationMultipleObject();
        initial.initializationOSRandom();
        int[][] OS = initial.getOS();
        int[][] MS = initial.getMS();
        int[][] T = initial.getT();
        decode.decode(OS, MS, T);
        int[] ms = decode.getMakespan();
        int[] wt = Fitness.WT(T);
        int[] wm = Fitness.WM(MS, T);
        int[][] fitness = {ms, wt, wm};
        int[] level = Pareto.paretoSort(fitness);
        ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), fitness.clone(), level);
        ASOperation.update(AS);

        int[] threshold = new int[Parameter.populationSize];
        int[][] newFitness;
        for (int i = 0; i < Parameter.maxCycleIndex; i++) {
            Parameter.crossoverProbability = 0.8 * (1 - 0.8 * ((double) i / Parameter.maxCycleIndex));
            OS = swapTwoJobs.run(OS);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms.clone(), wt.clone(), wm.clone()};
            level = Pareto.paretoSort(newFitness.clone());
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            MS = oneRandomPos.run(MS);
            T = FindTWithMS.correspondingT(MS);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms.clone(), wt.clone(), wm.clone()};
            level = Pareto.paretoSort(newFitness.clone());
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            selection.select(OS, MS, T, level);
            OS = selection.getNewOS();
            MS = selection.getNewMS();
            MS = ux.run(MS, Parameter.crossoverProbability);
            T = FindTWithMS.correspondingT(MS);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms, wt, wm};
            level = Pareto.paretoSort(newFitness.clone());
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            OS = ipox.run(OS, Parameter.crossoverProbability);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms, wt, wm};
            level = Pareto.paretoSort(newFitness);
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            MS = htl.runWithT(MS, T, ms);
            T = FindTWithMS.correspondingT(MS);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms.clone(), wt.clone(), wm.clone()};
            level = Pareto.paretoSort(newFitness.clone());
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            MS = otl.runWithT(MS, T, ms);
            T = FindTWithMS.correspondingT(MS);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms.clone(), wt.clone(), wm.clone()};
            level = Pareto.paretoSort(newFitness.clone());
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            MS = hsm.runWithT(MS, T, ms);
            T = FindTWithMS.correspondingT(MS);
            decode.decode(OS, MS, T);
            ms = decode.getMakespan();
            wt = Fitness.WT(T);
            wm = Fitness.WM(MS, T);
            newFitness = new int[][]{ms.clone(), wt.clone(), wm.clone()};
            level = Pareto.paretoSort(newFitness.clone());
            ASOperation.joinAS(AS, MS.clone(), OS.clone(), T.clone(), newFitness.clone(), level.clone());
            ASOperation.update(AS);
            for (int j = 0; j < Parameter.populationSize; j++) {
                if ((fitness[0][j] >= newFitness[0][j] && fitness[1][j] >= newFitness[1][j] && fitness[2][j] >= newFitness[2][j]) &&
                        (fitness[0][j] > newFitness[0][j] || fitness[1][j] > newFitness[1][j] || fitness[2][j] > newFitness[2][j])) {
                    threshold[j]++;
                } else {
                    threshold[j] = 0;
                }
            }
            for (int j = 0; j < Parameter.populationSize; j++) {
                if (threshold[j] > maxThreshold) {
                    resetForFJSP.reset(OS[j], MS[j], T[j]);
                    threshold[j] = 0;
                }
            }

        }
        System.out.println(AS);
    }
}
