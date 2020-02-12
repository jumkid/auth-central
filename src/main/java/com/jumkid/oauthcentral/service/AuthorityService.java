package com.jumkid.oauthcentral.service;

import com.jumkid.oauthcentral.controller.dto.Authority;
import com.jumkid.oauthcentral.exception.DataMutationException;
import com.jumkid.oauthcentral.exception.DataNotFoundException;
import com.jumkid.oauthcentral.exception.DuplicateValueException;
import com.jumkid.oauthcentral.model.AuthorityEntity;
import com.jumkid.oauthcentral.model.mapper.AuthorityMapper;
import com.jumkid.oauthcentral.model.mapper.ListAuthorityMapper;
import com.jumkid.oauthcentral.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthorityService {

    private final AuthorityMapper authorityMapper = Mappers.getMapper(AuthorityMapper.class);
    private final ListAuthorityMapper listAuthorityMapper = Mappers.getMapper(ListAuthorityMapper.class);

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority getAuthority(Integer authorityId) {
        return authorityMapper.entityToDTO(authorityRepository.findById(authorityId).orElse(null));
    }

    public List<Authority> getAuthorityByUser(String username) {
        return listAuthorityMapper.entitiesToDTOS(authorityRepository.findAllByUsername(username));
    }

    public Authority saveAuthority(Authority authority) {
        AuthorityEntity authorityEntity = fetchAuthority(authority.getId()).orElse(null);
        if (authorityEntity == null) {
            AuthorityEntity newAuthority = authorityRepository.save(authorityMapper.dtoToEntity(authority));

            return authorityMapper.entityToDTO(newAuthority);
        } else {
            throw new DuplicateValueException(String.format("authority id %d exists", authority.getId()));
        }
    }

    public Authority updateAuthority(Integer authorityId, Authority authority) {
        try {
            AuthorityEntity oldAuthority = fetchAuthority(authorityId).orElse(null);

            if (oldAuthority != null) {
                AuthorityEntity updatedAuthority = authorityRepository.save(authorityMapper.dtoToEntity(authority));

                normalizeDto(authorityId, authority);

                return authorityMapper.entityToDTO(updatedAuthority);
            } else {
                throw new DataNotFoundException(Authority.ENTITY_NAME, authorityId);
            }
        } catch (Exception e) {
            log.error("Failed to update authority {}", e.getMessage());
            throw new DataMutationException(Authority.ENTITY_NAME);
        }
    }

    public void deleteAuthority(Integer authorityId) {
        try {
            authorityRepository.deleteById(authorityId);
        } catch (Exception e) {
            log.error("Failed to delete Authority with id {}", authorityId);
            throw new DataMutationException(Authority.ENTITY_NAME);
        }
    }

    private void normalizeDto(Integer authorityId, Authority authority) {
        if (authorityId != null) {
            authority.setId(authorityId);
        }
    }
    private Optional<AuthorityEntity> fetchAuthority(Integer authorityId) {
        return authorityRepository.findById(authorityId);
    }

}
