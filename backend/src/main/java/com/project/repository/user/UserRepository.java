package com.project.repository.user;

import com.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(Long id);
    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);

    Page<User> findAllByUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(String username, String firstName, String lastName, String email, Pageable pageable);
    Integer countAllByUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(String username, String firstName, String lastName, String email);

    @Query("SELECT u FROM User u WHERE banned = ?1 AND (username LIKE ?2 OR firstName LIKE ?3 OR lastName LIKE ?4 OR email LIKE ?5)")
    Page<User> findAllByBannedAndUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(Boolean banned, String username, String firstName, String lastName, String email, Pageable pageable);
    @Query("SELECT COUNT(u) FROM User u WHERE banned = ?1 AND (userName LIKE ?2 OR firstName LIKE ?3 OR lastName LIKE ?4 OR email LIKE ?5)")
    Integer countAllByBannedAndUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(Boolean banned ,String username, String firstName, String lastName, String email);

}
