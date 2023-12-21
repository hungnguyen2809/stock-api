package com.hungnv28.core.repositories;

import com.hungnv28.core.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    @Query(nativeQuery = true, value = "CALL ERD_STOCK.CHECK_USER(:username);")
    UsersEntity checkUser(@Param("username") String username);
}
