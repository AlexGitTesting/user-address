package com.example.useraddresses.dao;

import com.example.useraddresses.domain.Address;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Set;

import static org.hibernate.jpa.QueryHints.HINT_READONLY;

/**
 * Repository to work with {@link Address} entity.
 *
 * @author Alexandr Yefremov
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @QueryHints(@QueryHint(name = HINT_READONLY, value = "true"))
    @Query("select a.id from Address as a where a.user.id=:userId")
    Set<Long> getAddressIdsByUserId(@Param("userId") Long userId);


    @EntityGraph(attributePaths = {"country"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select a from #{#entityName} as a where a.user.id=:userId and a.id in :addressIds ")
    Set<Address> findAddressesByUserIdIn(@Param("addressIds") Set<Long> addressIds, @Param("userId") Long userId);
}
