package ia;

import java.util.Comparator;

class SolutionComparator implements Comparator<Solution> {

    @Override
    public int compare(Solution sol1, Solution sol2) {
        if (sol1.getFitness()< sol2.getFitness()) {
            return 1;
        } else {
            if (sol1.getFitness() == sol2.getFitness()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
