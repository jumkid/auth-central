package com.jumkid.oauthcentral.model;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
@Builder
@AllArgsConstructor
public class AuthorityEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

}
