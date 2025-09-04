package br.com.joaojunio.cloudkeeper.data.dto.file;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UploadFileResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String type;
    private Long size;
    private Long timestamp;

    public UploadFileResponseDTO(String fileName, Long timestamp, String type, Long size) {
        this.fileName = fileName;
        this.timestamp = timestamp;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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
        return Objects.equals(getFileName(), that.getFileName()) && Objects.equals(getType(), that.getType()) && Objects.equals(getSize(), that.getSize()) && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileName(), getType(), getSize(), getTimestamp());
    }
}
