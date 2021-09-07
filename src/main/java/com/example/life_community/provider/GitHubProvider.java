package com.example.life_community.provider;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    /*public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        System.out.println("调用getAccessToken");
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println("string：" + string);

            String token = string.split("&")[0].split("=")[1];
            System.out.println("token："+ token);

            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken) {
        System.out.println("调用getUser ， accessToken： " + accessToken);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user").header("Authorization", "token " + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().toString();
            System.out.println("string：" + string);
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
