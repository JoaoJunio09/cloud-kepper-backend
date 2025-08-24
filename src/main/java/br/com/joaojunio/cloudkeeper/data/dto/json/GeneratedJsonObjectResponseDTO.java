package br.com.joaojunio.cloudkeeper.data.dto.json;

import java.util.Date;

public class GeneratedJsonObjectResponseDTO {

    private String message;
    private String savedPath;
    private Date timestamp;

    public GeneratedJsonObjectResponseDTO() {}

    public GeneratedJsonObjectResponseDTO(String message, String savedPath, Date timestamp) {
        this.message = message;
        this.savedPath = savedPath;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSavedPath() {
        return savedPath;
    }

    public void setSavedPath(String savedPath) {
        this.savedPath = savedPath;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
