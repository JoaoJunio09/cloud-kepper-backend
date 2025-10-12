package br.com.joaojunio.cloudkeeper.data.dto.folder;

public class MoveFolderRequestDTO {

    private Long userId;
    private String FolderId;
    private String currentFolderId;

    public MoveFolderRequestDTO() {}

    public MoveFolderRequestDTO(Long userId, Long folderId, Long currentFolderId) {
        this.userId = userId;
        this.FolderId = String.valueOf(folderId);
        this.currentFolderId = String.valueOf(currentFolderId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFolderId() {
        return FolderId;
    }

    public void setFolderId(String FolderId) {
        this.FolderId = FolderId;
    }

    public String getCurrentFolderId() {
        return currentFolderId;
    }

    public void setCurrentFolderId(String currentFolderId) {
        this.currentFolderId = currentFolderId;
    }
}
