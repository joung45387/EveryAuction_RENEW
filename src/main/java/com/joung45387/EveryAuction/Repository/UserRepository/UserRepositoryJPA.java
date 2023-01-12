package com.joung45387.EveryAuction.Repository.UserRepository;

import com.joung45387.EveryAuction.Domain.DTO.OAuthSignUpDTO;
import com.joung45387.EveryAuction.Domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryJPA implements UserRepository{
    private final EntityManager entityManager;
    private final UserRepositoryDataJPA userRepositoryDataJPA;
    @Override
    public User findByUserId(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByName(String name) {
        return userRepositoryDataJPA.findByName(name);
    }


    @Override
    public User updateOAuthUser(User user, OAuthSignUpDTO oAuthSignUpDTO){
        user.updateOAuthUser(oAuthSignUpDTO);
        return user;
    }

    @Override
    public User findByUserName(String userName) {
        User user = userRepositoryDataJPA.findByUsername(userName);
        return user;
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public boolean isAlreadyUserName(String userNmae) {
        return findByUserName(userNmae) != null;
    }
}
