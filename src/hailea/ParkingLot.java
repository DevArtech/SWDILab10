/*
 * Course: CS1011 - Saikath Bhattacharya
 * Fall 2022
 * Lab 8 - Parking Lots
 * Name: Adam Haile
 * Created: 10/19/22
 */

package hailea;

/**
 * The Parking Lot class which manages the logic of the Parking Lot
 */

public class ParkingLot {

    /**
     * The fixed threshold to close the parking lot
     */
    public static final int CLOSED_THRESHOLD = 80;

    /**
     * The name of the parking lot
     */
    public String name = "test";
    double capacity;
    double amountOfCars = 0;
    int recentTimestamp = 0;
    int timeClosed = 0;
    int timeOpened = 0;
    int compoundedClosedTime;

    final int percentMultiplier = 100;

    /**
     * Initialized the ParkingLot class
     * @param name String (Name of parking lot)
     * @param capacity Integer (Capacity of parking lot)
     */
    public ParkingLot(String name, int capacity) {
        if(name != null) {
            this.name = name;
        }
        this.capacity = capacity;
    }

    /**
     * Gets the name of the parking lot
     * @return The parking lot name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets if the parking lot is closed/at capacity
     * @return True/False If the parking lot is closed
     */
    public boolean isClosed() {
        double percent = (amountOfCars / capacity) * percentMultiplier;
        return percent >= CLOSED_THRESHOLD;
    }

    /**
     * Is called when a vehicle enters the parking lot
     * @param i The timestamp of the vehicle when it entered
     */
    public void markVehicleEntry(int i) {
        if(i >= recentTimestamp) {
            if (!isClosed()) {
                amountOfCars++;
                if (isClosed()) {
                    this.timeClosed = i;
                }
            } else {
                amountOfCars++;
            }
            recentTimestamp = i;
        }
    }

    /**
     * Returns the name of the parking lot, the number of cars in it, and the percentage of occupied space
     */

    public String toString() {
        if(getNumberOfSpotsRemaining() == 0) {
            return "Status for " + name +  " parking lot: " + (int)amountOfCars +
                    " vehicles (Closed)";
        } else {
            return "Status for " + name +  " parking lot: " + (int)amountOfCars +
                    " vehicles (" + ((amountOfCars / capacity) * percentMultiplier) + "%)";
        }
    }

    /**
     * Is called when a vehicle leaves the parking lot
     * @param i The timestamp of the vehicle when it exited
     */
    public void markVehicleExit(int i) {
        if(i >= recentTimestamp) {
            if (amountOfCars >= 1) {
                if (isClosed()) {
                    amountOfCars--;
                    if (!isClosed()) {
                        timeOpened = i;
                        compoundedClosedTime += timeOpened - timeClosed;
                    }
                } else {
                    amountOfCars--;
                }
            }
            recentTimestamp = i;
        }
    }

    /**
     * Gets the number of spots remaining in the parking lot at any one time
     * @return Number of spots remaining in parking lot
     */
    public int getNumberOfSpotsRemaining() {
        if((amountOfCars / capacity) * percentMultiplier > percentMultiplier) {
            return 0;
        } else {
            return (int) (capacity - amountOfCars);
        }
    }

    /**
     * Gets the number of minutes that have elapsed since the parking lot has closed
     * @return Number of minutes since parking lot closure
     */
    public int getMinutesClosed() {
        return compoundedClosedTime;
    }
}
