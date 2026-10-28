package com.Adesh.service;
import com.Adesh.entity.Ticket;
import com.Adesh.entity.Train;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;

public class SeatBooking {

        public boolean isSeatAvailable(
                EntityManager em,
                int trainId,
                LocalDate journeyDate,
                int seatNumber) {

            // Find the train
            Train train = em.find(Train.class, trainId);

            if (train == null) {
                System.out.println("Train not found!");
                return false;
            }

            // Validate seat number
            if (seatNumber < 1 || seatNumber > train.getTotalSeats()) {
                System.out.println("Invalid seat number!");
                return false;
            }

            // Check whether the seat is already booked
            Long count = em.createQuery(
                            "SELECT COUNT(t) FROM Ticket t " +
                                    "WHERE t.train.trainId = :trainId " +
                                    "AND t.journeyDate = :journeyDate " +
                                    "AND t.seatNumber = :seatNumber " +
                                    "AND t.status = :status",
                            Long.class
                    )
                    .setParameter("trainId", trainId)
                    .setParameter("journeyDate", journeyDate)
                    .setParameter("seatNumber", seatNumber)
                    .setParameter("status", "BOOKED")
                    .getSingleResult();

            return count == 0;
        }
}
