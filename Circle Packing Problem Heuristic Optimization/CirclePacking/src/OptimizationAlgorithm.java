import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class OptimizationAlgorithm {

    protected int iteration;
    protected int populationSize;
    protected double TARGET;
    protected Double[] circles;

    protected OptimizationAlgorithm(double TARGET){
        this.TARGET = TARGET;
    }

    public abstract double solution();

    public double solve(Double[] circles){
        System.out.println("######### "+this.getClass().getName()+" #########");
        double result;
        if(areCirclesSame(circles)){
            result = circles[0] * 2 * circles.length;
            System.out.print(String.format("Minimum width: %.3f",result) + " Circle Order: ");
            printCircleOrder(circles);
        }
        else{
            this.circles = new Double[circles.length];
            System.arraycopy(circles,0,this.circles,0,circles.length);
            result = solution();
        }

        return result;

    }

    public static double calculateWidth(Double[] circles){
        double dist = 0.0;
        for(int j = 0; j< circles.length - 1; ++j){
            dist += Math.sqrt(Math.pow(circles[j] + circles[j+1],2) - Math.pow(circles[j] - circles[j+1],2));
        }
        return  circles[0] + circles[circles.length-1] + dist;
    }

    protected boolean areCirclesSame(Double[] circles){
        for(int i=0; i<circles.length - 1; ++i){
            if(!circles[i].equals(circles[i + 1]))
                return false;
        }

        return true;
    }
    public static Double[] neighbor(Double[] circles){
        Double[] tempCircles = new Double[circles.length];
        System.arraycopy(circles,0,tempCircles,0,circles.length);
        List<Double> circleList = Arrays.asList(tempCircles);
        Collections.shuffle(circleList);
        return (Double[])circleList.toArray();
    }

    protected void printCircleOrder(Double[] resultOrder){
        for(Double i : resultOrder)
            System.out.print(i+" ");
        System.out.print(" ");
    }

    protected List<Double[]> generateNeighborhood(Double[] circles){
        List<Double[]> population = new ArrayList<>();
        for(int i=0; i<populationSize; ++i){
            population.add(neighbor(circles));
        }
        return population;
    }

    public Double[] localSearch(Double[] circles){
        List<Double[]> neighborhood = generateNeighborhood(circles);

        Double[] minSolution = new Double[circles.length];
        System.arraycopy(circles,0,minSolution,0,circles.length);
        double minWidth = calculateWidth(minSolution);

        for(Double[] neighbor : neighborhood){
            double currentWidth = calculateWidth(neighbor);
            if(currentWidth < minWidth){
                minWidth = currentWidth;
                System.arraycopy(neighbor,0,minSolution,0,circles.length);
            }
           /* else
                return minSolution;*/
        }

        return minSolution;
    }


}
