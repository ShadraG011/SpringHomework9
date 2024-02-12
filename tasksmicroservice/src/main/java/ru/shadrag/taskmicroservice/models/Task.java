package ru.shadrag.taskmicroservice.models;

import jakarta.persistence.*;
import lombok.Data;
import ru.shadrag.taskmicroservice.DAO.UserDao;
import ru.shadrag.taskmicroservice.components.Status;


import java.util.Date;


/**
 * ID (автоинкрементное)
 * Описание (не может быть пустым)
 * Статус (одно из значений: "не начата", "в процессе", "завершена")
 * Дата создания (автоматически устанавливается при создании задачи)
 */
@Data
@Entity
@Table(name = "tasks")
public class Task {


    public Task() {
        this.status = Status.NOT_START.getDescription();
        this.date = new Date();
    }

    public String getUserName() {
        if (user == null)
            return "Не назначен";
        else
            return user.getUsername();
    }

    //region ПОЛЯ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDao user;
    //endregion
}
