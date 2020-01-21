package com.dreamland.controller;

import com.dreamland.dto.AccessToken;
import com.dreamland.dto.GitHubUser;
import com.dreamland.pojo.User;
import com.dreamland.service.UserService;
import com.dreamland.utils.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    private UserService userService;

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
        if (gitHubUser != null) {
            User user = new User();
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setBio(gitHubUser.getBio());
            user.setToken(token);
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            userService.createOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
