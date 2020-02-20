package com.company;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int m, n, k, numTreads;
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter number of rows for first matrix: ");
        m = scanner.nextInt();
        System.out.println("Enter number of cols for the first matrix: ");
        n = scanner.nextInt();
        System.out.println("Enter number of cols for the second matrix: ");
        k = scanner.nextInt();
        System.out.println("Enter number of threads: ");
        numTreads = scanner.nextInt();
        int[][] a = new int[m][n];
        generateMatrix(a, m, n);
        int[][] b = new int[n][k];
        generateMatrix(b, n, k);
        int[][] c = new int[m][k];
        int[][] e = new int[m][k];
        long time = System.currentTimeMillis();
        getResultMatrix(numTreads, m, k, a, b, c);
        long res1 = (System.currentTimeMillis() - time);
        System.out.println("time: " + res1);

        long time2 = System.currentTimeMillis();
        multiplyMatrix(a,b,e);
        long res2 = (System.currentTimeMillis() - time2);
        System.out.println("time 2: "+res2);

//        System.out.println("This is A");
//        for (int[]d: a) {
//            System.out.println(Arrays.toString(d));
//        }
//        System.out.println("This is B");
//        for (int[]d: b) {
//            System.out.println(Arrays.toString(d));
//        }
//        System.out.println("This is C");
//        for (int[]d: c) {
//            System.out.println(Arrays.toString(d));
//        }
    }

    static void generateMatrix(int[][] mn,int m, int n) {
        Random random = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mn[i][j] = random.nextInt(10) + 1;
            }
        }
    }

    static int[][] multiplyMatrix(int[][] a, int[][] b, int[][] c) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                int sum = 0;
                for (int q = 0; q < b.length; q++) {
                    sum += a[i][q] * b[q][j];
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    static void getResultMatrix(int numTreads, int m, int k, int[][] a, int[][] b, int[][] c) {
        Thread[] threads = new Thread[numTreads];
        int numElements = m * k;
        int rem = numElements % numTreads;
        int numElementsInThread = numElements / numTreads;
        MultiplyTread[] arrElem = new MultiplyTread[numTreads];

        for (int i = 0; i < numTreads; i++) {
            int start = i * numElementsInThread;
            int end = start + numElementsInThread;
            MultiplyTread multThread = new MultiplyTread(a, b, c, start, end);
            arrElem[i] = multThread;
            threads[i] = new Thread(arrElem[i]);
            threads[i].start();
        }
            int start = numElements - rem;
            MultiplyTread multThread = new MultiplyTread(a, b, c, start, numElements);
            multThread.run();

        for (int i = 0; i < numTreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
