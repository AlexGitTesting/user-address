package com.example.useraddresses.dao;

import com.example.useraddresses.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Set;

import static org.hibernate.jpa.QueryHints.HINT_READONLY;

@Repository
// TODO: 14.02.2022 fill
public interface AddressRepository extends JpaRepository<Address, Long> {

    @QueryHints(@QueryHint(name = HINT_READONLY, value = "true"))
    @Query("select a.id from Address as a where a.user.id=:userId")
    Set<Long> getAddressIdsByUserId(@Param("userId") Long userId);



    @Query("select a from Address as a where a.user.id=:userId and a.id in :addressIds")
        // TODO: 14.02.2022 country is eager check
    Set<Address> findAddressesByUserIdIn(@Param("addressIds") Set<Long> addressIds, @Param("userId") Long userId);
}
