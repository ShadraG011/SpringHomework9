package ru.shadrag.usersmicroservices.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shadrag.usersmicroservices.DAO.TaskDao;
import ru.shadrag.usersmicroservices.models.User;
import ru.shadrag.usersmicroservices.repositories.UsersRepository;
import ru.shadrag.usersmicroservices.services.interfaces.UsersService;


import java.sql.*;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User deletedUser = getUserById(id);
        usersRepository.delete(deletedUser);
    }


    @Override
    public List<TaskDao> getAllTasks() {
        return entityManager.createQuery("select t from TaskDao t", TaskDao.class).getResultList();
    }

    @Override
    public TaskDao getTaskById(Long taskId) {
        return entityManager.find(TaskDao.class, taskId);
    }

    @Override
    @Transactional
    public void updateTask(Long taskId, User user) {
        TaskDao task = getTaskById(taskId);
        task.setUser(user);
        entityManager.merge(task);
    }


}
