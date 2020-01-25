public class IteratedLocalSearch extends OptimizationAlgorithm{
    public IteratedLocalSearch(int iteration,int populationSize,double target) {
        super(target);
        this.iteration = iteration;
        this.populationSize = populationSize;
    }

    public double solution(){

        Double[] minSolution = localSearch(circles);
        double minWidth = calculateWidth(minSolution);

        for(int i=0; i<iteration && minWidth > TARGET; ++i){
            Double[] mutatedSolution = neighbor(minSolution);
            mutatedSolution = localSearch(mutatedSolution);
            double mutatedSolutionWidth = calculateWidth(mutatedSolution);
            if(mutatedSolutionWidth < minWidth){
                minWidth = mutatedSolutionWidth;
                minSolution = mutatedSolution;
            }
        }

        System.out.print(String.format("Minimum width: %.3f",minWidth) + " Circle Order: ");
        printCircleOrder(minSolution);

        return minWidth;

    }
}