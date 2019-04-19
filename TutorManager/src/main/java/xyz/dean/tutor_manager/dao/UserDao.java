package xyz.dean.tutor_manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.dean.tutor_manager.pojo.User;

public interface UserDao extends JpaRepository<User, Integer> {

}
