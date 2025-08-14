package br.com.joaojunio.cloudkeeper.repositories;

import br.com.joaojunio.cloudkeeper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
