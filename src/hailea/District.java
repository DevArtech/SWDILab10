/*
 * Course: CS1011 - Saikath Bhattacharya
 * Fall 2022
 * Lab 8 - Parking Lots
 * Name: Adam Haile
 * Created: 10/19/22
 */

package hailea;

import java.util.ArrayList;

/**
 * Manages parking lots within a district.
 * @author Adam Haile
 */
public class District {

    ArrayList<ParkingLot> lots = new ArrayList<>();
    int timeAllClosed = 0;
    int timeReopened = 0;

    /**
     * Adds a new ParkingLot to the District
     * @param name - String, the name of the Parking Lot
     * @param capacity - Integer, the capacity of the Parking Lot
     * @return The index of the Parking Lot in the District array
     */

    public int addLot(String name, int capacity) {
        ParkingLot lot = new ParkingLot(name, capacity);
        lots.add(lot);
        return lots.size() - 1;
    }

    /**
     * Gets the parking lot of the specified index within the district array
     * @param index - The index of the searched for parking lot
     * @return The Parking Lot at the specified index with in the Distict array
     */

    public ParkingLot getLot(int index) {
        for(ParkingLot lot : lots) {
            if(lots.get(index) == lot) {
                return lot;
            }
        }
        return null;
    }

    /**
     * Display status information for all three lots.
     * @see ParkingLot#toString() for the format for each.
     */
    public String toString() {
        StringBuilder collective = new StringBuilder("District status:\n");
        for (ParkingLot lot : lots) {
            collective.append("  ").append(lot.toString()).append("\n");
        }
        return collective.toString();
    }

    /**
     * Returns the number of remaining parking spots in the district
     * @return the number of remaining parking spots in the district
     */
    public int getNumberOfSpotsRemaining() {
        int collective = 0;
        for (ParkingLot lot : lots) {
            collective += lot.getNumberOfSpotsRemaining();
        }
        return collective;
    }

    /**
     * Returns the amount of time all three lots have been
     * simultaneously closed.
     * @return number of minutes all three lots have been closed
     */
    public int getMinutesClosed() {
        return timeReopened - timeAllClosed;
    }

    /**
     * Checks the status of all three lots in the district and
     * returns true if they are all closed and false otherwise.
     * @return whether all three lots in the district are closed
     */
    public boolean isClosed() {
        boolean closeCheck = false;
        int lotTotalClosed = 0;
        for (ParkingLot lot : lots) {
            if (lot.isClosed()) {
                lotTotalClosed++;
            }
        }

        if(lotTotalClosed == lots.size()) {
            closeCheck = true;
        }

        return closeCheck;
    }

    /**
     * Record a vehicle entering a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleEntry for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleEntry on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be within the District's array)
     * @param timestamp Entry timestamp in minutes since all lots were opened.
     */
    public void markVehicleEntry(int lotNumber, int timestamp) {
        lots.get(lotNumber).markVehicleEntry(timestamp);
        if(isClosed()) {
            timeAllClosed = timestamp;
        }
    }

    /**
     * Record a vehicle exiting a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleExit for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleExit on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be within the Distict's array)
     * @param timestamp Exit timestamp in minutes since all lots were opened.
     */
    public void markVehicleExit(int lotNumber, int timestamp) {
        boolean closed = isClosed();
        lots.get(lotNumber).markVehicleExit(timestamp);
        if (closed) {
            if (!isClosed()) {
                timeReopened = timestamp;
            }
        }
    }
}
