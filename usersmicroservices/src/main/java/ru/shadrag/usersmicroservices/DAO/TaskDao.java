package ru.shadrag.usersmicroservices.DAO;

import jakarta.persistence.*;
import lombok.Data;
import ru.shadrag.usersmicroservices.models.User;

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
public class TaskDao {


    public TaskDao() {

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
    private User user;
    //endregion
}
