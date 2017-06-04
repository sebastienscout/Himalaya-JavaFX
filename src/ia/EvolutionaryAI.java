package ia;

import core.Action;
import core.Board;
import core.Player;
import core.Village;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvolutionaryAI extends Player {

    private int mu;
    private int tournamentSize;
    private int lambda;
    private int maxGeneration;
    private double crossOverRate;
    private double mutationRate;
    private Random random;
    private SolutionComparator comp = new SolutionComparator();
    private Solution bestSolActions = null;
    private Board board;

    private FileOutputStream fos;
    private FileOutputStream fosSimulation;
    private Writer writer;
    private Writer writerSimulation;

    public EvolutionaryAI(String color) {
        super(color);
        random = new Random();

        // population size of the parents
        mu = 20;
        // population size of childrens
        lambda = 1000;
        // tournament size for selection
        tournamentSize = 2;
        // rates of crossOver and mutation
        crossOverRate = 0.8;
        // rates of mutation
        mutationRate = 1.0;
        // maximum number of generation
        maxGeneration = 25;

        try {
            fos = new FileOutputStream("fitness_" + color + ".csv");
            writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            writer.write("turn,generation,best,avg\n");
            
            fosSimulation = new FileOutputStream("simulation_" + color + ".txt");
            writerSimulation = new BufferedWriter(new OutputStreamWriter(fosSimulation, "utf-8"));
        } catch (IOException ex) {
            Logger.getLogger(EvolutionaryAI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void evalPop(Population population) {
        for (Solution individual : population.getIndividuals()) {
            individual.computeFitness(board);
        }
    }

    private void initialization(Population parents) {
        parents.resize(mu);
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
        return new Solution(winner);
    }

    private void selection(Population parents, Population genitors) {
        for (int i = 0; i < genitors.getSize(); i++) {
            genitors.setIndividual(i, tournament(parents));
        }
    }

    private void crossOver(Solution sol1, Solution sol2) {
        for (int i = 0; i < sol1.size(); i++) {
            if (random.nextFloat() < 0.5) {
                Action tmp = new Action(sol1.get(i).getType(), sol1.get(i).getId());
                sol1.set(i, new Action(sol2.get(i).getType(), sol2.get(i).getId()));
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
                newIndiv.add(new Solution(children.getIndividuals().get(ichildren)));
                ichildren += 1;
            } else {
                newIndiv.add(new Solution(parents.getIndividuals().get(iparents)));
                iparents += 1;
            }
        }

        parents.setIndividuals(newIndiv);
    }

    public void run() {

        try {

            Population parents = new Population();
            Population children = new Population(lambda);

            initialization(parents);
            evalPop(parents);

            int nbGeneration = 1;

            writer.write(board.getNbTurn() + "," + nbGeneration + "," + parents.bestSolution().getFitness() + "," + parents.averageFitness() + '\n');
            while (nbGeneration < maxGeneration) {
                selection(parents, children);
                randomVariation(children);
                evalPop(children);
                replacement(parents, children);
                nbGeneration++;
                writer.write(board.getNbTurn() + "," + nbGeneration + "," + parents.bestSolution().getFitness() + "," + parents.averageFitness() + '\n');
            }
            
            writerSimulation.write("Tour " + board.getNbTurn() + " : ");
            writerSimulation.write(parents.bestSolution().getActions().subList(0, 5).toString() + '\n');
            writerSimulation.write("Prevision tour " + (board.getNbTurn() + 1) + " : ");
            writerSimulation.write(parents.bestSolution().getActions().subList(6, parents.bestSolution().getActions().size()).toString() + '\n');

            System.out.println(color + " -> " + parents.bestSolution() + " {AVG = " + parents.averageFitness() + "}");

            bestSolActions = parents.bestSolution();
            writer.flush();
            writerSimulation.flush();

        } catch (Exception ex) {
            Logger.getLogger(EvolutionaryAI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeFile() {
        try {
            writer.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(EvolutionaryAI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int calculateInitPosition() {

        double bestFitness = 0;
        int bestVillage = 1;

        for (Village v : board.getVillages()) {
            if (!board.getChoiceBegining().contains(v.getId())) {

                Population parents = new Population();
                Population children = new Population(lambda);

                Player cloneTestPosition = new Player(this);
                cloneTestPosition.setPosition(v);

                parents.resize(mu);

                for (Solution individual : parents.getIndividuals()) {
                    individual.init(cloneTestPosition);
                }
                evalPop(parents);
                int nbGeneration = 1;

                while (nbGeneration < 10) {
                    selection(parents, children);
                    randomVariation(children);
                    evalPop(children);
                    replacement(parents, children);
                    nbGeneration++;
                }

                if (parents.bestSolution().getFitness() > bestFitness) {
                    bestFitness = parents.bestSolution().getFitness();
                    bestVillage = v.getId();
                    System.out.println(color + " Best village = " + bestVillage + " [" + bestFitness + "]");
                }
            }
        }
        return bestVillage;
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Action> getActions() {
        return bestSolActions.getActions();
    }

}
