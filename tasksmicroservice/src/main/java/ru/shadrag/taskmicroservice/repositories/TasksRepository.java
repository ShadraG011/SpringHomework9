package ru.shadrag.taskmicroservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.shadrag.taskmicroservice.models.Task;


public interface TasksRepository extends JpaRepository<Task, Long> {
}
