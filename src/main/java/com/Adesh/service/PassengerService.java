package com.Adesh.service;
import com.Adesh.dao.PassengerDao;
import com.Adesh.entity.Passenger;
import jakarta.persistence.EntityManager;

import java.util.List;
public class PassengerService {

        private final PassengerDao passengerDao = new PassengerDao();

        // Add passenger
        public void addPassenger(EntityManager em, Passenger passenger) {
            if (passenger == null) {
                throw new IllegalArgumentException(
                        "Passenger cannot be null!"
                );
            }

            if (passenger.getName() == null ||
                    passenger.getName().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Passenger name is required!"
                );
            }

            passengerDao.addPassenger(em, passenger);
        }

        // Find passenger by ID
        public Passenger getPassengerById(
                EntityManager em, int passengerId) {

            if (passengerId <= 0) {
                throw new IllegalArgumentException(
                        "Invalid passenger ID!"
                );
            }

            return passengerDao.getPassengerById(em, passengerId);
        }

        // Display all passengers
        public List<Passenger> getAllPassengers(EntityManager em) {
            return passengerDao.getAllPassengers(em);
        }

        // Update passenger
        public void updatePassenger(
                EntityManager em, Passenger passenger) {

            if (passenger == null ||
                    passenger.getPassengerId() <= 0) {
                throw new IllegalArgumentException(
                        "Valid passenger details are required!"
                );
            }

            Passenger existing = passengerDao.getPassengerById(
                    em, passenger.getPassengerId()
            );

            if (existing == null) {
                throw new IllegalArgumentException(
                        "Passenger not found!"
                );
            }

            existing.setName(passenger.getName());
            existing.setAge(passenger.getAge());
            existing.setGender(passenger.getGender());
            existing.setMobile(passenger.getMobile());
        }

        // Delete passenger
        public void deletePassenger(
                EntityManager em, int passengerId) {

            Passenger passenger =
                    passengerDao.getPassengerById(em, passengerId);

            if (passenger == null) {
                throw new IllegalArgumentException(
                        "Passenger not found!"
                );
            }

            if (!passenger.getTickets().isEmpty()) {
                throw new IllegalStateException(
                        "Cannot delete a passenger who has ticket records."
                );
            }

            passengerDao.deletePassenger(em, passengerId);
        }

}
