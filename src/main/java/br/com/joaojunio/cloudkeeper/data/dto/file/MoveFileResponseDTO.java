package br.com.joaojunio.cloudkeeper.data.dto.file;

public class MoveFileResponseDTO {

    private String fileName;
    private String fileId;
    private String oldFolder;
    private String currentFolder;

    public MoveFileResponseDTO() {}

    public MoveFileResponseDTO(String fileName, String fileId, String oldFolder, String currentFolder) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.oldFolder = oldFolder;
        this.currentFolder = currentFolder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOldFolder() {
        return oldFolder;
    }

    public void setOldFolder(String oldFolder) {
        this.oldFolder = oldFolder;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(String currentFolder) {
        this.currentFolder = currentFolder;
    }
}
