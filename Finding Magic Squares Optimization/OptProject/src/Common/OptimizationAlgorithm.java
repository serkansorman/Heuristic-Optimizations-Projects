package Common;


import java.util.*;

public class OptimizationAlgorithm {
    protected final Random random;
    protected final MagicSquareFitnessCalculator fitnessCalculator;
    protected int maxIteration;
    public static int size;
    public static int arraySize;


    public OptimizationAlgorithm(int size,int maxIteration) {
        OptimizationAlgorithm.size = size;
        OptimizationAlgorithm.arraySize = (int)Math.pow(size, 2);
        this.maxIteration = maxIteration;
        this.fitnessCalculator = new MagicSquareFitnessCalculator(size);
        random = new Random();
    }

    protected int[] generateRandomSolution(){

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < arraySize; i++) {
            list.add(i + 1);
        }

        Collections.shuffle(list);

        return list.stream().mapToInt(i -> i).toArray();
    }

    protected String formatSquare(int[] square){

        String[] items = Arrays.stream(square).mapToObj(i -> i + "")
                .toArray(String[]::new);

        final int size = (int)Math.sqrt(items.length);
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < items.length; i += size) {
            final String[] line = Arrays.copyOfRange(items, i, i + size);
            sb.append(String.join("\t", line)).append("\n");
        }

        return sb.toString().trim();
    }
}
