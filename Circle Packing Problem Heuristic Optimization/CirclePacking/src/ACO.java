
import java.util.*;

public class ACO extends OptimizationAlgorithm {


    private double evaporationRate;
    
    private List<Ant> ants;
    private HashMap<Double[],Double> pheromoneTable;

    private Double[] gBestCircles;
    private double gBest = Double.MAX_VALUE;



    public ACO(int iteration,int populationSize,double evaporationRate,double target) {
        super(target);
        this.iteration = iteration;
        this.populationSize = populationSize;
        this.evaporationRate = evaporationRate;

        ants = new ArrayList<>();
        pheromoneTable = new HashMap<>();
    }

    @Override
    public double solution() {
        gBestCircles = new Double[circles.length];
        System.arraycopy(circles,0,this.gBestCircles,0,circles.length);

        generateAnts();

        int i = 0;
        while(i < iteration && gBest > TARGET){
            updatePheromones();
            updateGlobalBest();
            redirectAnts();
            evaporate();
            //System.out.println(i + ". iteration     -     Global Best Value: " + gBest);

            ++i;
        }

        System.out.print(String.format("Minimum width: %.3f",gBest) + " Circle Order: ");
        printCircleOrder(gBestCircles);

        return gBest;

    }

    private void generateAnts(){
        for(int i = 0; i<populationSize; ++i){
            ants.add(new Ant(gBestCircles));
        }
    }

    private void calculateFitness(){
        for(Ant ant : ants){
            ant.setFitness(calculateWidth(ant.getCircles()));
        }
    }

    private void updatePheromones(){
        calculateFitness();
        for(Ant ant : ants){
            //The lower width, the higher pheromone
            double pheromone = 1 / ant.getFitness();
            if(pheromoneTable.containsKey(ant.getCircles()))
                pheromoneTable.put(ant.getCircles(),pheromoneTable.get(ant.getCircles()) + pheromone);
            else
                pheromoneTable.put(ant.getCircles(),pheromone);

        }
    }

    private void evaporate(){
        pheromoneTable.forEach((k, v) -> {
            v *= (1 - evaporationRate);
            pheromoneTable.replace(k, v);
        });
    }

    private void updateGlobalBest(){
        Double[] bestPheromoneSolution = Collections.max(pheromoneTable.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        double width = calculateWidth(bestPheromoneSolution);

        if(width < gBest){
            gBest = width;
            System.arraycopy(bestPheromoneSolution,0,gBestCircles,0,gBestCircles.length);
        }

    }

    private void redirectAnts(){
        Random random = new Random();
        for(Ant ant : ants){
            if(random.nextInt(10) < 8){// Ant chooses the path which has max pheromone at %80 rate
                ant.setCircles(gBestCircles);
            }
            else{ // Ant find new path
                ant.setCircles(neighbor(gBestCircles));
            }
        }
    }
}
