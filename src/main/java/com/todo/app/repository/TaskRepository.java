package com.todo.app.repository;

import com.todo.app.entity.Tasks;
import com.todo.app.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Integer> {

//    @Query("SELECT t FROM Tasks t WHERE t.userId = :userId")
//    List<Tasks> findAllTaskByUserId(@Param("userId") int userId);

    List<Tasks> findByUser_Id(int userId);

    @Query("SELECT t FROM Tasks t WHERE t.status = :status")
    List<Tasks> findAllTasksByStatus(@Param("status") TaskStatus status);
}
