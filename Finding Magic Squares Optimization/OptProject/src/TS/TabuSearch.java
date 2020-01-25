package TS;


import Common.OptimizationAlgorithm;

import java.util.*;

public class TabuSearch extends OptimizationAlgorithm {

    private Solution bestSolution;
    private Solution currentSolution;
    TabuList tabuList;

    private int maxIteration;
    private int maxIterationWithoutBetterSolution;
    private int tabuListMaxLength;
    private int maxTries;
    private int neighborhoodSize;
    private SolutionComparator solutionComparator;

    public TabuSearch(int size,int maxTries,int maxIteration,int maxIterationWithoutBetterSolution,int tabuListMaxLength,int neighborhoodSize) {
        super(size,maxIteration);
        currentSolution = new Solution();
        tabuList = new TabuList(tabuListMaxLength);
        this.maxIteration = maxIteration;
        this.maxIterationWithoutBetterSolution = maxIterationWithoutBetterSolution;
        this.tabuListMaxLength = tabuListMaxLength;
        this.maxTries = maxTries;
        this.neighborhoodSize = neighborhoodSize;
        this.solutionComparator = new SolutionComparator();
    }


    public void startTabuSearch(){

        int tries = 0;
        boolean optimalSolutionFound = false;

        while(tries < maxTries && !optimalSolutionFound) {

            //Generate random solution
            int[] randSquare = generateRandomSolution();
            currentSolution = new Solution(randSquare,fitnessCalculator.calculateFitness(randSquare));

            //Set current solution as a best solution
            bestSolution = new Solution(currentSolution.getSquare(),currentSolution.getFitness());

            int iterations = 0;
            int noBetterSolution = 0;
            while(!optimalSolutionFound && (iterations < maxIteration) && (noBetterSolution < maxIterationWithoutBetterSolution)) {

                ++iterations;

                List<Solution> candidateSolutions = getCandidateNeighbors(bestSolution,tabuList);

                Solution bestCandidateSolution = getBestCandidate(candidateSolutions);

                //Update current solution
                currentSolution = new Solution(bestCandidateSolution.getSquare(),bestCandidateSolution.getFitness());

                if(bestCandidateSolution.getFitness() < bestSolution.getFitness()) {
                    bestSolution = new Solution(bestCandidateSolution.getSquare(),bestCandidateSolution.getFitness());
                    noBetterSolution = 0;
                }
                else {
                    noBetterSolution++;
                }

                if(bestSolution.getFitness() == 0) {
                    optimalSolutionFound = true;
                    printMagicSquare(bestSolution);
                }

                tabuList.insert(bestCandidateSolution);
                //System.out.println(Arrays.toString(bestSolution.getSquare()) + " Fitness: "+bestSolution.getFitness() + " " + noBetterSolution );

            }

            tries++;
        }

    }



    private List<Solution> getCandidateNeighbors(Solution currentSolution,TabuList tabuList){

        List<Solution> candidateSolutions = new ArrayList<>();
        for(int i=0; i<neighborhoodSize; ++i){
            Solution candidateNeighbor = neighbor(currentSolution);
            if(!tabuList.isForbidden(candidateNeighbor)){
                candidateNeighbor.setFitness(fitnessCalculator.calculateFitness(candidateNeighbor.getSquare()));
                candidateSolutions.add(candidateNeighbor);
            }
        }

        return candidateSolutions;
    }

    private Solution neighbor(Solution currentSolution){

        Solution neighbor = new Solution(currentSolution.getSquare(),currentSolution.getFitness());

        for (int i = 0; i < 2; i++) {

            if (Math.random() <= 0.5) {
                int index1, index2;

                do {
                    index1 = random.nextInt(arraySize);
                    index2 = random.nextInt(arraySize);
                }
                while (index1 == index2);

                int temp = neighbor.getSquare()[index1];
                neighbor.getSquare()[index1] = neighbor.getSquare()[index2];
                neighbor.getSquare()[index2] = temp;

            }
        }

        return neighbor;
    }

    private Solution getBestCandidate(List<Solution> candidateSolutions){
        if(candidateSolutions.isEmpty())
            return currentSolution;
        return Collections.min(candidateSolutions,solutionComparator);
    }

    private void printMagicSquare(Solution magicSquare) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatSquare(magicSquare.getSquare()));
        sb.append("\n");

        System.out.println(sb.toString());
    }

}

class SolutionComparator implements Comparator<Solution> {
    @Override
    public int compare(Solution o1, Solution o2) {
        return Integer.compare(o1.getFitness(),o2.getFitness());
    }
}
