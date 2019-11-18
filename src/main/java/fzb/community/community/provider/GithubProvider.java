package fzb.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import fzb.community.community.dto.AccessTokenDTO;
import fzb.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public static final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String accessToken = string.split("&")[0].split("=")[1];
            return accessToken;
        } catch (Exception e) {
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            }
            catch (Exception e){}
            return null;
    }
}
