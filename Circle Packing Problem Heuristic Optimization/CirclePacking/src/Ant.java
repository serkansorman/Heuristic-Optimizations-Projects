public class Ant {

    private Double[] circles;
    private double fitness;

    public Ant(Double[] circles) {
        this.circles = OptimizationAlgorithm.neighbor(circles);
    }

    public Double[] getCircles() {
        return circles;
    }

    public void setCircles(Double[] circles) {
        System.arraycopy(circles,0,this.circles,0,circles.length);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
