import java.util.List;

public class VNS extends OptimizationAlgorithm{
    private int kMax;

    public VNS(int iteration, int populationSize,int kMax,double target) {
        super(target);
        this.iteration = iteration;
        this.populationSize = populationSize;
        this.kMax = kMax;
    }

    public double solution(){

        Double[] minSolution = circles;
        double minWidth = calculateWidth(circles);

        for(int i =0; i< iteration && minWidth > TARGET; ++i){

            int k = 1;

            while(k < kMax){
                Double[] newSolution = shaking(circles,k);
                newSolution = localSearch(newSolution);
                double newSolutionWidth = calculateWidth(newSolution);

                if(newSolutionWidth < minWidth){
                    minWidth = newSolutionWidth;
                    minSolution = newSolution;

                    k = 1;
                }
                else
                    ++k;
            }
        }

        System.out.print(String.format("Minimum width: %.3f",minWidth) + " Circle Order: ");
        printCircleOrder(minSolution);

        return minWidth;
    }

    private Double[] shaking(Double[] circles,int k){
        List<Double[]> neighborhood = generateNeighborhood(circles);
        return neighborhood.get(k);
    }
}
