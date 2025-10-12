package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNode extends Node {

    public String fileId;
    public String fileType;
    public Long size;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    public Date datetime;

    public FileNode() {
        setType("file");
        this.datetime = new Date();
    }

    public FileNode(String fileId, String name, String fileType, Long size) {
        setType("file");
        setName(name);
        this.datetime = new Date();
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

    public String getDatetime() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(datetime);
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
