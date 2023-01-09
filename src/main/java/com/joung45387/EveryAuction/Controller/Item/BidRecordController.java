package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.BidRecordRepository.BidRecordRepository;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BidRecordController {
    private final ItemRepository itemRepository;
    private final BidRecordRepository bidRecordRepository;
    @GetMapping("/item/{id}/bidRecord")
    public String saleItemUpload(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model){
        Item item = itemRepository.findByItemId(id);
        List<BidRecord> recordList = bidRecordRepository.findByItem(item);
        model.addAttribute("list", recordList);
        return "BidRecord";
    }
}
