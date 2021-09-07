package com.example.life_community.controller;

import com.example.life_community.dto.GiteeAccessTokenDTO;
import com.example.life_community.provider.GitHubProvider;
import com.example.life_community.provider.GiteeProvider;
import com.example.life_community.provider.GiteeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;
    @Autowired
    GiteeProvider giteeProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, HttpServletRequest request) {
        System.out.println("调用 callback，code=" + code);
        System.out.println("clientId：" + clientId);
        System.out.println("clientSecret：" + clientSecret);
        System.out.println("redirectUri：" + redirectUri);
        // 方案：HttpClient
        // 方案：OkHttp
        GiteeAccessTokenDTO giteeAccessTokenDTO = new GiteeAccessTokenDTO();
        giteeAccessTokenDTO.setGrant_type("authorization_code");
        giteeAccessTokenDTO.setCode(code);
        giteeAccessTokenDTO.setClient_id(clientId);
        giteeAccessTokenDTO.setRedirect_uri(redirectUri);
        giteeAccessTokenDTO.setClient_secret(clientSecret);
        String accessToken = giteeProvider.getAccessToken(giteeAccessTokenDTO);

        GiteeUser giteeUser = giteeProvider.getUser(accessToken);

        if(giteeUser != null) {
            request.getSession().setAttribute("user", giteeUser);// session 从 HttpServletRequest 获取
            return "redirect:/";
            // 登录成功，写 Cookie 和 Session
        } else {
            // 登录失败重新登录
            return "redirect:/";
        }

    }

    // GitHub
    /*public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request) {
        System.out.println("调用 callback，code=" + code + ", state=" + state);
        System.out.println("clientId：" + clientId);
        System.out.println("clientSecret：" + clientSecret);
        System.out.println("redirectUri：" + redirectUri);
        // 方案：HttpClient
        // 方案：OkHttp
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);

        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        System.out.println("gitHubUser：" + JSON.toJSONString(gitHubUser));

        if(gitHubUser != null) {
            request.getSession().setAttribute("user", gitHubUser);
            return "redirect:/";
            // 登录成功
        } else {
            // 登 去录失败
            return "redirect:/";
        }
    }*/

}
