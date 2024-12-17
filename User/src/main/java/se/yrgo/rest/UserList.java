package se.yrgo.rest;

import java.util.List;
import se.yrgo.domain.UserEntity;

public class UserList {
    private List<UserEntity> users;

    public UserList() {}

    public UserList(List<UserEntity> users) {
        this.users = users;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
