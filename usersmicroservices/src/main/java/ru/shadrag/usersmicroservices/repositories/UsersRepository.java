package ru.shadrag.usersmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shadrag.usersmicroservices.models.User;


public interface UsersRepository extends JpaRepository<User, Long> {
}
