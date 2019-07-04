package com.tensheet.hackathon.summer2019;

import java.util.Collection;
import java.util.HashSet;

public class SeatingPlanUtil {

    private SeatingPlanUtil() {
        // static class
    }

    public static Collection<Guest> getGuestsAtTable(Seat seat) {
        Collection<Guest> guestsAtTable = new HashSet<>();
        Collection<Seat> seatsVisited = new HashSet<>();
        seatsVisited.add(seat);
        Seat nextSeat = seat.getLeft();
        while (nextSeat != null && !seatsVisited.contains(nextSeat)) {
            seatsVisited.add(nextSeat);
            guestsAtTable.add(nextSeat.getGuest());
            nextSeat = nextSeat.getLeft();
        }
        nextSeat = seat.getRight();
        while (nextSeat != null && !seatsVisited.contains(nextSeat)) {
            seatsVisited.add(nextSeat);
            guestsAtTable.add(nextSeat.getGuest());
            nextSeat = nextSeat.getRight();
        }
        return guestsAtTable;
    }

    public static Collection<Guest> getNeighbouringGuests(Seat seat) {
        Collection<Guest> neighbouringGuests = new HashSet<>();
        Seat leftSeat = seat.getLeft();
        if (leftSeat != null) {
            Guest leftGuest = leftSeat.getGuest();
            if (leftGuest != null) neighbouringGuests.add(leftGuest);
        }
        Seat rightSeat = seat.getRight();
        if (rightSeat != null) {
            Guest rightGuest = rightSeat.getGuest();
            if (rightGuest != null) neighbouringGuests.add(rightGuest);
        }
        return neighbouringGuests;
    }

}