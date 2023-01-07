package com.joung45387.EveryAuction.Controller;

import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class OauthLoginController {
    private final UserRepository userRepository;
    @GetMapping("/oauthlogin")
    public String oauthLogin(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userRepository.findByUserName(principalDetails.getUsername());
        if(user.getPhoneNumber() == null){
            return "AdditionalInfoOauth";
        }
        return "redirect:/";
    }
}
