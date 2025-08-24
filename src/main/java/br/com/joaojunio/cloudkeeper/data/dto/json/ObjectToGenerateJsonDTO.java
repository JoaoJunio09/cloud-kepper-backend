package br.com.joaojunio.cloudkeeper.data.dto.json;

import br.com.joaojunio.cloudkeeper.config.FolderStructurePathConfig;

public class ObjectToGenerateJsonDTO {

    private Long userId;
    private String userName;
    private FolderStructurePathConfig path;

    public ObjectToGenerateJsonDTO() {}

    public ObjectToGenerateJsonDTO(Long userId, String userName, FolderStructurePathConfig path) {
        this.userId = userId;
        this.userName = userName;
        this.path = path;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return String.valueOf(path);
    }

    public void setPath(FolderStructurePathConfig path) {
        this.path = path;
    }
}
