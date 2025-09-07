package br.com.joaojunio.cloudkeeper.data.dto.file;

public class DeleteFileResponseDTO {

    private String fileName;
    private String type;
    private Long size;

    public DeleteFileResponseDTO() {}

    public DeleteFileResponseDTO(String fileName, String type, Long size) {
        this.fileName = fileName;
        this.type = type;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
}
