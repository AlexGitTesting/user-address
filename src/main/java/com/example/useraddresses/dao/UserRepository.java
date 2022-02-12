package com.example.useraddresses.dao;

import com.example.useraddresses.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents repository layer for {@link User}.
 *
 * @author Alexandr Yefremov
 * @see PagingAndSortingRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
