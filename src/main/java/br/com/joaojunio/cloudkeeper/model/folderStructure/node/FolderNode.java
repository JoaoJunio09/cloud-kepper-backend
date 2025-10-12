package br.com.joaojunio.cloudkeeper.model.folderStructure.node;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FolderNode extends Node {
    public List<Node> children = new ArrayList<>();
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date datetime;

    public FolderNode() {
        setType("folder");
        this.datetime = new Date();
    }

    public FolderNode(String name) {
        setType("folder");
        setName(name);
        this.datetime = new Date();

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

    public String getDatetime() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(datetime);
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
