package com.example.life_community.provider;

import lombok.Data;

@Data
public class GiteeUser {

    private Long id;
    private String login;
    private String name;
    private String avatar_url;
    private String html_url;
    private String bio;

}
