package br.com.joaojunio.cloudkeeper.model;

import jakarta.persistence.*;

@Entity
@Table(name = "folder_structure")
public class FolderStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folder_structure_path")
    private String folderStructurePath;

    public FolderStructure() {}

    public String getFolderStructurePath() {
        return folderStructurePath;
    }

    public void setFolderStructurePath(String folderStructurePath) {
        this.folderStructurePath = folderStructurePath;
    }
}
