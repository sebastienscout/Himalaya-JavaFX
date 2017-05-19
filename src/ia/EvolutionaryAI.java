package ia;

import core.Action;
import core.Player;
import core.Village;
import java.util.ArrayList;
import java.util.Random;

public class EvolutionaryAI extends Player {

    private int mu;
    private int lambda;
    private int tournamentSize;
    private double crossOverRate;
    private double mutationRate;
    private int maxGeneration;
    private int nbGeneration;
    private Random random;
    private SolutionComparator comp = new SolutionComparator();
    private Solution bestSolActions = null;

    public EvolutionaryAI(String color, Village v) {
        super(color, v);
        random = new Random();
    }

    private void evalPop(Population population) {
        for (Solution individual : population.getIndividuals()) {
            individual.calculateFitness();
        }
    }

    private void initialization(Population parents) {
        parents.resize(this.mu);
        for (Solution individual : parents.getIndividuals()) {
            individual.init(this);
        }
    }

    private Solution tournament(Population parents) {
        Solution winner = parents.getRandomIndividual();
        Solution oneGuy = null;
        for (int i = 1; i < tournamentSize; i++) {
            oneGuy = parents.getRandomIndividual();
            if (oneGuy.getFitness() > winner.getFitness()) {
                winner = oneGuy;
            }
        }
        return winner;
    }

    private void selection(Population parents, Population genitors) {
        for (int i = 0; i < genitors.getSize(); i++) {
            genitors.setIndividual(i, tournament(parents));
        }
    }

    private void crossOver(Solution sol1, Solution sol2) {
        for (int i = 0; i < sol1.size(); i++) {
            if (random.nextFloat() < 0.5) {
                Action tmp = sol1.get(i);
                sol1.set(i, sol2.get(i));
                sol2.set(i, tmp);
            }
        }
    }

    private void mutation(Solution sol) {
        double rate = ((double) 1) / (double) sol.size();
        for (int i = 0; i < sol.size(); i++) {
            if (random.nextFloat() < rate) {
                sol.mutate(i);
            }
        }
    }

    private void randomVariation(Population genitors) {
        for (int i = 0; i < genitors.getIndividuals().size(); i += 2) {
            if (random.nextFloat() < crossOverRate) {
                crossOver(genitors.getIndividuals().get(i), genitors.getIndividuals().get(i + 1));
            }

            if (random.nextFloat() < mutationRate) {
                mutation(genitors.getIndividuals().get(i));
            }

            if (random.nextFloat() < mutationRate) {
                mutation(genitors.getIndividuals().get(i + 1));
            }
        }
    }

    private void replacement(Population parents, Population children) {
        // sort parents and children
        parents.getIndividuals().sort(comp);
        children.getIndividuals().sort(comp);

        // take the mu best solutions
        ArrayList<Solution> newIndiv = new ArrayList<>(mu);

        int iparents = 0;
        int ichildren = 0;

        for (int i = 0; i < mu; i++) {
            if (ichildren < children.getIndividuals().size() && parents.getIndividuals().get(iparents).getFitness() < children.getIndividuals().get(ichildren).getFitness()) {
                newIndiv.add(i, children.getIndividuals().get(ichildren));
                ichildren += 1;
            } else {
                newIndiv.add(i, parents.getIndividuals().get(iparents));
                iparents += 1;
            }
        }

        parents.setIndividuals(newIndiv);
    }

    public void run(Population parents, int mu, int lambda, int tournamentSize, double crossOverRate, double mutationRate, int maxGeneration) {
        nbGeneration = 0;
        this.mu = mu;
        this.lambda = lambda;
        this.tournamentSize = tournamentSize;
        this.crossOverRate = crossOverRate;
        this.mutationRate = mutationRate;
        this.maxGeneration = maxGeneration;

        Population children = new Population(this.lambda);

        initialization(parents);
        evalPop(parents);

        // only for testing : print
        System.out.println("Generation n°" + nbGeneration + " -> BestSolution = " + parents.bestSolution());

        while (this.nbGeneration < this.maxGeneration) {
            selection(parents, children);
            randomVariation(children);
            evalPop(children);
            replacement(parents, children);
            this.nbGeneration += 1;

            // only for testing : print
        }

        System.out.println("Generation n°" + nbGeneration + " -> BestSolution = " + parents.bestSolution());
        bestSolActions = parents.bestSolution();
    }

    public ArrayList<Action> getActions() {
        return bestSolActions.getActions();
    }

}
