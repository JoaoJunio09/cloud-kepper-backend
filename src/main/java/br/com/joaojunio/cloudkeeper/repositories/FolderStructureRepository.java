package br.com.joaojunio.cloudkeeper.repositories;

import br.com.joaojunio.cloudkeeper.model.FolderStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderStructureRepository extends JpaRepository<FolderStructure, Long> {
}
