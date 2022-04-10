package com.example.sampleproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/* A JpaRepository has pre-defined methods for executing SQL queries on the database
   You can add your own, like the one below. It comes with its own as well, like findAll().
   This is an INTERFACE, not a class.
   public <return type> findCarBy<insert attribute name here>(<attribute type> attribute) is the general format for a method.
*/
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.id = ?1") // Can define your own SQL queries
    public Reservation findReservationByID(long reservationID);

    @Query("SELECT r FROM Reservation r WHERE r.userID = ?1")
    public Reservation findReservationByUserID(long userID);
}


