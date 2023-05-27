package com.joung45387.EveryAuction.Controller.User;


import com.joung45387.EveryAuction.Domain.DTO.UserDTO;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository ur;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String signUp(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam(value = "error", required = false)String error){
        model.addAttribute("error", error);
        return "registration";
    }

    @PostMapping("/signup")
    public String postSignUp(UserDTO userDTO){
        User byUserName = ur.findByUserName(userDTO.getUsername());
        User byName = ur.findByName(userDTO.getName());
        if(byUserName!=null){
            String errorMessage = URLEncoder.encode("이미 존재하는 아이디입니다", StandardCharsets.UTF_8);
            return "redirect:/signup?error="+errorMessage;
        }
        else if(byName!=null){
            String errorMessage = URLEncoder.encode("이미 존재하는 닉네임입니다", StandardCharsets.UTF_8);
            return "redirect:/signup?error="+errorMessage;
        }
        else {
            User user = User.builder()
                    .username(userDTO.getUsername())
                    .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                    .name(userDTO.getName())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .address(userDTO.getAddress())
                    .build();
            ur.saveUser(user);
        }
        return "redirect:/";
    }
}
