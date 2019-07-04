package com.tensheet.hackathon.summer2019;

import org.apache.commons.lang.builder.CompareToBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeatingPlanSolutionPresenter {

    private SeatingPlanSolution seatingPlanSolution;

    public SeatingPlanSolutionPresenter(SeatingPlanSolution seatingPlanSolution) {
        this.seatingPlanSolution = seatingPlanSolution;
    }

    public String displaySeatAssignments() {
        StringBuilder sb = new StringBuilder();
        List<Seat> seats = new ArrayList<>(seatingPlanSolution.getSeats());
        seats.sort((seat1, seat2) -> new CompareToBuilder()
                .append(seat1.getTableNumber(), seat2.getTableNumber())
                .append(seat1.getSeatNumber(), seat2.getSeatNumber())
                .toComparison());
        for (Seat seat : seats) {
            sb.append("table (");
            sb.append(seat.getTableNumber());
            sb.append("), seat (");
            sb.append(seat.getSeatNumber());
            sb.append("), guest (");
            Guest guest = seat.getGuest();
            if (guest == null) {
                sb.append("[empty seat]");
            } else {
                sb.append(guest.getFirstName());
                sb.append(" ");
                sb.append(guest.getLastName());
                sb.append(", ");
                sb.append(guest.getGender());
            }
            sb.append(")\n");
        }
        return sb.toString();
    }

    public String displaySolutionStats() {
        long femaleGuests = seatingPlanSolution.getGuests()
                .stream()
                .filter(guest -> guest.getGender() == Guest.Gender.FEMALE)
                .count();
        long maleGuests = seatingPlanSolution.getGuests()
                .stream()
                .filter(guest -> guest.getGender() == Guest.Gender.MALE)
                .count();
        int seatedGuests = seatingPlanSolution.getSeats()
                .stream()
                .filter(seat -> seat.getGuest() != null)
                .map(Seat::getGuest)
                .collect(Collectors.toSet())
                .size();
        long emptySeats = seatingPlanSolution.getSeats()
                .stream()
                .filter(seat -> seat.getGuest() == null)
                .count();
        int totalSeats = seatingPlanSolution.getSeats().size();
        int totalGuests = seatingPlanSolution.getGuests().size();
        return "total guests (" + totalGuests +
                "), total seats (" + totalSeats +
                "), empty seats (" + emptySeats +
                "), seated guests (" + seatedGuests +
                "), female guests (" + femaleGuests +
                "), male guests (" + maleGuests + ")";
    }

}
