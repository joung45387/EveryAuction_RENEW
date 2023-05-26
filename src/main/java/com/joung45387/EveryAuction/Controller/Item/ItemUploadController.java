package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.ImageRepository.ImageRepository;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import com.joung45387.EveryAuction.Service.ImageService;
import com.joung45387.EveryAuction.Service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemUploadController {
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final S3Upload s3Upload;

    @GetMapping("/itemupload")
    public String SaleItemUpload(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        return "ItemUpload";
    }

    @PostMapping("/itemupload")
    public String PostSaleItemUpload(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     Model model,
                                     ItemDTO itemDTO,
                                     @RequestParam MultipartFile[] files) throws IOException {
        String thumnailLink = "";
        if(!files[0].isEmpty()){
            BufferedImage thumnail = imageService.makeThumbnail(files[0], 600, 300);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(thumnail, "jpeg", os);
            InputStream fileInputStream = new ByteArrayInputStream(os.toByteArray());
            thumnailLink = s3Upload.upload(fileInputStream, files[0].getName()+"thumb");
            os.close();
            fileInputStream.close();
        }

        Item item = itemRepository.saveItem(itemDTO, principalDetails.getUser(), thumnailLink);
        try {
            for(MultipartFile file : files){
                InputStream inputStream = file.getInputStream();
                String upload = s3Upload.upload(inputStream, file.getOriginalFilename());
                imageRepository.saveImage(upload, item);
                inputStream.close();
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "redirect:/item/"+item.getId();
    }
}
