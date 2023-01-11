package com.joung45387.EveryAuction.Controller;

import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    @GetMapping("/")
    public String oauthLogin(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        List<Item> items = itemRepository.findAll();
        List<String> photos = items.stream().map(img -> new String(Base64.encodeBase64((byte[]) img.getItemPhoto()))).collect(Collectors.toList());
        model.addAttribute("items", items);
        model.addAttribute("photos", photos);
        return "main";
    }
}
