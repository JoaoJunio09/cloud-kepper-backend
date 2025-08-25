package br.com.joaojunio.cloudkeeper.data.dto.json;

public class FileAddedToTheStructureDTO {

    private Long userId;
    private String type;
    private String name;
    private String localStorage;
    private Long size;

    public FileAddedToTheStructureDTO() {}

    public FileAddedToTheStructureDTO(Long userId, String type, String name, String localStorage, Long size) {
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.localStorage = localStorage;
        this.size = size;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getLocalStorage() {
        return localStorage;
    }

    public void setLocalStorage(String localStorage) {
        this.localStorage = localStorage;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
