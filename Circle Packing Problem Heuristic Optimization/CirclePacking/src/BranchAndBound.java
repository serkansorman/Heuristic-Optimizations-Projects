import java.util.Arrays;

/**
 * Reference: https://www.geeksforgeeks.org/traveling-salesman-problem-using-branch-and-bound-2/
 */
public class BranchAndBound extends OptimizationAlgorithm{

    int N;
    int final_path[] ;
    Double result_path[];
    boolean visited[];
    double final_res = Double.MAX_VALUE;
    double result = Double.MAX_VALUE;
    private double[][] adj;

    protected BranchAndBound(double TARGET) {
        super(TARGET);
    }

    @Override
    public double solution() {
        N = circles.length;
        result_path = new Double[N];
        for(int i=0; i<N; ++i){
            swap(circles,0,i);
            startBranching();
            compareWithReverse();
        }

        System.out.print(String.format("Minimum width: %.3f",result) + " Circle Order: ");

        for(int i = 0; i<result_path.length; ++i)
            System.out.print(result_path[i]+ " ");


        return result;

    }

    private void compareWithReverse(){

        Double[] finalResult1 = new Double[circles.length];
        for(int i = 0; i<circles.length; ++i)
            finalResult1[i] = circles[final_path[i]];

        Double[] finalResult2 = new Double[circles.length];
        int k=0;
        for(int i = 1; i<final_path.length; ++i,++k)
            finalResult2[k] = circles[final_path[i]];

        double final_res1 =  calculateWidth(finalResult1);
        double final_res2 =  calculateWidth(finalResult2);

        if(final_res1 < final_res2 && final_res1 < result){
            result = final_res1;
            System.arraycopy(finalResult1,0,result_path,0,N);
        }
        else if(final_res2 < final_res1 && final_res2 < result){
            result = final_res2;
            System.arraycopy(finalResult2,0,result_path,0,N);
        }
    }

    private void swap(Double[] input, int a, int b) {
        Double tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private void startBranching(){

        final_res = Double.MAX_VALUE;
        adj = new double[N][N];
        visited = new boolean[N];
        final_path = new int[N + 1];
        constructGraph();
        int curr_path[] = new int[N + 1];

        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);

        for (int i = 0; i < N; i++)
            curr_bound += (firstMin(adj, i) +
                    secondMin(adj, i));
        curr_bound = (curr_bound==1)? curr_bound/2 + 1 :
                curr_bound/2;

        visited[0] = true;
        curr_path[0] = 0;

        TSPRec(adj, curr_bound, 0, 1, curr_path);

    }

    private void constructGraph(){
        for(int i = 0; i<N; ++i){
            for(int j = 0; j<N; ++j){
                if(i != j){
                    Double arr[] = new Double[2];
                    arr[0] = circles[i];
                    arr[1] = circles[j];
                    adj[i][j] = calculateWidth(arr) - (arr[0] + arr[1]);
                }
                else
                    adj[i][j] = -1;
            }
        }
    }


    void copyToFinal(int curr_path[])
    {
        for (int i = 0; i < N; i++)
            final_path[i] = curr_path[i];
        final_path[N] = curr_path[0];
    }


    double firstMin(double adj[][], int i)
    {
        double min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }

    double secondMin(double adj[][], int i) {
        double first = Double.MAX_VALUE, second = Double.MAX_VALUE;
        for (int j=0; j<N; j++) {
            if (i == j)
                continue;

            if (adj[i][j] <= first) {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }

    private void TSPRec(double adj[][], double curr_bound, double curr_weight,
                       int level, int curr_path[]) {
        if (level == N) {
            if (adj[curr_path[level - 1]][curr_path[0]] != 0) {
                double curr_res = curr_weight +
                        adj[curr_path[level-1]][curr_path[0]];

                if (curr_res < final_res) {
                    copyToFinal(curr_path);
                    final_res = curr_res;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (adj[curr_path[level-1]][i] != 0 &&
                    !visited[i]) {
                double temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];

                if (level==1)
                    curr_bound -= ((firstMin(adj, curr_path[level - 1]) +
                            firstMin(adj, i))/2);
                else
                    curr_bound -= ((secondMin(adj, curr_path[level - 1]) +
                            firstMin(adj, i))/2);

                if (curr_bound + curr_weight < final_res) {
                    curr_path[level] = i;
                    visited[i] = true;

                    TSPRec(adj, curr_bound, curr_weight, level + 1,
                            curr_path);
                }

                curr_weight -= adj[curr_path[level-1]][i];
                curr_bound = temp;

                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }
}
