package br.com.joaojunio.cloudkeeper.data.dto.json;

public class FileRemovedFromStructure {

    private Long userId;
    private String fileId;

    public FileRemovedFromStructure(Long userId, String fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
