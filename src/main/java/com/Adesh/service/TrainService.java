package com.Adesh.service;
import com.Adesh.dao.TrainDao;
import com.Adesh.entity.Train;
import jakarta.persistence.EntityManager;

import java.util.List;
public class TrainService {

        private final TrainDao trainDao = new TrainDao();

        // Add train
        public void addTrain(EntityManager em, Train train) {
            if (train == null) {
                throw new IllegalArgumentException(
                        "Train cannot be null!"
                );
            }

            if (train.getTrainName() == null ||
                    train.getTrainName().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Train name is required!"
                );
            }

            if (train.getTotalSeats() <= 0) {
                throw new IllegalArgumentException(
                        "Total seats must be greater than zero!"
                );
            }

            trainDao.addTrain(em, train);
        }

        // Find train by ID
        public Train getTrainById(EntityManager em, int trainId) {
            if (trainId <= 0) {
                throw new IllegalArgumentException(
                        "Invalid train ID!"
                );
            }

            return trainDao.getTrainById(em, trainId);
        }

        // Display all trains
        public List<Train> getAllTrains(EntityManager em) {
            return trainDao.getAllTrains(em);
        }

        // Search trains
        public List<Train> searchTrains(
                EntityManager em,
                String source,
                String destination) {

            if (source == null || source.trim().isEmpty() ||
                    destination == null ||
                    destination.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Source and destination are required!"
                );
            }

            return trainDao.searchTrains(
                    em, source.trim(), destination.trim()
            );
        }

        // Update train
        public void updateTrain(EntityManager em, Train train) {
            if (train == null || train.getTrainId() <= 0) {
                throw new IllegalArgumentException(
                        "Valid train details are required!"
                );
            }

            Train existing =
                    trainDao.getTrainById(em, train.getTrainId());

            if (existing == null) {
                throw new IllegalArgumentException(
                        "Train not found!"
                );
            }

            existing.setTrainName(train.getTrainName());
            existing.setSource(train.getSource());
            existing.setDestination(train.getDestination());
            existing.setTotalSeats(train.getTotalSeats());
        }

        // Delete train
        public void deleteTrain(EntityManager em, int trainId) {
            Train train = trainDao.getTrainById(em, trainId);

            if (train == null) {
                throw new IllegalArgumentException(
                        "Train not found!"
                );
            }

            if (!train.getTickets().isEmpty()) {
                throw new IllegalStateException(
                        "Cannot delete a train that has ticket records."
                );
            }

            trainDao.deleteTrain(em, trainId);
        }

}
