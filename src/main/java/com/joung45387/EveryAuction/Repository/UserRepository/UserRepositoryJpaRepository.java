package com.joung45387.EveryAuction.Repository.UserRepository;


import com.joung45387.EveryAuction.Domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpaRepository extends JpaRepository<User, Long> {
    public User findByUsername(String userName);
}
