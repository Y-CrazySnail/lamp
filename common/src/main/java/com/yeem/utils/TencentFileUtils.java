package com.yeem.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TencentFileUtils {

    public static final String BUCKET_NAME = "ed-fs-1301197907";

    public static final String SECRET_ID = "AKIDb9UbVAcomW1HI5edPyKT4QXQ6XkjY9Vc";

    public static final String SECRET_KEY = "00mJcgV5QWN0OYBdrqeHhtseBTXLtDHA";

    public static final String REGION = "ap-shanghai";

    public static Response getCredential() {
        TreeMap<String, Object> config = new TreeMap<>();
        try {
            config.put("secretId", SECRET_ID);
            config.put("secretKey", SECRET_KEY);
            config.put("durationSeconds", 1800);
            config.put("bucket", BUCKET_NAME);
            config.put("region", REGION);
            config.put("allowPrefixes", new String[]{"*"});
            String[] allowActions = new String[]{
                    "name/cos:PutObject",
                    "name/cos:PostObject",
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload",
                    "name/cos:GetObject"};
            config.put("allowActions", allowActions);
            return CosStsClient.getCredential(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

    public static COSClient createCOSClient() {
        Response response = getCredential();
        String tmpSecretId = response.credentials.tmpSecretId;
        String tmpSecretKey = response.credentials.tmpSecretKey;
        String sessionToken = response.credentials.sessionToken;
        COSCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region("ap-shanghai"));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        clientConfig.setSocketTimeout(30 * 1000);
        clientConfig.setConnectionTimeout(30 * 1000);
        return new COSClient(cred, clientConfig);
    }

    public static void generatePreSignedUrl() {
        COSClient cosClient = createCOSClient();
        String key = "/test/testhjklasdhjkasd1.txt";
        Date expirationDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 10);
        URL url = cosClient.generatePresignedUrl(BUCKET_NAME, key, expirationDate);
        log.info("{}", url);
    }

    public static void upload() {
        COSClient cosClient = createCOSClient();
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        String key = "/test/testhjklasdhjkasd1.txt";
        String localFilePath = "C:\\Users\\dell\\Desktop\\test\\test.txt";
        File localFile = new File(localFilePath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        Map<String, String> userMeta = new HashMap<>();
        objectMetadata.setUserMetadata(userMeta);
        putObjectRequest.withMetadata(objectMetadata);
        try {
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            log.info("{}", uploadResult);
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        }
        transferManager.shutdownNow(true);
    }

}