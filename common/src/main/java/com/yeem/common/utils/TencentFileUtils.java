package com.yeem.common.utils;

import cn.hutool.core.date.DateUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TencentFileUtils {

    /**
     * 获取无签名路径
     *
     * @param bucketName 桶名称
     * @param key        文件key
     * @return URL
     */
    public static URL getUrl(String bucketName, String region, String key) {
        COSCredentials cred = new AnonymousCOSCredentials();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            URL url = cosClient.getObjectUrl(bucketName, key);
            log.info("{}", url);
            return url;
        } catch (Exception e) {
            log.error("get object url error:", e);
            throw new RuntimeException("get object url error");
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 获取签名路径
     *
     * @param bucketName 桶名称
     * @param secretId   密钥ID
     * @param secretKey  密钥Key
     * @param region     地区
     * @param key        文件key
     * @return URL
     */
    public static URL generatePreSignedUrl(String bucketName, String secretId, String secretKey, String region, String key) {
        COSClient cosClient = createCOSClient(bucketName, secretId, secretKey, region);
        try {
            // 30天
            Date expirationDate = DateUtil.offsetDay(new Date(), 30);
            URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate);
            log.info("{}", url);
            return url;
        } catch (Exception e) {
            log.error("generate pre signed url error", e);
            throw new RuntimeException("generate pre signed url error");
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 腾讯云COS 上传文件
     *
     * @param bucketName  桶名称
     * @param secretId    密钥ID
     * @param secretKey   密钥Key
     * @param region      地区
     * @param key         文件Key
     * @param inputStream 文件输入流
     */
    public static UploadResult upload(String bucketName, String secretId, String secretKey, String region, String key, InputStream inputStream) {
        COSClient cosClient = createCOSClient(bucketName, secretId, secretKey, region);
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        try {
            long inputStreamLength = inputStream.available();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStreamLength);
            Map<String, String> userMeta = new HashMap<>();
            objectMetadata.setUserMetadata(userMeta);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
            putObjectRequest.setStorageClass(StorageClass.Standard);

            Upload upload = transferManager.upload(putObjectRequest);
            return upload.waitForUploadResult();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("upload file to tencent cos error:{}", e);
        } finally {
            transferManager.shutdownNow(true);
            cosClient.shutdown();
        }
    }

    /**
     * 获取临时凭证
     *
     * @return 临时凭证
     */
    public static Response getCredential(String bucketName, String secretId, String secretKey, String region) {
        TreeMap<String, Object> config = new TreeMap<>();
        try {
            config.put("secretId", secretId);
            config.put("secretKey", secretKey);
            config.put("durationSeconds", 1800);
            config.put("bucket", bucketName);
            config.put("region", region);
            config.put("allowPrefixes", new String[]{"*"});
            config.put("allowActions", new String[]{
                    "name/cos:PutObject",
                    "name/cos:PostObject",
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload",
                    "name/cos:GetObject"});
            return CosStsClient.getCredential(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

    /**
     * 创建COS客户端
     *
     * @return COS客户端
     */
    public static COSClient createCOSClient(String bucketName, String secretId, String secretKey, String region) {
        Response response = getCredential(bucketName, secretId, secretKey, region);
        String tmpSecretId = response.credentials.tmpSecretId;
        String tmpSecretKey = response.credentials.tmpSecretKey;
        String sessionToken = response.credentials.sessionToken;
        COSCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        clientConfig.setSocketTimeout(30 * 1000);
        clientConfig.setConnectionTimeout(30 * 1000);
        return new COSClient(cred, clientConfig);
    }
}