package com.artesanalbeer.artesanalbeerstore.reposotory.user;

import com.artesanalbeer.artesanalbeerstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
