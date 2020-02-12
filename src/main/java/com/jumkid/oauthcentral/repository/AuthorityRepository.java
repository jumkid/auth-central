package com.jumkid.oauthcentral.repository;

import com.jumkid.oauthcentral.model.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {

    public List<AuthorityEntity> findAllByUsername(@Param("username") String username);

    public int deleteByUsernameAndRole(@Param("username") String username, @Param("role") String role);

    public int deleteByUsername(@Param("username") String username);

}
