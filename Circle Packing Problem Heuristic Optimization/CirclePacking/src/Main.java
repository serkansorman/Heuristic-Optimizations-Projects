

public class Main {

    static double  greedyResult = 0;
    static double BBresult = 0;
    static double SAresult = 0;
    static double ILSresult = 0;
    static double VNSresult = 0;
    static double PSOresult = 0;
    static double ACOresult = 0;


    static long greedyTime = 0;
    static long BBtime = 0;
    static long SAtime = 0;
    static long ILStime = 0;
    static long VNStime = 0;
    static long PSOtime = 0;
    static long ACOtime = 0;



    private static void showExecutionTime(OptimizationAlgorithm optimizationAlgorithm,Double[] circles){
        long startTime = System.nanoTime();
        double result = optimizationAlgorithm.solve(circles);
        long timeElapsed = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Total time: "+ timeElapsed  +"ms");

        if(optimizationAlgorithm instanceof  Greedy){
            greedyResult += result;
            greedyTime += timeElapsed;
        }
        else if(optimizationAlgorithm instanceof  BranchAndBound){
            BBresult += result;
            BBtime += timeElapsed;
        }
        else if(optimizationAlgorithm instanceof  SimulatedAnnealing){
            SAresult += result;
            SAtime += timeElapsed;
        }
        else if(optimizationAlgorithm instanceof  IteratedLocalSearch){
            ILSresult += result;
            ILStime += timeElapsed;
        }
        else if(optimizationAlgorithm instanceof  VNS){
            VNSresult += result;
            VNStime += timeElapsed;
        }
        else if(optimizationAlgorithm instanceof  PSO){
            PSOresult += result;
            PSOtime += timeElapsed;
        }
        else if(optimizationAlgorithm instanceof  ACO){
            ACOresult += result;
            ACOtime += timeElapsed;
        }

    }

    private static void executeAll(Double[] circles){
        //showExecutionTime(new BruteForce(772.099),circles);
        showExecutionTime(new Greedy(913.7),circles);
        showExecutionTime(new BranchAndBound(913.7),circles);
        /*showExecutionTime(new SimulatedAnnealing(50000,0,1.0,.0001,0.9,913.7),circles);
        showExecutionTime(new IteratedLocalSearch(3000,1000,913.7),circles);
        showExecutionTime(new VNS(500,1000,500,913.7),circles);
        showExecutionTime(new PSO(10000,300,1,913.7),circles);
        showExecutionTime(new ACO(100000,300,0.7,913.7),circles);*/
    }


    public static void main(String... args){
        /*Double[] circles = {5.0, 5.0, 7.0, 47.0, 341.0, 918.0, 7.0, 4.0, 251.0, 1.0,
                77.0, 341.0, 5.0};*/
        Double [] circles = {2.0,6.0,8.0,11.0,5.0,4.0,1.0,3.0,40.0,10.0,30.0,6.0};
        //Double[] circles = {5.0 ,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0};
        //Double [] circles = {99.0,5.0,2.0,500.0,650.0,4.0,100.0,121.0,987.0,7.0};
        //Double [] circles = {99.0,5.0,2.0,500.0,650.0,4.0,100.0,32.0};
        //Double [] circles = {2.0,121.0,42.0,995.0,345.0,2.0};
        //Double [] circles = {1.0,2.0,2.0};
        //Double [] circles = {1.0,2.0,3.0,4.0};



        int numOfTry = 1;
        for(int i=0; i<numOfTry; ++i)
            executeAll(circles);

        System.out.println("Greedy Average Result: "+ greedyResult/numOfTry + " Greedy Average Time: " + greedyTime/numOfTry);
        System.out.println("BB Average Result: "+ BBresult/numOfTry + " BB Average Time: " + BBtime/numOfTry);
        System.out.println("SA Average Result: "+ SAresult/numOfTry + " SA Average Time: " + SAtime/numOfTry);
        System.out.println("ILS Average Result: "+ ILSresult/numOfTry + " ILS Average Time: " + ILStime/numOfTry);
        System.out.println("VNS Average Result: "+ VNSresult/numOfTry + " VNS Average Time: " + VNStime/numOfTry);
        System.out.println("PSO Average Result: "+ PSOresult/numOfTry + " PSO Average Time: " + PSOtime/numOfTry);
        System.out.println("ACO Average Result: "+ ACOresult/numOfTry + " ACO Average Time: " + ACOtime/numOfTry);



    }
}

