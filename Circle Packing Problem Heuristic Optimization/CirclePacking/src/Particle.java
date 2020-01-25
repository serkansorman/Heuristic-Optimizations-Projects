import java.util.Random;

public class Particle implements Comparable<Particle>{


    double[] velocity;
    int[] position;
    int dimension;
    double fitness;
    Double[] circles; //solution
    Particle pBestParticle;

    public Particle(int numberOfItems,Double[] circles) {
        this.dimension = numberOfItems;
        position = new int[numberOfItems];
        velocity = new double[numberOfItems];
        this.circles = new Double[circles.length];


        System.arraycopy(OptimizationAlgorithm.neighbor(circles),0,this.circles,0,circles.length);
        fitness = OptimizationAlgorithm.calculateWidth(this.circles);

        Random rn = new Random();

        for (int i = 0; i < numberOfItems; i++) {
            int x = rn.nextInt(PSO.V_MAX + 1);
            velocity[i] = x;
            position[i] = x;
        }

        initPbestParticle();

    }

    public Particle(double fitness, int dimension,Double[] circles) {
        this.dimension = dimension;
        this.fitness = fitness;
        position = new int[dimension];
        velocity = new double[dimension];
        this.circles = new Double[circles.length];
    }

    void initPbestParticle(){
        pBestParticle = new Particle(fitness, dimension,circles);
        System.arraycopy(this.position, 0, pBestParticle.position, 0, dimension);
        System.arraycopy(this.velocity, 0, pBestParticle.velocity, 0, dimension);
        System.arraycopy(this.circles, 0, pBestParticle.circles, 0, circles.length);
        pBestParticle.fitness = this.fitness;

    }

    void updateVelocity(Particle gBest, Particle pBest) {

        double w = 0.689343;
        double c1 = 1.42694, c2 = 1.42694;

        for (int i = 0; i < position.length; i++) {
            double rand1 = Math.random();
            double rand2 = Math.random();
            double vValue  = w * velocity[i] + c1 * rand1 * (pBest.position[i] - position[i]) + c2 * rand2 * (gBest.position[i] - position[i]);
            if(vValue > PSO.V_MAX){
                velocity[i] = PSO.V_MAX;
            }
            else if(vValue < 0.0){
                velocity[i] = 0.0;
            }else{
                velocity[i] = vValue;
            }

        }
    }




    void updatePosition() {
        for (int i = 0; i < position.length; i++) {
            position[i] += velocity[i];
        }
    }

    void updateLocalBestParticle() {

        if (this.getFitness() < pBestParticle.getFitness()) {
            pBestParticle.fitness = this.fitness;
            System.arraycopy(this.position, 0, pBestParticle.position, 0, dimension);
            System.arraycopy(this.velocity, 0, pBestParticle.velocity, 0, dimension);
            System.arraycopy(this.circles, 0, pBestParticle.circles, 0, circles.length);

        }

    }

    void randomlyArrangeCircles(){

        int index1 = new Random().nextInt(circles.length);
        int index2 = 0;
        boolean done = false;
        while(!done)
        {
            index2 = new Random().nextInt(circles.length);
            if(index1 != index2){
                done = true;
            }
        }

        double temp = circles[index1];
        circles[index1] = circles[index2];
        circles[index2] = temp;
    }

    void update(){
        // The higher the position , the more changes it will need.
        int changes = (int)Math.floor(Math.abs(this.position[new Random().nextInt(dimension)]));
        for(int j = 0; j < changes; j++){
            if(new Random().nextBoolean()){
                randomlyArrangeCircles();
            }
        }
    }

    void setFitness(double f) {
        fitness = f;
    }

    double getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(Particle o) {
        return Double.compare(this.pBestParticle.getFitness(), o.pBestParticle.getFitness());
    }
}