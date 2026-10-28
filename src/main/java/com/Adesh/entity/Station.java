package com.Adesh.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
public class Station {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "station_id")
        private int stationId;

        @Column(name = "station_name", nullable = false, length = 100)
        private String stationName;

        @Column(length = 100)
        private String city;

        @ManyToMany(mappedBy = "stations")
        private List<Train> trains = new ArrayList<>();

        public Station() {
        }

        public Station(String stationName, String city) {
            this.stationName = stationName;
            this.city = city;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Train> getTrains() {
            return trains;
        }

        public void setTrains(List<Train> trains) {
            this.trains = trains;
        }

        @Override
        public String toString() {
            return "Station{" +
                    "stationId=" + stationId +
                    ", stationName='" + stationName + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
}
