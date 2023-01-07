package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    @GetMapping("/item/{id}")
    public String SaleItemUpload(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model){
        Item item = itemRepository.findByItemId(id);

        return "ItemUpload";
    }
}
