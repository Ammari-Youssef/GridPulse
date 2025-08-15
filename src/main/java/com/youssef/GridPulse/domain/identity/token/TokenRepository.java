package com.youssef.GridPulse.domain.identity.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

  @Query(value = """
    select t from Token t inner join User u
    on t.user.id = u.id
    where u.id = :id and t.tokenType = :tokenType and t.expired = false and t.revoked = false
    """)
  List<Token> findAllValidTokenByUser(UUID id, TokenType tokenType);

  Optional<Token> findByToken(String token);
}
