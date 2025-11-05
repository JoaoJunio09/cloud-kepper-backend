package br.com.joaojunio.cloudkeeper.repositories;

import br.com.joaojunio.cloudkeeper.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
