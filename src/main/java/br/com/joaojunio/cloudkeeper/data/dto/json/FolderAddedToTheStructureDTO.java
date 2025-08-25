package br.com.joaojunio.cloudkeeper.data.dto.json;

public class FolderAddedToTheStructureDTO {

    private Long userId;
    private String name;

    public FolderAddedToTheStructureDTO() {}

    public FolderAddedToTheStructureDTO(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
