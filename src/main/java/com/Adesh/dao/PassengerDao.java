package com.Adesh.dao;

import com.Adesh.entity.Passenger;
import jakarta.persistence.EntityManager;
import java.util.List;
public class PassengerDao {

        // 1. Add a new passenger
        public void addPassenger(EntityManager em, Passenger passenger) {
            em.persist(passenger);
            System.out.println("Passenger added successfully!");
        }

        // 2. Find a passenger by ID
        public Passenger getPassengerById(EntityManager em, int passengerId) {
            return em.find(Passenger.class, passengerId);
        }

        // 3. Display all passengers
        public List<Passenger> getAllPassengers(EntityManager em) {
            return em.createQuery(
                    "SELECT p FROM Passenger p", Passenger.class
            ).getResultList();
        }

        // 4. Update passenger mobile number
        public boolean updateMobile(EntityManager em, int passengerId, String mobile) {

            Passenger passenger = em.find(Passenger.class, passengerId);

            if (passenger == null) {
                System.out.println("Passenger not found!");
                return false;
            }

            passenger.setMobile(mobile);

            System.out.println("Passenger mobile number updated successfully!");
            return true;
        }

        // 5. Delete a passenger by ID
        public boolean deletePassenger(EntityManager em, int passengerId) {

            Passenger passenger = em.find(Passenger.class, passengerId);

            if (passenger == null) {
                System.out.println("Passenger not found!");
                return false;
            }

            em.remove(passenger);

            System.out.println("Passenger deleted successfully!");
            return true;
        }
}
