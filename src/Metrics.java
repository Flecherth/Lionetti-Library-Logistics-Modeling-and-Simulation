import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
public class Metrics {
    private int totalDistanceTraveled;
    private double shelfUsage;
    //Changed variable name from m1 to make it more clear that this is tracking the number of misplaced books (might change to be based on % instead of #)
    private int numOfMisplacedBooks;
    private long misplacedBooksRuntime;

    private long sortingTime;
    private long shelvingTime;


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

    public void setSortingTime(long i) {
        sortingTime = i;
    }

    public void setShelvingTime(long i) {
        shelvingTime = i;
    }
    public void setMisplacedBooksRuntime(long i) {
        misplacedBooksRuntime = i;
    }
    public long getMisplacedBooksRuntime() {
        return misplacedBooksRuntime;
    }

    public long getSortingTime() {
        return sortingTime;
    }

    public long getShelvingTime() {
        return shelvingTime;
    }

    public int getNumOfMisplacedBooks() {
        return numOfMisplacedBooks;
    }
    public void setNumOfMisplacedBooks(int m) {
        numOfMisplacedBooks = m;
    }
}

