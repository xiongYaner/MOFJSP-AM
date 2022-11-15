package com.gjr.fjspall.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BaseMethod {
    public static int[] randomDisturb(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int temp = new Random().nextInt(array.length - 1 - i + 1) + i;
            int tempNum = array[i];
            array[i] = array[temp];
            array[temp] = tempNum;
        }
        return array;
    }


    public static int getIndex(int[] array, int value) {
        int index = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }


    public static int max(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }


    public static int min(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static int maxTwo(int[][] array) {
        int maxTwo = max(array[0]);
        for (int i = 1; i < array.length; i++) {
            if (maxTwo < max(array[i])) {
                maxTwo = max(array[i]);
            }
        }
        return maxTwo;
    }

    public static boolean isMember(int number, int[] array) {
        boolean judge = false;
        for (int i = 0; i < array.length; i++) {
            if (number == array[i]) {
                judge = true;
                break;
            }
        }
        return judge;
    }


    public static int indexOfMax(double[] array) {
        int index = 0;
        double max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }

    public static int indexOfHalfMaxRandom(double[] array) {
        int length = array.length;
        double[] temp = new double[length];
        System.arraycopy(array, 0, temp, 0, length);
        ArrayList<Double> kthElements = findKthElements(temp, length / 2);
        Random random = new Random();
        int n = random.nextInt(kthElements.size());
        for (int i = 0; i < array.length; i++) {
            if (array[i]==kthElements.get(n)){
                return i;
            }
        }
        return 0;
    }

    public static ArrayList<Double> findKthElements(double[] arr, int k) {
        ArrayList<Double> res = new ArrayList<Double>();
        if (arr.length <= 0 || arr == null || arr.length < k) {
            return res;
        }
        Arrays.sort(arr);
        for (int i = arr.length - 1; i > arr.length - 1 - k; i--) {
            res.add(arr[i]);
        }
        return res;
    }

    public static int indexOfMin(int[] array) {
        int index = 0;
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                index = i;
            }
        }
        return index;
    }


    public static ArrayList<Integer> findAll(int[] array, int value) {
        ArrayList<Integer> pos = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                pos.add(i);
            }
        }
        return pos;
    }

    public static int ifDominate(int[] A, int[] B) {
        int relationship;
        int goalNum = A.length;
        int[] oneZero = new int[goalNum];
        for (int i = 0; i < A.length; i++) {
            if (A[i] < B[i]) {
                oneZero[i] = 1;
            } else {
                if (A[i] > B[i]) {
                    oneZero[i] = -1;
                } else {
                    oneZero[i] = 0;
                }
            }
        }
        if (!isMember(1, oneZero) && Arrays.stream(oneZero).sum() != 0) {
            relationship = -1;
        } else {
            if (!isMember(-1, oneZero) && Arrays.stream(oneZero).sum() != 0) {
                relationship = 1;
            } else {
                relationship = 0;
            }
        }
        return relationship;
    }

    public static boolean lineIfRepeat(int[] array, int[][] ARRAY) {
        boolean repeat = false;
        for (int i = 0; i < ARRAY.length; i++) {
            int sum = 0;
            for (int j = 0; j < array.length; j++) {
                sum += Math.abs(array[j] - ARRAY[i][j]);
            }
            if (sum == 0) {
                repeat = true;
                break;
            }
        }
        return repeat;
    }

    public static void arrayCopy(int[][] array, int[][] copy) {
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, copy[i], 0, array[i].length);
        }

    }

    public static void arrayCopy(int[][][] array, int[][][] copy) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.arraycopy(array[i][j], 0, copy[i][j], 0, array[i][j].length);
            }
        }
    }

    public static void arrayCopy(double[][][] array, double[][][] copy) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.arraycopy(array[i][j], 0, copy[i][j], 0, array[i][j].length);
            }
        }
    }

    public static void arrayListGive(ArrayList<int[]> list, int[] array) {
        int[] temp = new int[array.length];
        list.add(temp);
        for (int i = 0; i < array.length; i++) {
            list.get(list.size() - 1)[i] = array[i];
        }
    }


    public static int[] randomBiArray(int len) {
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            if (Math.random() <= 0.5) {
                array[i] = 1;
            } else {
                array[i] = 0;
            }
        }
        return array;
    }


    public static int sum(int[] num) {
        int result = 0;
        for (int j : num) {
            result += j;
        }
        return result;
    }


    public static void reversedOrder(int[] num, int a, int b) {
        if (a != b) {
            int max = Math.max(a, b);
            int min = Math.min(a, b);
            for (int i = 0; i < Math.abs(a - b) / 2 + 1; i++) {
                int temp = 0;
                temp = num[min + i];
                num[min + i] = num[max - i];
                num[max - i] = temp;
            }
        }
    }


    public static void rearInsert(int[] num, int a, int b) {
        int temp = num[a];
        for (int i = 0; i < b - a + 1; i++) {
            num[a + i] = num[a + i + 1];
        }
        num[b] = temp;
    }


    public static int[] setFirstPosZero(int[] array, int value) {
        int[] result = new int[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        int firstPos = findFirstPos(array, value);
        if (firstPos != -1) {
            result[firstPos] = 0;
        }
        return result;
    }

    public static int findFirstPos(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }


}







