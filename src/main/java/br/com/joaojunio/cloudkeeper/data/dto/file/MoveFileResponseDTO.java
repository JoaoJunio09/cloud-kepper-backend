package br.com.joaojunio.cloudkeeper.data.dto.file;

public class MoveFileResponseDTO {

    private String fileName;
    private String fileId;
    private String oldFolder;
    private String nameFolder;

    public MoveFileResponseDTO(String name, String fileId, String oldFolder, String nameFolder) {}

    public MoveFileResponseDTO(String fileName, String fileId) {
        this.fileName = fileName;
        this.fileId = fileId;
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

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }
}
