package com.demo.repository;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByMail(String mail);

//    @Query("from User u where u.verificationStatus='pending' and u.mail=:mail")
//    User getUnverifiedUserBymail(String mail);

    @Query("FROM User u WHERE u.verificationStatus = 'pending' ")
    List<User> getUnverifiedUsers();


//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.isVerified = true WHERE u.isVerified = false AND u.mail = :userMail")
//    void updateUserVerificationStatus(@Param("userMail") String userMail);

//    @Query("UPDATE User u SET u.isVerified = true WHERE u.isVerified = false AND u.mail = :userMail")
//    void updateUserVerificationStatus(@Param("userMail") String userMail);

    @Transactional
    void deleteByMail(String mail);

}
