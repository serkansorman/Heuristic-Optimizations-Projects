package Common;


import GA.GeneticAlgorithm;
import TS.TabuSearch;

public class Main {

    public static void main(String... args){
        GeneticAlgorithm magicSquareFinder = new GeneticAlgorithm(4,100000,
                10,8,2000,0.5);
        long startTime = System.nanoTime();
        magicSquareFinder.startGeneticAlgorithm();
        long timeElapsed = System.nanoTime() - startTime;
        System.out.println("Total time: "+ timeElapsed / 1000000 +"ms");

       /* TabuSearch tabuSearch = new TabuSearch(4,10,5000,
                200,50,10);
        long startTime = System.nanoTime();
        tabuSearch.startTabuSearch();
        long timeElapsed = System.nanoTime() - startTime;
        System.out.println("Total time: "+ timeElapsed / 1000000 +"ms");*/

    }
}
