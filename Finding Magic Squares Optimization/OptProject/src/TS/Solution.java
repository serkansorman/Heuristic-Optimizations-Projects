package TS;


import Common.OptimizationAlgorithm;

import java.util.Arrays;

public class Solution{

    private int[] square;
    private int fitness;

    public Solution() {
    }

    public Solution(int[] square) {
        this.square = new int[OptimizationAlgorithm.arraySize];
        System.arraycopy(square,0,this.square,0,this.square.length);
    }

    public Solution(int[] square, int fitness) {
        this(square);
        this.fitness = fitness;
    }

    public int[] getSquare() {
        return square;
    }


    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getFitness() {
        return fitness;
    }


    @Override
    public boolean equals(Object object) {
        return object.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Arrays.hashCode(this.square);
        return hash;
    }
}
