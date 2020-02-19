package com.company;

public class MultiplyTread implements Runnable {
    int[][] a;
    int[][] b;
    int[][] c;
    private int start, end;

    public MultiplyTread(int[][] a, int[][] b, int[][] c, int start, int end) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {

        for (int indexElem = start; indexElem < end; indexElem++) {
            int sum = 0;
            int i = indexElem / b[0].length;
            int j = indexElem % b[0].length;
            for (int q = 0; q < b.length; q++) {
                sum += a[i][q] * b[q][j];
            }
            c[i][j] = sum;
        }

    }
}
