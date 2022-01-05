
import java.util.Random;

/*
-- Author by Alex Press
*/

//Main class
public class SGA1 {

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;

    public static void main(String[] args) {

        Random rn = new Random();

        SGA1 obj1 = new SGA1();

        //Initialize population
        obj1.population.initializePopulation(10);

        //Calculate fitness of each individual
        obj1.population.calculateFitness();

        System.out.println("Generation: " + obj1.generationCount + " Fittest: " + obj1.population.fittest);

        //While population gets an individual with maximum fitness
        while (obj1.population.fittest < 5) {
            ++obj1.generationCount;

            //Do selection
            obj1.selection();

            //Do crossover
            obj1.crossover();

            //Do mutation under a random probability
            if (rn.nextInt()%7 < 5) {
                obj1.mutation();
            }

            //Add fittest offspring to population
            obj1.addFittestOffspring();

            //Calculate new fitness value
            obj1.population.calculateFitness();

            System.out.println("Generation: " + obj1.generationCount + " Fittest: " + obj1.population.fittest);
        }

        System.out.println("\nSolution found in generation " + obj1.generationCount);
        System.out.println("Fitness: "+obj1.population.getFittest().fitness);
        System.out.print("Genes: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(obj1.population.getFittest().genes[i]);
        }

        System.out.println("");

    }

    //Selection
    void selection() {

        //Select the most fittest individual
        fittest = population.getFittest();

        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    //Mutation
    void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //Get fittest offspring
    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }

}


