package com.Adesh.dao;
import com.Adesh.entity.Station;
import com.Adesh.entity.Train;
import jakarta.persistence.EntityManager;

import java.util.List;
        public class StationDao {

        // 1. Add a new station
        public void addStation(EntityManager em, Station station) {
            em.persist(station);
        }

        // 2. Find station by ID
        public Station getStationById(EntityManager em, int stationId) {
            return em.find(Station.class, stationId);
        }

        // 3. Display all stations
        public List<Station> getAllStations(EntityManager em) {
            return em.createQuery(
                    "SELECT s FROM Station s",
                    Station.class
            ).getResultList();
        }

        // 4. Search stations by city
        public List<Station> getStationsByCity(
                EntityManager em, String city) {

            return em.createQuery(
                            "SELECT s FROM Station s " +
                                    "WHERE LOWER(s.city) = LOWER(:city)",
                            Station.class
                    )
                    .setParameter("city", city)
                    .getResultList();
        }

        // 5. Add a station to a train
        public void addStationToTrain(
                EntityManager em, int trainId, int stationId) {

            Train train = em.find(Train.class, trainId);
            Station station = em.find(Station.class, stationId);

            if (train == null) {
                throw new IllegalArgumentException("Train not found!");
            }

            if (station == null) {
                throw new IllegalArgumentException("Station not found!");
            }

            if (!train.getStations().contains(station)) {
                train.getStations().add(station);
            }

            // Update the inverse side of the relationship in memory
            if (!station.getTrains().contains(train)) {
                station.getTrains().add(train);
            }
        }

        // 6. Update station details
        public void updateStation(EntityManager em, Station station) {
            em.merge(station);
        }

        // 7. Delete station
        public void deleteStation(EntityManager em, int stationId) {
            Station station = em.find(Station.class, stationId);

            if (station == null) {
                throw new IllegalArgumentException("Station not found!");
            }

            if (!station.getTrains().isEmpty()) {
                throw new IllegalStateException(
                        "Remove the station from its trains before deleting it."
                );
            }

            em.remove(station);
        }

}
