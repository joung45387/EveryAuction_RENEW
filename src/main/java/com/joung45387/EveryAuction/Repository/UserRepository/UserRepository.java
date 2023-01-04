package com.joung45387.EveryAuction.Repository.UserRepository;

import com.joung45387.EveryAuction.Domain.Model.User;

public interface UserRepository {
    public User findByUserId();
    public User findByUserName(String userName);
    public void saveUser(User user);

    public boolean isAlreadyUserName(String userNmae);
}
