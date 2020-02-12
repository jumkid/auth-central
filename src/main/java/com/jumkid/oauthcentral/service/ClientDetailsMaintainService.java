package com.jumkid.oauthcentral.service;

import com.jumkid.oauthcentral.controller.dto.ClientDetails;
import com.jumkid.oauthcentral.exception.DataMutationException;
import com.jumkid.oauthcentral.exception.DataNotFoundException;
import com.jumkid.oauthcentral.model.ClientDetailsEntity;
import com.jumkid.oauthcentral.model.mapper.ClientDetailsMapper;
import com.jumkid.oauthcentral.model.mapper.ListClientDetailsMapper;
import com.jumkid.oauthcentral.repository.ClientDetailsRepository;
import com.jumkid.oauthcentral.utils.ClientDetailsField;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ClientDetailsMaintainService {

    private final ClientDetailsMapper clientDetailsMapper = Mappers.getMapper(ClientDetailsMapper.class);

    private final ClientDetailsRepository clientDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientDetailsMaintainService(ClientDetailsRepository clientDetailsRepository, PasswordEncoder passwordEncoder) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ClientDetails> getAll() {
        return Mappers.getMapper(ListClientDetailsMapper.class).entitiesToDTOS(clientDetailsRepository.findAll());
    }

    public ClientDetails getClientDetails(Integer clientDetailsId) {
        return clientDetailsMapper.entityToDTO(clientDetailsRepository.findById(clientDetailsId).orElse(null));
    }

    public ClientDetails saveClientDetails(ClientDetails clientDetails) {
        try {
            normalizeDto(null, clientDetails, null);

            ClientDetailsEntity newClientDetails = clientDetailsMapper.dtoToEntity(clientDetails);

            return clientDetailsMapper.entityToDTO(clientDetailsRepository.save(newClientDetails));
        } catch (Exception e) {
            log.error("Failed to save ClientDetails {}", e.getMessage());
            throw new DataMutationException(ClientDetails.ENTITY_NAME);
        }
    }

    public ClientDetails updateClientDetails(Integer clientDetailsId, ClientDetails clientDetails) {
        try {
            ClientDetailsEntity oldClientDetails = this.fetchClientDetailsEntity(clientDetailsId).orElse(null);
            if (oldClientDetails != null) {
                normalizeDto(clientDetailsId, clientDetails, oldClientDetails);

                ClientDetailsEntity updatedClientDetails =
                        clientDetailsRepository.save(clientDetailsMapper.dtoToEntity(clientDetails));

                return clientDetailsMapper.entityToDTO(updatedClientDetails);
            } else {
                throw new DataNotFoundException(ClientDetails.ENTITY_NAME, clientDetailsId);
            }
        } catch (Exception e) {
            log.error("Failed to update ClientDetails {}", e.getMessage());
            throw new DataMutationException(ClientDetails.ENTITY_NAME);
        }
    }

    public ClientDetails patchClientDetails(Integer clientDetailsId, Map<String, Object> updatesMap) {
        try {
            ClientDetailsEntity oldClientDetails = this.fetchClientDetailsEntity(clientDetailsId).orElse(null);
            if (oldClientDetails != null) {
                ClientDetailsEntity patchedClientDetails = clientDetailsMapper.updatesMapToEntity(updatesMap, oldClientDetails);

                return clientDetailsMapper.entityToDTO(clientDetailsRepository.save(patchedClientDetails));
            } else {
                throw new DataNotFoundException(ClientDetails.ENTITY_NAME, clientDetailsId);
            }
        } catch (Exception e) {
            log.error("Failed to patch ClientDetails {}", e.getMessage());
            throw new DataMutationException(ClientDetails.ENTITY_NAME);
        }
    }

    public void deleteClientDetails(Integer clientDetailsId) {
        try {
            clientDetailsRepository.deleteById(clientDetailsId);
        } catch (Exception e) {
            log.error("Failed to delete ClientDetails with id {} {}", clientDetailsId, e.getMessage());
            throw new DataMutationException(ClientDetails.ENTITY_NAME);
        }
    }

    private void normalizeDto(Integer clientDetailsId, ClientDetails clientDetails, ClientDetailsEntity oldClientDetails) {
        if (clientDetailsId != null) {
            clientDetails.setId(clientDetailsId);
        }

        if (oldClientDetails != null) {
            clientDetails.setClientSecret(oldClientDetails.getClientSecret());
        } else {
            clientDetails.setId(null);
            clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
        }
    }

    private Optional<ClientDetailsEntity> fetchClientDetailsEntity(Integer clientDetailsId) {
        return this.clientDetailsRepository.findById(clientDetailsId);
    }

}
