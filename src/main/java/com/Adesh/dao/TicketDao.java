package com.Adesh.dao;
import com.Adesh.entity.Passenger;
import com.Adesh.entity.Ticket;
import com.Adesh.entity.Train;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
public class TicketDao {

        // 1. Book a ticket
        public Ticket bookTicket(
                EntityManager em,
                int passengerId,
                int trainId,
                LocalDate journeyDate,
                int seatNumber) {

            if (journeyDate == null ||
                    journeyDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException(
                        "Journey date cannot be in the past!"
                );
            }

            Passenger passenger =
                    em.find(Passenger.class, passengerId);

            Train train =
                    em.find(Train.class, trainId);

            if (passenger == null) {
                throw new IllegalArgumentException(
                        "Passenger not found!"
                );
            }

            if (train == null) {
                throw new IllegalArgumentException(
                        "Train not found!"
                );
            }

            if (seatNumber < 1 ||
                    seatNumber > train.getTotalSeats()) {
                throw new IllegalArgumentException(
                        "Invalid seat number!"
                );
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

            if (count > 0) {
                throw new IllegalStateException(
                        "This seat is already booked!"
                );
            }

            // Generate a unique PNR
            String pnr = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 10)
                    .toUpperCase();

            Ticket ticket = new Ticket(
                    pnr,
                    journeyDate,
                    seatNumber,
                    "BOOKED"
            );

            ticket.setPassenger(passenger);
            ticket.setTrain(train);

            em.persist(ticket);

            return ticket;
        }

        // 2. Find ticket by ticket ID
        public Ticket getTicketById(
                EntityManager em, int ticketId) {

            return em.find(Ticket.class, ticketId);
        }

        // 3. Find ticket by PNR
        public Ticket getTicketByPNR(
                EntityManager em, String pnr) {

            List<Ticket> tickets = em.createQuery(
                            "SELECT t FROM Ticket t " +
                                    "WHERE t.pnrNumber = :pnr",
                            Ticket.class
                    )
                    .setParameter("pnr", pnr)
                    .setMaxResults(1)
                    .getResultList();

            return tickets.isEmpty() ? null : tickets.get(0);
        }

        // 4. Display all tickets
        public List<Ticket> getAllTickets(EntityManager em) {

            return em.createQuery(
                    "SELECT t FROM Ticket t",
                    Ticket.class
            ).getResultList();
        }

        // 5. Display tickets belonging to a passenger
        public List<Ticket> getTicketsByPassengerId(
                EntityManager em, int passengerId) {

            return em.createQuery(
                            "SELECT t FROM Ticket t " +
                                    "WHERE t.passenger.passengerId = :passengerId",
                            Ticket.class
                    )
                    .setParameter("passengerId", passengerId)
                    .getResultList();
        }

        // 6. Display tickets for a train on a particular date
        public List<Ticket> getTicketsByTrainAndDate(
                EntityManager em,
                int trainId,
                LocalDate journeyDate) {

            return em.createQuery(
                            "SELECT t FROM Ticket t " +
                                    "WHERE t.train.trainId = :trainId " +
                                    "AND t.journeyDate = :journeyDate " +
                                    "AND t.status = :status",
                            Ticket.class
                    )
                    .setParameter("trainId", trainId)
                    .setParameter("journeyDate", journeyDate)
                    .setParameter("status", "BOOKED")
                    .getResultList();
        }

        // 7. Cancel a ticket by PNR
        public boolean cancelTicket(
                EntityManager em, String pnr) {

            Ticket ticket = getTicketByPNR(em, pnr);

            if (ticket == null) {
                return false;
            }

            if ("CANCELLED".equalsIgnoreCase(ticket.getStatus())) {
                return false;
            }

            ticket.setStatus("CANCELLED");

            return true;
        }

        // 8. Check whether a seat is available
        public boolean isSeatAvailable(
                EntityManager em,
                int trainId,
                LocalDate journeyDate,
                int seatNumber) {

            Train train = em.find(Train.class, trainId);

            if (train == null ||
                    journeyDate == null ||
                    seatNumber < 1 ||
                    seatNumber > train.getTotalSeats()) {
                return false;
            }

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
