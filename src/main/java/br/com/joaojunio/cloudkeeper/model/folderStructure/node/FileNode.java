package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

public class FileNode extends Node {

    public String fileId;
    public String fileType;
    public Long size;

    public FileNode() {
        setType("file");
    }

    public FileNode(String fileId, String name, String fileType, Long size) {
        setType("file");
        setName(name);
        this.fileId = fileId;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
