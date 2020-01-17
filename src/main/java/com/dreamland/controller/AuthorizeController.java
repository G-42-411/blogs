package com.dreamland.controller;

import com.dreamland.entity.AccessToken;
import com.dreamland.entity.GitHubUser;
import com.dreamland.mapper.UserMapper;
import com.dreamland.pojo.User;
import com.dreamland.utils.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;


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
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session, HttpServletResponse response) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setClient_id(Client_id);
        accessToken.setClient_secret(Client_secret);
        accessToken.setRedirect_uri(RedirectUri);
        String Token = githubProvider.getAccessToken(accessToken);
        GitHubUser gitHubUser = githubProvider.getUser(Token);
        //System.out.println(gitHubUser);
        if (gitHubUser != null) {
            User user = new User();
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setBio(gitHubUser.getBio());
            user.setToken(token);
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            Cookie cookie = new Cookie("token", token);
            //cookie.setMaxAge(5*60);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
