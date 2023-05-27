package com.joung45387.EveryAuction.Controller.User;

import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class signInController {
    @GetMapping("/signin")
    public String singIn(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         Model model,
                         @RequestParam(value = "error", required = false)String error,
                         @RequestParam(value = "exception", required = false)String exception){
        if(principalDetails==null){
            model.addAttribute("error", error);
            model.addAttribute("exception", exception);
            return "signin";
        }

        return "redirect:/";
    }
}
