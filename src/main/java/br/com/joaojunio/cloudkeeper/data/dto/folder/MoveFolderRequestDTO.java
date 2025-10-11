package br.com.joaojunio.cloudkeeper.data.dto.folder;

public class MoveFolderRequestDTO {

    private Long userId;
    private String folderName;
    private String folderId;
    private String newFolderName;

    public MoveFolderRequestDTO() {}

    public MoveFolderRequestDTO(Long userId, String folderName, String folderId, String newFolderName) {
        this.userId = userId;
        this.folderName = folderName;
        this.folderId = folderId;
        this.newFolderName = newFolderName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }
}
