package com.example.sampleproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/* A JpaRepository has pre-defined methods for executing SQL queries on the database
   You can add your own, like the one below. It comes with its own as well, like findAll().
   This is an INTERFACE, not a class.
   public <return type> findCarBy<insert attribute name here>(<attribute type> attribute) is the general format for a method.
*/
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1") // Can define your own SQL queries
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    public User findById(int id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User c set c.firstName = :firstname, c.lastName = :lastname, c.email = :email, c.phone = :phone WHERE c.id = :Id")
    public void updateUserInfo(@Param("Id") Long id,
                               @Param("firstname") String firstname,
                               @Param("lastname") String lastname,
                               @Param("phone") String phone,
                               @Param("email") String email
                               );

}
