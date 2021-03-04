package Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Application.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query(value = "SELECT * FROM User WHERE userName = ?1", nativeQuery = true)
  User findByUsername(String userName);

  @Query(value = "SELECT * FROM User WHERE userName = ?1 AND password = ?2", nativeQuery = true)
  User findByUsernameAndPassword(String username, String password);

  @Query(value = "Select * FROM User where userId = ?1", nativeQuery = true)
  int getUserCountFromSuperUser(int userId);
}
