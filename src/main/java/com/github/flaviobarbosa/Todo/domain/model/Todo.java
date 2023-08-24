package com.github.flaviobarbosa.Todo.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "done", nullable = false)
    private boolean done;

}
