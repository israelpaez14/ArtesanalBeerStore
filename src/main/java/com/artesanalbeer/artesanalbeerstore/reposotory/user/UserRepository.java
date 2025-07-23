package com.artesanalbeer.artesanalbeerstore.reposotory.user;

import com.artesanalbeer.artesanalbeerstore.entities.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
}
