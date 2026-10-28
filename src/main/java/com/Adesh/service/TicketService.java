package com.Adesh.service;
import com.Adesh.dao.TicketDao;
import com.Adesh.entity.Ticket;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class TicketService {
    private final TicketDao ticketDao = new TicketDao();

        // Book ticket
        public Ticket bookTicket(
                EntityManager em,
                int passengerId,
                int trainId,
                LocalDate journeyDate,
                int seatNumber) {

            return ticketDao.bookTicket(
                    em,
                    passengerId,
                    trainId,
                    journeyDate,
                    seatNumber
            );
        }

        // Search ticket by PNR
        public Ticket getTicketByPNR(
                EntityManager em, String pnr) {

            if (pnr == null || pnr.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "PNR number is required!"
                );
            }

            return ticketDao.getTicketByPNR(em, pnr.trim());
        }

        // Display all tickets
        public List<Ticket> getAllTickets(EntityManager em) {
            return ticketDao.getAllTickets(em);
        }

        // Display tickets for a passenger
        public List<Ticket> getTicketsByPassengerId(
                EntityManager em, int passengerId) {

            if (passengerId <= 0) {
                throw new IllegalArgumentException(
                        "Invalid passenger ID!"
                );
            }

            return ticketDao.getTicketsByPassengerId(
                    em, passengerId
            );
        }

        // Cancel ticket
        public boolean cancelTicket(EntityManager em, String pnr) {
            if (pnr == null || pnr.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "PNR number is required!"
                );
            }

            return ticketDao.cancelTicket(em, pnr.trim());
        }

        // Check seat availability
        public boolean isSeatAvailable(
                EntityManager em,
                int trainId,
                LocalDate journeyDate,
                int seatNumber) {

            if (journeyDate == null ||
                    journeyDate.isBefore(LocalDate.now())) {
                return false;
            }

            return ticketDao.isSeatAvailable(
                    em, trainId, journeyDate, seatNumber
            );
        }

}
