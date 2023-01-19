package com.joung45387.EveryAuction.Controller.Record;

import com.joung45387.EveryAuction.Domain.DTO.PurchaseInfoDTO;
import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.BidRecordRepository.BidRecordRepository;
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
public class PurchaseRecordController {
    private final BidRecordRepository bidRecordRepository;
    @GetMapping("/purchaserecord")
    public String purchaseRecord(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        List<PurchaseInfoDTO> infos = bidRecordRepository.myPurchase(principalDetails.getUser().getId());
        model.addAttribute("items", infos);
        model.addAttribute("myId", principalDetails.getUser().getId());
        return "purchaseRecord";
    }
}
