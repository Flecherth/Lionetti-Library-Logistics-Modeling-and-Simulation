import java.util.*;

public class ACO implements ShelvingAlgorithm {
    private int numAnts = 50;
    private int numIterations = 100;
    private double alpha = 1;
    private double beta = 2;
    private double evaporation = 0.5;
    private double[][] pheromone;

    public void initializePheromone(int numBooks, int numShelves) {
        pheromone = new double[numBooks][numShelves];
        for (int i = 0; i < numBooks; i++) Arrays.fill(pheromone[i], 1.0);
    }

    public List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> shelves, Library L) {
        long shelvingStartTime = System.currentTimeMillis();
        for (Cart cart : carts) {
            int numBooks = cart.books.length;
            int numShelves = shelves.size();
            initializePheromone(numBooks, numShelves);
            int[] bestSolution = new int[numBooks];
            int bestScore = Integer.MAX_VALUE;
            for (int iter = 0; iter < numIterations; iter++) {
                List<int[]> allSolutions = new ArrayList<>();
                List<Integer> allScores = new ArrayList<>();
                for (int ant = 0; ant < numAnts; ant++) {
                    int[] solution = new int[numBooks];
                    int totalDistance = 0;
                    int currentShelf = 0;
                    for (int b = 0; b < numBooks; b++) {
                        if (cart.books[b] == null) continue;
                        //Probabilistic selection based on pheromone & heuristic
                        double[] probabilities = new double[numShelves];
                        double sum = 0;
                        for (int s = 0; s < numShelves; s++) {
                            int distance = Math.abs(currentShelf - s);
                            int misplacedPenalty = (Book.setDestination(cart.books[b], numShelves) != s) ? 5 : 0;
                            probabilities[s] = Math.pow(pheromone[b][s], alpha) *
                                    Math.pow(1.0 / (1 + distance + misplacedPenalty), beta);
                            sum += probabilities[s];
                        }
                        for (int s = 0; s < numShelves; s++) probabilities[s] /= sum;
                        double r = Math.random();
                        double cumulative = 0;
                        int chosenShelf = 0;
                        for (int s = 0; s < numShelves; s++) {
                            cumulative += probabilities[s];
                            if (r <= cumulative) {
                                chosenShelf = s;
                                break;
                            }
                        }
                        solution[b] = chosenShelf;
                        totalDistance += Math.abs(currentShelf - chosenShelf) * 5;
                        currentShelf = chosenShelf;
                    }
                    allSolutions.add(solution);
                    allScores.add(totalDistance);
                }
                //Update pheromones
                for (int b = 0; b < numBooks; b++)
                    for (int s = 0; s < numShelves; s++)
                        pheromone[b][s] *= (1 - evaporation);
                for (int a = 0; a < numAnts; a++) {
                    int[] solution = allSolutions.get(a);
                    int score = allScores.get(a);
                    for (int b = 0; b < numBooks; b++)
                        pheromone[b][solution[b]] += 1.0 / (1 + score);
                    //Keep track of best solution
                    if (score < bestScore) {
                        bestScore = score;
                        bestSolution = solution.clone();
                    }
                }
            }
            //Apply the best solution: physically shelving the books
            int currentShelfIndex = 0;
            for (int b = 0; b < cart.books.length; b++) {
                if (cart.books[b] == null) continue;
                int targetShelfIndex = bestSolution[b];
                L.LibraryMetrics.updateDistance(currentShelfIndex, targetShelfIndex);
                currentShelfIndex = targetShelfIndex;
                //Shifts to the next shelf when shelf is full
                if(!shelves.get(targetShelfIndex).isNotFull()){
                    L.shiftShelves(shelves.get(targetShelfIndex), cart.books[b]);
                }
                // Insert book into the shelf
                shelves.get(targetShelfIndex).insertBook(cart.books[b]);
            }
        }
        L.LibraryMetrics.setShelvingTime(System.currentTimeMillis() - shelvingStartTime);
        return shelves;
    }
}
