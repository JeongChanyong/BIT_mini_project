package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    // JPA Query Method
    User findByUsername(String username); // 기본 jpa가 지원 하는 Query는 한정적이여서 만들어서 사용할 수 있게끔 지원을..



}
