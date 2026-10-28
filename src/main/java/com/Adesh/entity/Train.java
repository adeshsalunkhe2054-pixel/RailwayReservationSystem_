package com.Adesh.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="train")

public class Train{
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "train_id")
        private int trainId;

        @Column(name = "train_name", nullable = false, length = 100)
        private String trainName;

        @Column(name = "source_station", nullable = false, length = 100)
        private String source;

        @Column(name = "destination_station", nullable = false, length = 100)
        private String destination;

        @Column(name = "total_seats", nullable = false)
        private int totalSeats;

        @OneToMany(mappedBy = "train")
        private List<Ticket> tickets = new ArrayList<>();

        @ManyToMany
        @JoinTable(
                name = "train_station",
                joinColumns = @JoinColumn(name = "train_id"),
                inverseJoinColumns = @JoinColumn(name = "station_id")
        )
        private List<Station> stations = new ArrayList<>();

        public Train() {
        }

        public Train(String trainName, String source,
                     String destination, int totalSeats) {
            this.trainName = trainName;
            this.source = source;
            this.destination = destination;
            this.totalSeats = totalSeats;
        }

        public int getTrainId() {
            return trainId;
        }

        public void setTrainId(int trainId) {
            this.trainId = trainId;
        }

        public String getTrainName() {
            return trainName;
        }

        public void setTrainName(String trainName) {
            this.trainName = trainName;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getTotalSeats() {
            return totalSeats;
        }

        public void setTotalSeats(int totalSeats) {
            this.totalSeats = totalSeats;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }

        public List<Station> getStations() {
            return stations;
        }

        public void setStations(List<Station> stations) {
            this.stations = stations;
        }

        @Override
        public String toString() {
            return "Train{" +
                    "trainId=" + trainId +
                    ", trainName='" + trainName + '\'' +
                    ", source='" + source + '\'' +
                    ", destination='" + destination + '\'' +
                    ", totalSeats=" + totalSeats +
                    '}';
        }
}
