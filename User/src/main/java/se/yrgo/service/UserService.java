package se.yrgo.service;

import se.yrgo.domain.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();

    public UserEntity createUser(UserEntity user);

    public void deleteUser(Long id);
    UserEntity getUserById(Long id);
}
