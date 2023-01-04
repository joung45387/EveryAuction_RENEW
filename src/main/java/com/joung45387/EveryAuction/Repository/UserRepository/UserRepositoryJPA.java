package com.joung45387.EveryAuction.Repository.UserRepository;

import com.joung45387.EveryAuction.Domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryJPA implements UserRepository{
    private final EntityManager em;
    private final UserRepositoryJpaRepository urjr;
    @Override
    public User findByUserId() {

        return null;
    }

    @Override
    public User findByUserName(String userName) {
        User user = urjr.findByUsername(userName);
        return user;
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public boolean isAlreadyUserName(String userNmae) {
        return findByUserName(userNmae) != null;
    }
}
