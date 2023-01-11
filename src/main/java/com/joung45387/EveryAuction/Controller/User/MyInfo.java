package com.joung45387.EveryAuction.Controller.User;

import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyInfo {
    @GetMapping("/myinfo")
    public String signUp(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        model.addAttribute("myInfo", principalDetails.getUser());
        return "MyInfo";
    }
}
