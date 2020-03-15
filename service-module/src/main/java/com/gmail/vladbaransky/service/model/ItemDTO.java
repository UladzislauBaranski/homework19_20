package com.gmail.vladbaransky.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemDTO {

    private Integer id;
    @NotNull
    @Size(max = 40)
    private String name;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
