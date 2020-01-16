package com.dreamland.controller;

import com.dreamland.entity.AccessToken;
import com.dreamland.entity.GitHubUser;
import com.dreamland.utils.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String Client_id;
    @Value("${github.client.secret}")
    private String Client_secret;
    @Value("${github.Redirect.uri}")
    private String RedirectUri;


    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setClient_id(Client_id);
        accessToken.setClient_secret(Client_secret);
        accessToken.setRedirect_uri(RedirectUri);
        String Token = githubProvider.getAccessToken(accessToken);
        GitHubUser user = githubProvider.getUser(Token);
        System.out.println(user);
        return "index";
    }
}
