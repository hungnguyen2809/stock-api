package com.hungnv28.core.repositories;

import com.hungnv28.core.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "CALL CHECK_LOGIN(:username, :password);")
    public Users checkUser(@Param("username") String username, @Param("password") String password);
}
