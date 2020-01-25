public class SimulatedAnnealing extends OptimizationAlgorithm{

    // Initial and final temperature
    private double T = 1.0;
    // Temperature at which iteration terminates
    private double tMin = .0001;
    // Decrease in temperature
    private double alpha = 0.9;

    public SimulatedAnnealing(int iteration, int populationSize,double t, double tMin, double alpha,double target) {
        super(target);
        this.iteration = iteration;
        this.populationSize = populationSize;
        T = t;
        this.tMin = tMin;
        this.alpha = alpha;
    }

    public double solution(){

        Double[] minSolution = neighbor(circles);
        double minWidth = calculateWidth(circles);


        while(T > tMin && minWidth > TARGET){

            for(int i=0; i<iteration; ++i){
                Double[] newSolution = neighbor(minSolution);
                double newSolutionWidth = calculateWidth(newSolution);

                if(newSolutionWidth < minWidth){
                    minWidth = newSolutionWidth;
                    minSolution = newSolution;
                }
                else if(Math.pow(Math.E, (minWidth - newSolutionWidth)/T) > Math.random()){
                    minWidth = newSolutionWidth;
                    minSolution = newSolution;
                }
            }
            T *= alpha;
        }

        System.out.print(String.format("Minimum width: %.3f",minWidth) + " Circle Order: ");
        printCircleOrder(minSolution);

        return minWidth;
    }

}
