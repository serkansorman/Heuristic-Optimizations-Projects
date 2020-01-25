package GA;

import Common.OptimizationAlgorithm;

import java.util.Random;

/**
 * Referenced http://www.ceng.metu.edu.tr/~ucoluk/research/publications/tspnew.pdf
 */
public class Crossover{
    
    private final Random random = new Random();
    
    public int[][] crossover(int[] square1, int[] square2) {
        int[] inv1 = buildInversionSequenceFromSquare(square1);
        int[] inv2 = buildInversionSequenceFromSquare(square2);
        int crossoverPoint = generateCrossoverPoint();

        int[] invCross1 = crossInversionSequences(inv1, inv2, crossoverPoint);
        int[] invCross2 = crossInversionSequences(inv2, inv1, crossoverPoint);
        
        int[] child1 = buildSquareFromInversionSequence(invCross1);
        int[] child2 = buildSquareFromInversionSequence(invCross2);
        
        return new int[][] { child1, child2 };
    }
    
    private int generateCrossoverPoint() {
        return random.nextInt(OptimizationAlgorithm.arraySize - 2) + 1;
    }
    
    private int[] crossInversionSequences(int[] inv1, int[] inv2,
            int crossoverPoint) {
        int N = inv1.length;
        int[] result = new int[N];
        
        for (int i = 0; i < N; i++) {
            if (i <= crossoverPoint) {
                result[i] = inv1[i];
            } else {
                result[i] = inv2[i];
            }
        }

        return result;
    }
    
    private int[] buildInversionSequenceFromSquare(int[] square) {
        int N = square.length;
        int[] inversion = new int[N];
        
        for (int i = 0, n = 1; i < N; i++, n++) {
            inversion[i] = 0;
            
            for (int j = 0; square[j] != n; j++) {
                if (square[j] > n) inversion[i]++;
            }
        }
        
        return inversion;
    }

    private int[] buildSquareFromInversionSequence(int[] inv) {
        int N = inv.length;
        int[] pos = new int[N];
        
        for (int i = N - 1; i >= 0; i--) {
            pos[i] = inv[i];

            for (int m = i + 1; m < N; m++) {
                if (pos[m] >= inv[i]) pos[m]++;
            }
        }
        
        int[] square = new int[N];
        for (int i = 0; i < N; i++) square[pos[i]] = i + 1;
        return square;
    }
    
}
