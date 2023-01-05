package com.joung45387.EveryAuction.Controller;

import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class signInController {
    @GetMapping("/signin")
    public String singIn(@AuthenticationPrincipal PrincipalDetails user, Model model){
        if(user!=null){
            System.out.println(user.getUsername());
            model.addAttribute("login", true);
        }
        model.addAttribute("login", false);
        return "signin";
    }
}
