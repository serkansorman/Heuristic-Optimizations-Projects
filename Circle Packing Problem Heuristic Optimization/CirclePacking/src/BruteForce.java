import java.util.ArrayList;
import java.util.List;

public class BruteForce extends  OptimizationAlgorithm{

    protected BruteForce(double TARGET) {
        super(TARGET);
    }

    public double solution(){
        int size = circles.length;
        List<Double[]> permutations  = new ArrayList<>();
        getPermutations(size,circles,permutations);

        double minWidth = Double.MAX_VALUE;
        int minWidthIndex = 0;

        for(int i = 0; i<permutations.size(); ++i){
            //Find distance increment from center to center.
            double width = calculateWidth(permutations.get(i));
            if(width < minWidth){
                minWidth = width;
                minWidthIndex = i;
            }
        }

        System.out.print(String.format("Minimum width: %.3f",minWidth) + " Circle Order: ");
        printCircleOrder(permutations.get(minWidthIndex));

        return minWidth;
    }

    private void swap(Double[] input, int a, int b) {
        Double tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }


    private void getPermutations(int n, Double[] elements, List<Double[]> permutations) {
        if(n == 1) {
            Double[] temp = new Double[elements.length];;
            System.arraycopy(elements, 0, temp, 0, elements.length);
            permutations.add(temp);
        } else {
            for(int i = 0; i < n-1; i++) {
                getPermutations(n - 1, elements,permutations);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            getPermutations(n - 1, elements,permutations);
        }
    }


}
