package com.joung45387.EveryAuction.Repository.UserRepository;

import com.joung45387.EveryAuction.Domain.DTO.OAuthSignUpDTO;
import com.joung45387.EveryAuction.Domain.Model.User;

public interface UserRepository {

    User findByUserId(Long id);
    User findByName(String name);

    User updateOAuthUser(User user, OAuthSignUpDTO oAuthSignUpDTO);

    public User findByUserName(String userName);
    public void saveUser(User user);
}
