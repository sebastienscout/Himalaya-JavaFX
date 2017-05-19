package ia;

import java.util.ArrayList;

public class Population {

    private ArrayList<Solution> individuals;

    private int size;

    public Population(int mu) {
        this.size = 0;
        resize(mu);
    }

    public Population() {
        this.size = 0;
    }

    public void resize(int mu) {
        if (this.size != mu) {
            this.size = mu;
            individuals = new ArrayList<>(this.size);
            for (int i = 0; i < this.size; i++) {
                individuals.add(new Solution());
            }
        }
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Solution> getIndividuals() {
        return individuals;
    }

    public Solution getRandomIndividual() {
        int randIndividual = (int)(Math.random() * size);
        return individuals.get(randIndividual);
    }
    
    public void setIndividual(int i, Solution individual){
        individuals.set(i, individual);
    }

    public void setIndividuals(ArrayList<Solution> newIndiv) {
        individuals = newIndiv;
    }

    public Solution bestSolution() {
        if (size == 0) {
            return null;
        } else {
            Solution best = individuals.get(0);
            for (int i = 1; i < size; i++) {
                if (individuals.get(i).getFitness() > best.getFitness()) {
                    best = individuals.get(i);
                }
            }

            return best;
        }
    }
    
    public double averageFitness() {
	if (size == 0) 
	    return 0;
	else {
	    double sum = 0.0;
	    for(int i = 0; i < size; i++) {
		sum += individuals.get(i).getFitness();
	    } 
	    
	    return sum / (double) size;
	}
    }

    @Override
    public String toString() {
	return individuals.toString();
    }

}
