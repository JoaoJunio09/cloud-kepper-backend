package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

import java.util.ArrayList;
import java.util.List;

public class FolderNode extends Node {
    public List<Node> children = new ArrayList<>();

    public FolderNode() {
        setType("folder");
    }

    public FolderNode(String name) {
        setType("folder");
        setName(name);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
