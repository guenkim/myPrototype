package com.guen.program.todo.repository.jpa.springdata;

import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoSpringDataRepository extends JpaRepository<Todo,Long> {

}
