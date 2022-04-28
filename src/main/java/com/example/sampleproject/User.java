package com.example.sampleproject;

import javax.persistence.*;

/* An @Entity class defines a class that will be turned into a table in the database
 *  You use @Table to set up some configuration variables, including the table's name as you see below
 *  You use @Column to define a column in the table, which is an attribute of the class. You also specify the column name,
 *  whether it can be null, unique and the length if it's a string
 */
@Entity
@Table(name = "users")
public class User {

    /* This is the id column, it starts from 1 and gets incremented every time a Car is added to the table
     *  Equivalent of AUTO_INCREMENT in MySQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // The PK of the table


    @Column(nullable = false, unique = true, length = 45)
    private String email; // Email should be present and is unique

    @Column(nullable = false, unique = true, length = 64)
    private String password; // Password should be present and is unique

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName; // First name should be present


    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName; // Last name should be present

    @Column(name = "phone", length = 10)
    private String phone; // Phone number is optional

    // getters and setters are below

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
