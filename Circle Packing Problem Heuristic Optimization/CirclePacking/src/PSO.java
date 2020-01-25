import java.util.Arrays;
import java.util.Random;

public class PSO extends OptimizationAlgorithm {
    private final int PARTICLE_SIZE;
    private Particle[] particles;
    private Particle gBestParticle;
    private int dimension = 0;

    public static final int V_MAX = 4; // Maximum velocity change allowed.



    PSO(int iteration,int particleSize,int dimension,double target){
        super(target);
        this.iteration = iteration;
        this.dimension = dimension;
        PARTICLE_SIZE = particleSize;
        TARGET = target;
    }

    @Override
    public double solution() {

        initParticles();
        updateGlobalBestParticle();

        int iteration = 0;
        while (iteration < this.iteration && gBestParticle.fitness > TARGET) {

            for (int i = 0; i < PARTICLE_SIZE; i++) {

                particles[i].updateVelocity(gBestParticle, particles[i].pBestParticle);
                particles[i].updatePosition();

                particles[i].setFitness(OptimizationAlgorithm.calculateWidth(particles[i].circles));
                particles[i].updateLocalBestParticle();
                updateGlobalBestParticle();

                particles[i].update();
            }

            iteration++;
            //System.out.println(iteration + ". iteration     -     Global Best Value: " + gBestParticle.fitness);

        }

        System.out.print(String.format("Minimum width: %.3f",gBestParticle.fitness) + " Circle Order: ");
        printCircleOrder(gBestParticle.circles);

        return gBestParticle.fitness;


    }

    private void initParticles() {
        particles = new Particle[PARTICLE_SIZE];
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(dimension,circles);
        }
        gBestParticle = new Particle(particles[0].fitness, dimension,particles[0].circles);
        System.arraycopy(particles[0].position, 0, gBestParticle.position, 0, dimension);
        System.arraycopy(particles[0].velocity, 0, gBestParticle.velocity, 0, dimension);
        System.arraycopy(particles[0].circles, 0, gBestParticle.circles, 0, circles.length);

    }

    private void updateGlobalBestParticle() {

        for (Particle particle : particles) {
            if (particle.pBestParticle.fitness < gBestParticle.fitness) {
                gBestParticle.fitness = particle.pBestParticle.fitness;
                System.arraycopy(particle.pBestParticle.position, 0, gBestParticle.position, 0, particle.dimension);
                System.arraycopy(particle.pBestParticle.velocity, 0, gBestParticle.velocity, 0, particle.dimension);
                System.arraycopy(particle.pBestParticle.circles, 0, gBestParticle.circles, 0, circles.length);
            }
        }
    }

}
