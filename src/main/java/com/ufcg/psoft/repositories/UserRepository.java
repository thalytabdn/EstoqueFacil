package com.ufcg.psoft.repositories;

import java.io.Serializable;
import java.util.Optional;
import com.ufcg.psoft.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<T, ID extends Serializable> extends JpaRepository<User, String> {

	Optional<User> findByEmail(String email);
}
