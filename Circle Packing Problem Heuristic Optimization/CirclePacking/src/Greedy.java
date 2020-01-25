import java.util.Arrays;
import java.util.List;

public class Greedy extends OptimizationAlgorithm{

    protected Greedy(double TARGET) {
        super(TARGET);
    }

    public double solution(){

        int size = circles.length;

        List<Double> optCircleOrder = Arrays.asList(new Double[size]);
        getOptCircleOrder(size,circles,optCircleOrder);

        Double[] solution = (Double[]) optCircleOrder.toArray();
        double width = calculateWidth(solution);


        System.out.print(String.format("Minimum width: %.3f",width) + " Circle Order: ");
        printCircleOrder(solution);

        return width;
    }


    private void getOptCircleOrder(int n, Double[] elements, List<Double> optCircleOrder) {

        int size = elements.length;
        Arrays.sort(elements);

        //Set at middle to min element
        optCircleOrder.set(size / 2,elements[0]);

        //Set left and right side of min element to other elements
        for(int i = 1; i<size - 1; ++i){
            if(i % 2 == 1){
                if(size / 2 - i >= 0)
                    optCircleOrder.set(size / 2 - i,elements[size - i]);
                if(size / 2 + i < size)
                    optCircleOrder.set(size / 2 + i,elements[size - (i+1)]);
            }
            else{
                if(size / 2 - i >= 0)
                    optCircleOrder.set(size / 2 - i,elements[i-1]);
                if(size / 2 + i < size)
                    optCircleOrder.set(size / 2 + i,elements[i]);
            }
        }
    }
}
