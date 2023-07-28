package com.whatsapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
//    @NotBlank
    private String full_name;

//    @NotBlank
    private String email;
    private String profile_picture;
//    @NotBlank
    private String password;

//    @OneToMany
//    public List<Message> messages=new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
//    private List<Chat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(full_name, user.full_name) && Objects.equals(email, user.email) && Objects.equals(profile_picture, user.profile_picture) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, full_name, email, profile_picture, password);
    }
}
