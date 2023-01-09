package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemUploadController {
    private final ItemRepository itemRepository;
    @GetMapping("/itemupload")
    public String SaleItemUpload(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        return "ItemUpload";
    }

    @PostMapping("/item/upload")
    public String PostSaleItemUpload(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     Model model,
                                     ItemDTO itemDTO,
                                     @RequestParam MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Item item = itemRepository.saveItem(itemDTO, principalDetails.getUser(), bytes);
        return "redirect:/item/"+item.getId();
    }
}
