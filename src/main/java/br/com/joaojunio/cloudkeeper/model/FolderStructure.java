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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FolderStructure() {}

    public String getFolderStructurePath() {
        return folderStructurePath;
    }

    public void setFolderStructurePath(String folderStructurePath) {
        this.folderStructurePath = folderStructurePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
