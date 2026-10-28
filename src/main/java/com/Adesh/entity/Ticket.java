package com.Adesh.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ticket")
public class Ticket {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ticket_id")
        private int ticketId;

        @Column(name = "pnr_number", unique = true, length = 20)
        private String pnrNumber;

        @Column(name = "journey_date", nullable = false)
        private LocalDate journeyDate;

        @Column(name = "seat_number")
        private int seatNumber;

        @Column(name = "booking_status", nullable = false, length = 20)
        private String status;

        // Many tickets can belong to one passenger
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "passenger_id", nullable = false)
        private Passenger passenger;

        // Many tickets can belong to one train
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "train_id", nullable = false)
        private Train train;

        public Ticket() {
        }

        public Ticket(String pnrNumber, LocalDate journeyDate,
                      int seatNumber, String status) {
            this.pnrNumber = pnrNumber;
            this.journeyDate = journeyDate;
            this.seatNumber = seatNumber;
            this.status = status;
        }

        public int getTicketId() {
            return ticketId;
        }

        public void setTicketId(int ticketId) {
            this.ticketId = ticketId;
        }

        public String getPnrNumber() {
            return pnrNumber;
        }

        public void setPnrNumber(String pnrNumber) {
            this.pnrNumber = pnrNumber;
        }

        public LocalDate getJourneyDate() {
            return journeyDate;
        }

        public void setJourneyDate(LocalDate journeyDate) {
            this.journeyDate = journeyDate;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Passenger getPassenger() {
            return passenger;
        }

        public void setPassenger(Passenger passenger) {
            this.passenger = passenger;
        }

        public Train getTrain() {
            return train;
        }

        public void setTrain(Train train) {
            this.train = train;
        }

        @Override
        public String toString() {
            return "Ticket{" +
                    "ticketId=" + ticketId +
                    ", pnrNumber='" + pnrNumber + '\'' +
                    ", journeyDate=" + journeyDate +
                    ", seatNumber=" + seatNumber +
                    ", status='" + status + '\'' +
                    '}';
        }
}
