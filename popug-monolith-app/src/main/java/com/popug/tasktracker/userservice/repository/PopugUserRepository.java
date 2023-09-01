package com.popug.tasktracker.userservice.repository;

import com.popug.tasktracker.userservice.entity.PopugUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopugUserRepository extends CrudRepository<PopugUser,Long> {
    PopugUser findByUsername(String username);
}
