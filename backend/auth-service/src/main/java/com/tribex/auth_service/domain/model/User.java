package com.tribex.auth_service.domain.model;

import jakarta.persistence.*;
import lombok.*;

/*
    @Entity tells Spring this class is a database table
 */
@Entity

/*
    Table name in PostgreSQL
 */
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /*
        Primary Key
     */
    @Id

    /*
        Auto increment ID
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Username of user
     */
    @Column(nullable = false)
    private String username;

    /*
        Email should be unique
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
        Encrypted password
     */
    @Column(nullable = false)
    private String password;
}