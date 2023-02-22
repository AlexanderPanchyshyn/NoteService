package com.pb.test.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Document(collection = "Users")
public class User {
    @Id
    @JsonIgnore
    private String id;
    private String username;
    private String password;
    @JsonIgnore
    private List<Role> roles;
}
