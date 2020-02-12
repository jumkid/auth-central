package com.jumkid.oauthcentral.repository;

import com.jumkid.oauthcentral.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT :field FROM users",  nativeQuery = true)
    List<String> getSingleFieldOfAllUsers(String field);

}
