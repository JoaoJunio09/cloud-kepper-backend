package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

import java.util.ArrayList;
import java.util.List;

public class FolderNode {

    public String type = "folder";
    public String name;
    public List<Object> children = new ArrayList<>();

    public FolderNode() {}

    public FolderNode(String name) {
        this.name = name;
    }

    public void addChild(Object child) {
        children.add(child);
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

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }
}
