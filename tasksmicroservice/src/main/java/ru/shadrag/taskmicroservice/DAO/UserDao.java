package ru.shadrag.taskmicroservice.DAO;

import jakarta.persistence.*;
import lombok.Data;
import ru.shadrag.taskmicroservice.models.Task;

import java.util.List;
@Data
@Entity
@Table(name = "users")
public class UserDao {

    //region КОНСТРУКТОРЫ
    public UserDao() {
    }

    public UserDao(String username) {
        this.username = username;
    }
    //endregion


    //region ПОЛЯ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Task> userTasksList;
    //endregion
}
