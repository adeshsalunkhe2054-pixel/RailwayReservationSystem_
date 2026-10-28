package com.Adesh.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "passenger")

public class Passenger {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "passenger_id")
        private int passengerId;

        @Column(name = "passenger_name", nullable = false, length = 50)
        private String name;

        @Column(nullable = false)
        private int age;

        @Column(length = 10)
        private String gender;

        @Column(name = "mobile_number", length = 15)
        private String mobile;

        @OneToMany(mappedBy = "passenger")
        private List<Ticket> tickets = new ArrayList<>();

        public Passenger() {
        }

        public Passenger(String name, int age, String gender, String mobile) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.mobile = mobile;
        }

        public int getPassengerId() {
            return passengerId;
        }

        public void setPassengerId(int passengerId) {
            this.passengerId = passengerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }

        @Override
        public String toString() {
            return "Passenger{" +
                    "passengerId=" + passengerId +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    ", mobile='" + mobile + '\'' +
                    '}';
        }
}
