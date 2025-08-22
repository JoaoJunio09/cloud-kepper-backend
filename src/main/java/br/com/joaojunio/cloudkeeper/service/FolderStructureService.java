package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.FolderStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FolderStructureService {

    @Autowired
    FolderStructureRepository repository;

    public void generetedObjectJsonFolderStructure(br.com.joaojunio.cloudkeeper.model.User user) {
        try {
            User userStructure = new User(user.getFirstName(), user.getId());



        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    static class User {
        public String username;
        public long userId;
        public Map<String, Object> structure = new LinkedHashMap<>();

        public User(String username, long userId) {
            this.username = username;
            this.userId = userId;
        }
    }

    static class FileNode {
        public String type = "file";
        public String name;
        public String localStorage;
        public String fileType;

        public FileNode(String name, String localStorage, String fileType) {
            this.name = name;
            this.localStorage = localStorage;
            this.fileType = fileType;
        }
    }

    static class FolderNode {
        public String type = "folder";
        public String name;
        public List<Object> children = new ArrayList<>();

        public FolderNode(String name) {
            this.name = name;
        }

        public void addChildren(Object child) {
            children.add(child);
        }
    }
}
