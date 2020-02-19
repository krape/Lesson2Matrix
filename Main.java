package com.company;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random random = new Random();
    int[][] a, b, c;

    public static void main(String[] args) {
        int m, n, k, numTreads;
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter number of rows for first matrix: ");
        m = scanner.nextInt();
        System.out.println("Enter number of cols for first matrix: ");
        n = scanner.nextInt();
        System.out.println("Enter number of cols for second matrix: ");
        k = scanner.nextInt();
        System.out.println("Enter number of threads: ");
        numTreads = scanner.nextInt();
        int[][] a = generateMatrix(m, n);
        int[][] b = generateMatrix(n, k);
        int[][] c = new int[m][k];
        getResultMatrix(numTreads, m, k, a, b, c);
//        c = multiplyMatrix(a,b);

        System.out.println("This is A");
        for (int[]d: a) {
            System.out.println(Arrays.toString(d));
        }
        System.out.println("This is B");
        for (int[]d: b) {
            System.out.println(Arrays.toString(d));
        }
        System.out.println("This is C");
        for (int[]d: c) {
            System.out.println(Arrays.toString(d));
        }
    }

    static int[][] generateMatrix(int m, int n) {
        int[][] mn = new int[m][n];
        for (int i = 0; i < m; i++) {
            int[] elem = new int[n];
            for (int j = 0; j < n; j++) {
                elem[j] = random.nextInt(10) + 1;
            }
            mn[i] = elem;
        }
        return mn;
    }

    static int[][] multiplyMatrix(int[][] a, int[][] b) {
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            int[] elem = new int[b[0].length];
            for (int j = 0; j < b[0].length; j++) {
                int sum = 0;
                for (int q = 0; q < b.length; q++) {
                    sum += a[i][q] * b[q][j];

                }
                elem[j] = sum;
            }
            c[i] = elem;
        }
        return c;
    }

    static void getResultMatrix(int numTreads, int m, int k, int[][] a, int[][] b, int[][] c) {
        Thread[] threads = new Thread[numTreads];
        int numElements = m * k;
        int rem = numElements % numTreads;
        int numElementsInThread = numElements / numTreads;
        MultiplyTread[] arrElem = new MultiplyTread[numElementsInThread];
        for (int i = 0; i < numTreads; i++) {
            int start = i * numElementsInThread;
            int end = start + numElementsInThread;
            MultiplyTread multThread = new MultiplyTread(a, b, c, start, end);
            arrElem[i] = multThread;
            threads[i] = new Thread(arrElem[i]);
            threads[i].start();
        }
        for (int i = 0; i < rem; i++) {
            int start = numElements - rem;
            MultiplyTread multThread = new MultiplyTread(a, b, c, start, numElements);
            multThread.run();
        }
        for (int i = 0; i < numTreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
