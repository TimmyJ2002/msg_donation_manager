package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Modifying
  @Query("DELETE FROM User u WHERE u.id= :id")
  void deleteUsersById(@Param("id") Long id);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  User save( User user);
}
