import java.util.*;
public class Metrics {
    private int totalDistanceTraveled;
    private double shelfUsage;
    //Changed variable name from m1 to make it more clear that this is tracking the number of misplaced books (might change to be based on % instead of #)
    private int numOfMisplacedBooks;

    private int sortingTime;
    private int shelvingTime;


    public void updateDistance(int currentLocation, int destination){
        setTotalDistanceTraveled((getTotalDistanceTraveled() + (((Math.abs(destination - currentLocation))*5))));
    }

    public void setTotalDistanceTraveled(int totalDistanceTraveled) {
        this.totalDistanceTraveled = totalDistanceTraveled;
    }

    public void setShelfUsage(double shelfUsage) {
        this.shelfUsage = shelfUsage;
    }

    public int getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public double getShelfUsage() {
        return shelfUsage;
    }
    public void printResults(Library L){
        System.out.println("Total Distance Traveled: " + getTotalDistanceTraveled() + " Meters\n Shelf Usage: " + getShelfUsage() + "%");
    }
}
