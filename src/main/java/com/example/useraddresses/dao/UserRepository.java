package com.example.useraddresses.dao;

import com.example.useraddresses.domain.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Represents repository layer for {@link UserProfile}.
 *
 * @author Alexandr Yefremov
 * @see PagingAndSortingRepository
 */
@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

    @EntityGraph(attributePaths = {"addresses", "addresses.country"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select u from #{#entityName} u where u.id=:userId")
    Optional<UserProfile> getUserById(@Param("userId") Long userId);
}
