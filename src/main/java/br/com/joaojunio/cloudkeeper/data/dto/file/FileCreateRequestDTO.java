package br.com.joaojunio.cloudkeeper.data.dto.file;

import java.util.Objects;
public class FileCreateRequestDTO {

    private Long id;
    private String name;
    private String type;
    private Long size;
    private Long userId;

    public FileCreateRequestDTO() {}

    public FileCreateRequestDTO(Long id, String name, String type, Long size, Long userId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userid) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FileCreateRequestDTO that = (FileCreateRequestDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getType(), that.getType()) && Objects.equals(getSize(), that.getSize()) && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getSize(), getUserId());
    }
}
