package com.joung45387.EveryAuction.Controller;

import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String oauthLogin(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        return "main";
    }
}
