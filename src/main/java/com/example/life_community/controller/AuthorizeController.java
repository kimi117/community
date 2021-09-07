package com.example.life_community.controller;

import com.example.life_community.dto.GiteeAccessTokenDTO;
import com.example.life_community.model.User;
import com.example.life_community.provider.GitHubProvider;
import com.example.life_community.provider.GiteeProvider;
import com.example.life_community.provider.GiteeUser;
import com.example.life_community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;
    @Autowired
    GiteeProvider giteeProvider;
    @Autowired
    UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, HttpServletRequest request, HttpServletResponse response) {
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
//            request.getSession().setAttribute("user", giteeUser);// session 从 HttpServletRequest 获取

            Date date = new Date();

            // Ctrl+Alt+v
            User user = new User();
            user.setName(giteeUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(giteeUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            // 当登录成功后，将获取到的用户信息及生成 Token 存储到数据库
            // 并 Token 存储 Cookie
            response.addCookie(new Cookie("loginToken", token));

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
