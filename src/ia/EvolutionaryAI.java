package ia;

import core.Action;
import core.Board;
import core.Player;
import core.Resource;
import core.Village;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvolutionaryAI extends Player {

    private int mu;
    private int tournamentSize;
    private double crossOverRate;
    private double mutationRate;
    private int maxGeneration;
    private Random random;
    private SolutionComparator comp = new SolutionComparator();
    private Solution bestSolActions = null;
    private Board board;

    private FileOutputStream fos;
    private Writer writer;

    public EvolutionaryAI(String color, Village v) {
        super(color, v);
        random = new Random();
        try {
            fos = new FileOutputStream("fitness_" + getColor() + ".txt");
            writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            writer.write("turn,generation,fitness\n");
        } catch (IOException ex) {
            Logger.getLogger(EvolutionaryAI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void evalPop(Population population) {
        for (Solution individual : population.getIndividuals()) {
            individual.calculateFitness(board);
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

    public void run(Population parents, int mu, int lambda, int tournamentSize, double crossOverRate, double mutationRate, int maxGeneration) {

        try {

            this.mu = mu;
            this.tournamentSize = tournamentSize;
            this.crossOverRate = crossOverRate;
            this.mutationRate = mutationRate;
            this.maxGeneration = maxGeneration;
            this.board = board;

            Population children = new Population(lambda);

            initialization(parents);
            evalPop(parents);

            int nbGeneration = 0;

//            System.out.println(getColor() + " Generation n°" + nbGeneration + " -> BestSolution = " + parents.bestSolution());
            writer.write(board.getNbTurn() + "," + nbGeneration + "," + parents.bestSolution().getFitness() + '\n');
            while (nbGeneration < this.maxGeneration) {
                selection(parents, children);
                randomVariation(children);
                evalPop(children);
                replacement(parents, children);
                nbGeneration++;
                writer.write(board.getNbTurn() + "," + nbGeneration + "," + parents.bestSolution().getFitness() + '\n');
            }

            System.out.println(getColor() + " -> " + parents.bestSolution());

            bestSolActions = parents.bestSolution();
            writer.flush();

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
        HashMap<Integer, Integer> values = new HashMap<>();
        for (Village village : board.getVillages()) {
            if (village.getResources().size() > 0 && !(board.getChoiceBegining().contains(village.getId()))) {
                int value = 0;
                for (Resource resource : village.getResources()) {
                    value += resource.getValue();
                }
                values.put(village.getId(), value);
            }
        }

        return Collections.max(values.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Action> getActions() {
        return bestSolActions.getActions();
    }

}
