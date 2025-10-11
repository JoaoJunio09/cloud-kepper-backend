package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FolderNode extends Node {
    public List<Node> children = new ArrayList<>();
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    public FolderNode() {
        setType("folder");
    }

    public FolderNode(String name) {
        setType("folder");
        setName(name);

        Random generate = new Random();
        this.id = String.valueOf(generate.nextLong());
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
