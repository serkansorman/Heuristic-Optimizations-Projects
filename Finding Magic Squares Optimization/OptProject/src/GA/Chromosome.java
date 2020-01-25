package GA;

import Common.MagicSquareFitnessCalculator;

import java.util.Arrays;

public class Chromosome{
    
    private final MagicSquareFitnessCalculator fitnessCalculator;
    private final int[] square;
    private Integer fitness;
    
    public Chromosome(int[] square,MagicSquareFitnessCalculator fitnessCalculator) {
        this.square = square;
        this.fitnessCalculator = fitnessCalculator;
    }

    public int[] getSquare() {
        return square;
    }


    public int getFitness() {
        if (fitness == null) {
            fitness = fitnessCalculator.calculateFitness(square);
        }

        return fitness;
    }

    
    public String toString(boolean includeFitness) {
        return Arrays.toString(square) +
            (includeFitness ? " - Fitness: " + getFitness() : "");
    }

    @Override
    public String toString() {
        return toString(false);
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof Chromosome) {
            return object.toString().equals(this.toString());
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Arrays.hashCode(this.square);
        return hash;
    }

}
