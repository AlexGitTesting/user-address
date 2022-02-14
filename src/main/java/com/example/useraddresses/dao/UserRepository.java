package com.example.useraddresses.dao;

import com.example.useraddresses.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Represents repository layer for {@link User}.
 *
 * @author Alexandr Yefremov
 * @see PagingAndSortingRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"addresses", "addresses.country"},type = EntityGraph.EntityGraphType.FETCH)
    @Query("select u from #{#entityName} u where u.id=:userId" )
    Optional<User> getUserById(@Param("userId")Long userId);
}
