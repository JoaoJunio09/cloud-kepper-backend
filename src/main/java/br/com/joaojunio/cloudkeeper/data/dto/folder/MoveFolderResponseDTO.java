package br.com.joaojunio.cloudkeeper.data.dto.folder;

public class MoveFolderResponseDTO {

    private String folderName;
    private String folderId;

    public MoveFolderResponseDTO() {}

    public MoveFolderResponseDTO(String folderName, String folderId) {
        this.folderName = folderName;
        this.folderId = folderId;
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
}
