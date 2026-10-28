package com.Adesh;

import com.Adesh.dao.StationDao;
import com.Adesh.entity.Passenger;
import com.Adesh.entity.Station;
import com.Adesh.entity.Ticket;
import com.Adesh.entity.Train;
import com.Adesh.service.PassengerService;
import com.Adesh.service.TicketService;
import com.Adesh.service.TrainService;
import com.Adesh.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final PassengerService passengerService =
            new PassengerService();

    private static final TrainService trainService =
            new TrainService();

    private static final TicketService ticketService =
            new TicketService();

    private static final StationDao stationDao =
            new StationDao();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println(" RAILWAY RESERVATION SYSTEM");
            System.out.println("================================");
            System.out.println("1. Add Passenger");
            System.out.println("2. Display All Passengers");
            System.out.println("3. Search Passenger by ID");
            System.out.println("4. Add Train");
            System.out.println("5. Display All Trains");
            System.out.println("6. Search Train");
            System.out.println("7. Add Station");
            System.out.println("8. Book Ticket");
            System.out.println("9. Search Ticket by PNR");
            System.out.println("10. Cancel Ticket");
            System.out.println("11. Display All Tickets");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            try {
                switch (choice) {

                    case 1:
                        addPassenger();
                        break;

                    case 2:
                        displayPassengers();
                        break;

                    case 3:
                        searchPassenger();
                        break;

                    case 4:
                        addTrain();
                        break;

                    case 5:
                        displayTrains();
                        break;

                    case 6:
                        searchTrain();
                        break;

                    case 7:
                        addStation();
                        break;

                    case 8:
                        bookTicket();
                        break;

                    case 9:
                        searchTicket();
                        break;

                    case 10:
                        cancelTicket();
                        break;

                    case 11:
                        displayTickets();
                        break;

                    case 12:
                        running = false;
                        System.out.println(
                                "Thank you for using Railway Reservation System!"
                        );
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }

        sc.close();
    }

    // Read integer safely
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    // 1. Add Passenger
    private static void addPassenger() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter passenger name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = readInt();

            System.out.print("Enter gender: ");
            String gender = sc.nextLine();

            System.out.print("Enter mobile number: ");
            String mobile = sc.nextLine();

            Passenger passenger =
                    new Passenger(name, age, gender, mobile);

            em.getTransaction().begin();

            passengerService.addPassenger(em, passenger);

            em.getTransaction().commit();

            System.out.println("Passenger added successfully!");
            System.out.println("Passenger ID: " +
                    passenger.getPassengerId());

        } catch (Exception e) {
            rollback(em);
            throw e;

        } finally {
            em.close();
        }
    }

    // 2. Display Passengers
    private static void displayPassengers() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Passenger> passengers =
                    passengerService.getAllPassengers(em);

            if (passengers.isEmpty()) {
                System.out.println("No passengers found!");
            } else {
                for (Passenger passenger : passengers) {
                    System.out.println(passenger);
                }
            }

        } finally {
            em.close();
        }
    }

    // 3. Search Passenger
    private static void searchPassenger() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter passenger ID: ");
            int id = readInt();

            Passenger passenger =
                    passengerService.getPassengerById(em, id);

            if (passenger == null) {
                System.out.println("Passenger not found!");
            } else {
                System.out.println(passenger);
            }

        } finally {
            em.close();
        }
    }

    // 4. Add Train
    private static void addTrain() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter train name: ");
            String name = sc.nextLine();

            System.out.print("Enter source: ");
            String source = sc.nextLine();

            System.out.print("Enter destination: ");
            String destination = sc.nextLine();

            System.out.print("Enter total seats: ");
            int seats = readInt();

            Train train =
                    new Train(name, source, destination, seats);

            em.getTransaction().begin();

            trainService.addTrain(em, train);

            em.getTransaction().commit();

            System.out.println("Train added successfully!");
            System.out.println("Train ID: " + train.getTrainId());

        } catch (Exception e) {
            rollback(em);
            throw e;

        } finally {
            em.close();
        }
    }

    // 5. Display Trains
    private static void displayTrains() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Train> trains =
                    trainService.getAllTrains(em);

            if (trains.isEmpty()) {
                System.out.println("No trains found!");
            } else {
                for (Train train : trains) {
                    System.out.println(train);
                }
            }

        } finally {
            em.close();
        }
    }

    // 6. Search Train
    private static void searchTrain() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter source: ");
            String source = sc.nextLine();

            System.out.print("Enter destination: ");
            String destination = sc.nextLine();

            List<Train> trains =
                    trainService.searchTrains(
                            em, source, destination
                    );

            if (trains.isEmpty()) {
                System.out.println("No matching trains found!");
            } else {
                for (Train train : trains) {
                    System.out.println(train);
                }
            }

        } finally {
            em.close();
        }
    }

    // 7. Add Station
    private static void addStation() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter station name: ");
            String name = sc.nextLine();

            System.out.print("Enter city: ");
            String city = sc.nextLine();

            Station station = new Station(name, city);

            em.getTransaction().begin();

            stationDao.addStation(em, station);

            em.getTransaction().commit();

            System.out.println("Station added successfully!");
            System.out.println("Station ID: " +
                    station.getStationId());

        } catch (Exception e) {
            rollback(em);
            throw e;

        } finally {
            em.close();
        }
    }

    // 8. Book Ticket
    private static void bookTicket() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter passenger ID: ");
            int passengerId = readInt();

            System.out.print("Enter train ID: ");
            int trainId = readInt();

            System.out.print("Enter journey date (YYYY-MM-DD): ");
            LocalDate journeyDate =
                    LocalDate.parse(sc.nextLine().trim());

            System.out.print("Enter seat number: ");
            int seatNumber = readInt();

            em.getTransaction().begin();

            Ticket ticket = ticketService.bookTicket(
                    em,
                    passengerId,
                    trainId,
                    journeyDate,
                    seatNumber
            );

            em.getTransaction().commit();

            System.out.println("Ticket booked successfully!");
            System.out.println("PNR: " + ticket.getPnrNumber());
            System.out.println("Seat: " + ticket.getSeatNumber());
            System.out.println("Journey date: " +
                    ticket.getJourneyDate());

        } catch (Exception e) {
            rollback(em);
            throw e;

        } finally {
            em.close();
        }
    }

    // 9. Search Ticket
    private static void searchTicket() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter PNR number: ");
            String pnr = sc.nextLine();

            Ticket ticket =
                    ticketService.getTicketByPNR(em, pnr);

            if (ticket == null) {
                System.out.println("Ticket not found!");
            } else {
                System.out.println(ticket);

                System.out.println("Passenger: " +
                        ticket.getPassenger().getName());

                System.out.println("Train: " +
                        ticket.getTrain().getTrainName());
            }

        } finally {
            em.close();
        }
    }

    // 10. Cancel Ticket
    private static void cancelTicket() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            System.out.print("Enter PNR number: ");
            String pnr = sc.nextLine();

            em.getTransaction().begin();

            boolean cancelled =
                    ticketService.cancelTicket(em, pnr);

            if (cancelled) {
                em.getTransaction().commit();
                System.out.println("Ticket cancelled successfully!");
            } else {
                em.getTransaction().rollback();
                System.out.println(
                        "Ticket not found or already cancelled!"
                );
            }

        } catch (Exception e) {
            rollback(em);
            throw e;

        } finally {
            em.close();
        }
    }

    // 11. Display Tickets
    private static void displayTickets() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Ticket> tickets =
                    ticketService.getAllTickets(em);

            if (tickets.isEmpty()) {
                System.out.println("No tickets found!");
            } else {
                for (Ticket ticket : tickets) {
                    System.out.println(ticket);
                }
            }

        } finally {
            em.close();
        }
    }

    // Roll back failed transactions
    private static void rollback(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}