package com.example.life_community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.life_community.dto.GiteeAccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {

    public String getAccessToken(GiteeAccessTokenDTO giteeAccessTokenDTO) {

        System.out.println("调用getAccessToken");
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(giteeAccessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println("string：" + string);

            JSONObject jsonObject = JSON.parseObject(string);
            String accessToken = jsonObject.getString("access_token");
            /*String token = string.split("&")[0].split("=")[1];
            System.out.println("token："+ token);*/

            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String accessToken) {
        System.out.println("调用getUser ， accessToken： " + accessToken);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();// 注意：这里很容易写成 toString()
            System.out.println("string：" + string);

            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            System.out.println("giteeUser JSON：" + JSON.toJSONString(giteeUser));

            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
