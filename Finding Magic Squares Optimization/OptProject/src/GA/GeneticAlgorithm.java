package GA;

import Common.OptimizationAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GeneticAlgorithm extends OptimizationAlgorithm {
    

    private final int populationSize;
    private final int eliteSize;
    private final int eliteDeathPeriod;
    private final double mutationProbability;

    private final ChromosomeComparator comparator;
    private final Crossover crossover;
    private final List<Chromosome> population;

    private int generationCount;
    private int generationCountWithElite;
    
    public GeneticAlgorithm(int size, int maxIteration, int populationSize, int eliteSize,
             int eliteDeathPeriod, double mutationProbability) {
        super(size,maxIteration);
        this.populationSize = populationSize;
        this.eliteSize = eliteSize;
        this.eliteDeathPeriod = eliteDeathPeriod;
        this.mutationProbability = mutationProbability;


        this.crossover = new Crossover();
        this.comparator = new ChromosomeComparator();
        this.population = new ArrayList<>();
    }
    

    public void startGeneticAlgorithm() {
        generationCount = generationCountWithElite = 0;

        generateInitialPopulation();

        int i = 0;
        while (i<maxIteration) {

            Collections.sort(population, comparator);

            if (checkBestSolution())
                break;

            createNewGeneration();
            ++i;
        }
        
    }

    private void generateInitialPopulation() {
        population.clear();

        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome(generateRandomSolution(),fitnessCalculator));
        }
    }

    
    private boolean checkBestSolution() {
        Chromosome bestSolution = population.get(0);

        if(bestSolution.getFitness() == 0){
            printMagicSquare(bestSolution);
            System.out.println("Search is completed");
            return true;
        }

        return false;
    }

    
    private void printMagicSquare(Chromosome magicSquare) {
        StringBuilder sb = new StringBuilder();

        sb.append("Generation number: ").append(generationCount).append("\n");
        sb.append(formatSquare(magicSquare.getSquare()));

        System.out.println(sb.toString());
    }
    
    private List<Chromosome> createMatingPool() {
        List<Chromosome> matingPool = new ArrayList<>();
        
        int poolSize = populationSize / 2;
        while (matingPool.size() < poolSize) {
            Chromosome i1 = getRandomChromosome(population);
            Chromosome i2 = getRandomChromosome(population);
            
            if (i1 == i2) {
                continue;
            }
            
            matingPool.add(i1.getFitness() > i2.getFitness() ? i1 : i2);
        }
        
        return matingPool;
    }
    
    private void createNewGeneration() {
        generationCount++;

        // Applies the elite death period
        if (eliteDeathPeriod != 0 && generationCountWithElite > eliteDeathPeriod) {
            population.subList(0, eliteSize).clear();
            generationCountWithElite = 0;
        } else {
            generationCountWithElite++;
        }
        
        List<Chromosome> matingPool = createMatingPool();
        
        // Elitism. Transfers the N best individuals to the next generation.
        if(eliteSize < population.size())
            population.subList(eliteSize, population.size()).clear();


        while (population.size() < populationSize) {
            Chromosome i1 = getRandomChromosome(matingPool);
            Chromosome i2 = getRandomChromosome(matingPool);
            Chromosome[] children = crossoverAndMutate(i1, i2);


            for (Chromosome child : children) {
                String representation = child.toString();
                boolean duplicate = false;

                for (Chromosome chromosome : population) {
                    if (representation.equals(chromosome.toString())) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    population.add(child);
                }
            }

        }
    }

    private Chromosome[] crossoverAndMutate(Chromosome parent1, Chromosome parent2) {
        int[][] children = crossover.crossover(parent1.getSquare(),
                parent2.getSquare());

        // Mutation
        for (int i = 0; i < children.length; i++) {
            int[] child = children[i];

            if (Math.random() <= mutationProbability) {
                int index1, index2;

                do {
                    index1 = random.nextInt(arraySize);
                    index2 = random.nextInt(arraySize);
                }
                while (index1 == index2);

                int aux = child[index1];
                child[index1] = child[index2];
                child[index2] = aux;

            }
        }
        
        Chromosome[] chromosomes = new Chromosome[children.length];
        
        for (int i = 0; i < chromosomes.length; i++) {
            chromosomes[i] = new Chromosome(children[i],fitnessCalculator);
        }
        
        return chromosomes;
    }


    private  <T> T getRandomChromosome(List<T> list) {
        int index = random.nextInt(list.size());
        return list.get(index);
    }

}

class ChromosomeComparator implements Comparator<Chromosome> {
    @Override
    public int compare(Chromosome o1, Chromosome o2) {
        return Integer.compare(o1.getFitness(),o2.getFitness());
    }
}
