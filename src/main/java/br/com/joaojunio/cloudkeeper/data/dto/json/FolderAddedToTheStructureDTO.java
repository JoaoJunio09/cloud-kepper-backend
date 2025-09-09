package br.com.joaojunio.cloudkeeper.data.dto.json;

public class FolderAddedToTheStructureDTO {

    private Long userId;
    private String newFolderName;
    private String folderName;

    public FolderAddedToTheStructureDTO() {}

    public FolderAddedToTheStructureDTO(Long userId, String newFolderName, String folderName) {
        this.userId = userId;
        this.newFolderName = newFolderName;
        this.folderName = folderName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
