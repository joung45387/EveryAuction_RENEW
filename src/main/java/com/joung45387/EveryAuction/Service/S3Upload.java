package com.joung45387.EveryAuction.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(InputStream inputStream, String fileName) throws IOException {
        fileName = "image/"+UUID.randomUUID()+fileName;
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(inputStream.available());


        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        inputStream.close();
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}