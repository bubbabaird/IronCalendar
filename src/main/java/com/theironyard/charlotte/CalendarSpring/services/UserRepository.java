package com.theironyard.charlotte.CalendarSpring.services;

import com.theironyard.charlotte.CalendarSpring.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by BUBBABAIRD on 5/11/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}