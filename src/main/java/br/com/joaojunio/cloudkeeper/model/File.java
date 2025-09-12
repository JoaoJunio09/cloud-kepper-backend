package br.com.joaojunio.cloudkeeper.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "size", nullable = false)
    private Long size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public File() {}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(getId(), file.getId()) && Objects.equals(getName(), file.getName()) && Objects.equals(getType(), file.getType()) && Objects.equals(getSize(), file.getSize()) && Objects.equals(getUser(), file.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType(), getSize(), getUser());
    }
}
