package com.jumkid.oauthcentral.service;

import com.jumkid.oauthcentral.controller.dto.User;
import com.jumkid.oauthcentral.exception.DataMutationException;
import com.jumkid.oauthcentral.exception.DataNotFoundException;
import com.jumkid.oauthcentral.exception.DuplicateValueException;
import com.jumkid.oauthcentral.model.mapper.UserMapper;
import com.jumkid.oauthcentral.model.UserEntity;
import com.jumkid.oauthcentral.repository.AuthorityRepository;
import com.jumkid.oauthcentral.repository.UserRepository;
import com.jumkid.oauthcentral.utils.UserField;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        return userMapper.entityToDTO(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public List<String> getSingleFieldOfAll(UserField userField) {
        return userRepository.getSingleFieldOfAllUsers(userField.columnName());
    }

    public User saveUser(User user) {
        UserEntity existUser = fetchUserEntity(user.getUsername()).orElse(null);
        try {
            if (existUser == null) {
                normalizeDto(null, user, null);

                UserEntity newUser = userMapper.dtoToEntity(user);

                return userMapper.entityToDTO(userRepository.save(newUser));
            } else {
                throw new DuplicateValueException(String.format("username %s exists, please use different username", user.getUsername()));
            }
        } catch (Exception e) {
            log.error("Failed to save user {}", e.getMessage());
            throw new DataMutationException(User.ENTITY_NAME);
        }
    }

    public User updateUser(String username, User user) {
        try {
            Optional<UserEntity> oldUser = fetchUserEntity(username);

            if (oldUser.isPresent()) {
                normalizeDto(username, user, oldUser.get());

                UserEntity updateUser = userRepository.save(userMapper.dtoToEntity(user));

                return userMapper.entityToDTO(updateUser);
            } else {
                throw new DataNotFoundException(User.ENTITY_NAME, username);
            }
        } catch (Exception e) {
            log.error("Failed to update user {}", e.getMessage());
            throw new DataMutationException(User.ENTITY_NAME);
        }
    }

    public User patchUser(String username, Map<String, Object> updatesMap) {
        try {
            UserEntity oldUser = fetchUserEntity(username).orElse(null);
            if (oldUser != null) {
                UserEntity patchedUser = userMapper.updatesMapToEntity(updatesMap, oldUser);

                return userMapper.entityToDTO(userRepository.save(patchedUser));
            } else {
                throw new DataNotFoundException(User.ENTITY_NAME, username);
            }
        } catch (Exception e) {
            log.error("Failed to save user {}", e.getMessage());
            throw new DataMutationException(User.ENTITY_NAME);
        }
    }

    @Transactional
    public int deleteUser(String username) {
        try {
            Optional<UserEntity> existingUser = fetchUserEntity(username);
            if (existingUser.isPresent()) {
                userRepository.delete(existingUser.get());
                return authorityRepository.deleteByUsername(username);
            } else {
                throw new DataNotFoundException(User.ENTITY_NAME, username);
            }
        } catch (Exception e) {
            log.error("Failed to mutate user {}", e.getMessage());
        }
        return 0;
    }

    private void normalizeDto(String username, User user, UserEntity oldUser) {
        if (username != null && !username.isBlank()) {
            user.setUsername(username);
        }

        if (oldUser != null) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    private Optional<UserEntity> fetchUserEntity(String username) {
        return this.userRepository.findByUsername(username);
    }

}
