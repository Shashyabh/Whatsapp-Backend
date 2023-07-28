package com.whatsapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String chat_name;
    private String chat_image;

    @Column(name = "is_group")
    private boolean isGroup;

    @ManyToMany
    private Set<User> admin=new HashSet<>();

//    @Column(name = "created_by")
    @ManyToOne
    private User createdBy;

    @ManyToMany
    private Set<User> users=new HashSet<>();

    @OneToMany
    private List<Message> messages=new ArrayList<>();

}
