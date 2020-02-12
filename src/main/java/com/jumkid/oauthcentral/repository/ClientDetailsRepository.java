package com.jumkid.oauthcentral.repository;

import com.jumkid.oauthcentral.model.ClientDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDetailsRepository extends JpaRepository<ClientDetailsEntity, Integer> {
}
