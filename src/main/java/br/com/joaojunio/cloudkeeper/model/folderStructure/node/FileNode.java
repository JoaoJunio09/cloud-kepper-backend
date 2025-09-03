package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

public class FileNode {

    public String type = "file";
    public String name;
    public String fileType;
    public Long size;

    public FileNode() {}

    public FileNode(String name, String fileType, Long size) {
        this.name = name;
        this.fileType = fileType;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
