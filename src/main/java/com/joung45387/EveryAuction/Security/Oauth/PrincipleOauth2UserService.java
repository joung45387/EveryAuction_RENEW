package com.joung45387.EveryAuction.Security.Oauth;

import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import com.joung45387.EveryAuction.Security.Oauth.UserInfo.GoogleUserInfo;
import com.joung45387.EveryAuction.Security.Oauth.UserInfo.KakaoUserInfo;
import com.joung45387.EveryAuction.Security.Oauth.UserInfo.NaverUserInfo;
import com.joung45387.EveryAuction.Security.Oauth.UserInfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(provider.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }
        else if(provider.equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_"+ providerId;
        String password = bCryptPasswordEncoder.encode("every_auction");
        String name = UUID.randomUUID().toString().substring(0,8);
        User user = userRepository.findByUserName(username);
        if(user == null){
            user = User
                    .builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .provider(provider)
                    .build();
            userRepository.saveUser(user);
        }

        return new PrincipalDetails(user, oAuth2UserInfo);
    }
}
