package br.com.joaojunio.cloudkeeper.data.dto.json;

public class FolderAddedToTheStructureDTO {

    private Long userId;
    private String newFolderName;
    private String folderName;
    private Long folderId;

    public FolderAddedToTheStructureDTO() {}

    public FolderAddedToTheStructureDTO(Long userId, String newFolderName, String folderName, Long folderId) {
        this.userId = userId;
        this.newFolderName = newFolderName;
        this.folderName = folderName;
        this.folderId = folderId;
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

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }
}
