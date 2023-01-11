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

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository ur;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String signUp(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        return "registration";
    }

    @PostMapping("/signup")
    public String postSignUp(UserDTO userDTO){
        if(ur.isAlreadyUserName(userDTO.getUsername())){
            System.out.println("이미 존재하는 아이디");
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
