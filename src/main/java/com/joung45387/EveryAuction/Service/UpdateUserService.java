package com.joung45387.EveryAuction.Service;

import com.joung45387.EveryAuction.Domain.DTO.OAuthSignUpDTO;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public void updateUser(Long userId, OAuthSignUpDTO oAuthSignUpDTO){
        User user = userRepository.findByUserId(userId);
        user = userRepository.updateOAuthUser(user, oAuthSignUpDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());

        PrincipalDetails principalDetails = new PrincipalDetails(user);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principalDetails, user.getPassword(), authorities));

    }
}
