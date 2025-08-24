package br.com.joaojunio.cloudkeeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FolderStructurePathConfig {

    private String folderStructure;

    public String getFolderStructure() {
        return folderStructure;
    }

    public void setFolderStructure(String folderStructure) {
        this.folderStructure = folderStructure;
    }
}
