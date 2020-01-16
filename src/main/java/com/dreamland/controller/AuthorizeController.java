package com.dreamland.controller;

import com.dreamland.entity.AccessToken;
import com.dreamland.entity.GitHubUser;
import com.dreamland.utils.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setClient_id("73de90edf12125142f2b");
        accessToken.setClient_secret("f63d2bd6341276734af97b1c9bd3f85d68d7abef");
        accessToken.setRedirect_uri("http://localhost:8080/callback");
        String Token = githubProvider.getAccessToken(accessToken);
        GitHubUser user = githubProvider.getUser(Token);
        System.out.println(user);
        return "index";
    }
}
