package ru.shadrag.usersmicroservices.models;

import jakarta.persistence.*;
import lombok.Data;
import ru.shadrag.usersmicroservices.DAO.TaskDao;

import java.util.List;
@Data
@Entity
@Table(name = "users")
public class User {

    //region ПОЛЯ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<TaskDao> userTasksList;
    //endregion
}
