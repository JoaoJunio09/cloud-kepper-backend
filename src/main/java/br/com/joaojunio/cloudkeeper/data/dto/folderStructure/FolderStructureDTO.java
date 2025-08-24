package br.com.joaojunio.cloudkeeper.data.dto.folderStructure;

import br.com.joaojunio.cloudkeeper.data.dto.user.UserDTO;

public class FolderStructureDTO {

    private Long id;
    private String folderStructurePath;
    private UserDTO user;

    public FolderStructureDTO() {}

    public String getFolderStructurePath() {
        return folderStructurePath;
    }

    public void setFolderStructurePath(String folderStructurePath) {
        this.folderStructurePath = folderStructurePath;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
