package br.com.joaojunio.cloudkeeper.data.dto.file;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UploadFileResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileNameUri;
    private String type;
    private Long size;

    public UploadFileResponseDTO(String fileName, String fileNameUri, String type, Long size) {
        this.fileName = fileName;
        this.fileNameUri = fileNameUri;
        this.type = type;
        this.size = size;
    }

    public UploadFileResponseDTO() {}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameUri() {
        return fileNameUri;
    }

    public void setFileNameUri(String fileNameUri) {
        this.fileNameUri = fileNameUri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UploadFileResponseDTO that = (UploadFileResponseDTO) o;
        return Objects.equals(getFileName(), that.getFileName()) && Objects.equals(getFileNameUri(), that.getFileNameUri()) && Objects.equals(getType(), that.getType()) && Objects.equals(getSize(), that.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileName(), getFileNameUri(), getType(), getSize());
    }
}
