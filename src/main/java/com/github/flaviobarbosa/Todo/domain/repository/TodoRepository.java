package com.github.flaviobarbosa.Todo.domain.repository;

import com.github.flaviobarbosa.Todo.domain.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
