package com.pb.test.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter @Setter
public class Role {
    @Id
    private String id;
    private String roleName;
    private List<String> permissions;

    public Role(String roleName, List<String> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }
}
