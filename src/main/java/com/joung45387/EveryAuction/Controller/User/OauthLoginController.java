package com.joung45387.EveryAuction.Controller.User;

import com.joung45387.EveryAuction.Domain.DTO.OAuthSignUpDTO;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import com.joung45387.EveryAuction.Service.UpdateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class OauthLoginController {
    private final UserRepository userRepository;
    private final UpdateUserService updateUserService;

    @GetMapping("/oauthlogin")
    public String oauthLogin(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        if(user.getPhoneNumber() == null){
            return "AdditionalInfoOauth";
        }
        return "redirect:/";
    }
    @PostMapping("/oauthSignUp")
    public String oauthSignUp(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, OAuthSignUpDTO oAuthSignUpDTO){
        updateUserService.updateUser(principalDetails.getUser().getId(), oAuthSignUpDTO);
        return "redirect:/";
    }
}
