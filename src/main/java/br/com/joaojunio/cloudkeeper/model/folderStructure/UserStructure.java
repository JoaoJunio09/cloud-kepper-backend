package br.com.joaojunio.cloudkeeper.model.folderStructure;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserStructure {

    public String username;
    public Long userId;
    public Map<String, Object> structure = new LinkedHashMap<>();

    public UserStructure() {}

    public UserStructure(String username, long userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<String, Object> getStructure() {
        return structure;
    }

    public void setStructure(Map<String, Object> structure) {
        this.structure = structure;
    }
}
