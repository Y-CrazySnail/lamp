package com.snail.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
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

    public static void main(String[] args) {
        Response response = get();
        String tmpSecretId = response.credentials.tmpSecretId;
        String tmpSecretKey = response.credentials.tmpSecretKey;
        String sessionToken = response.credentials.sessionToken;
        COSCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region("ap-shanghai"));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        clientConfig.setSocketTimeout(30 * 1000);
        clientConfig.setConnectionTimeout(30 * 1000);
        COSClient cosClient = new COSClient(cred, clientConfig);
        String key = "/test/testhjklasdhjkasd1.txt";
        Date expirationDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 10);
        URL url = cosClient.generatePresignedUrl(BUCKET_NAME, key, expirationDate);
        log.info("{}", url);
    }
    public static Response get() {
        TreeMap<String, Object> config = new TreeMap<>();
        try {
            String secretId = "AKIDb9UbVAcomW1HI5edPyKT4QXQ6XkjY9Vc";
            String secretKey = "00mJcgV5QWN0OYBdrqeHhtseBTXLtDHA";
            config.put("secretId", secretId);
            config.put("secretKey", secretKey);
            config.put("durationSeconds", 1800);
            config.put("bucket", "ed-fs-1301197907");
            config.put("region", "ap-shanghai");
            config.put("allowPrefixes", new String[]{"*"});
            String[] allowActions = new String[]{"name/cos:PutObject", "name/cos:PostObject",
                    "name/cos:InitiateMultipartUpload", "name/cos:ListMultipartUploads",
                    "name/cos:ListParts", "name/cos:UploadPart", "name/cos:CompleteMultipartUpload"};
            config.put("allowActions", allowActions);
            return CosStsClient.getCredential(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }



    public static COSClient createCOSClient() {
        Response response = get();
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

    public static void upload() {

        Response response = get();
        String tmpSecretId = response.credentials.tmpSecretId;
        String tmpSecretKey = response.credentials.tmpSecretKey;
        String sessionToken = response.credentials.sessionToken;
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        Region region = new Region("ap-shanghai"); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
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


        // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
        // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);


        //若需要设置对象的自定义 Headers 可参照下列代码,若不需要可省略下面这几行,对象自定义 Headers 的详细信息可参考 https://cloud.tencent.com/document/product/436/13361
        ObjectMetadata objectMetadata = new ObjectMetadata();

        //若设置 Content-Type、Cache-Control、Content-Disposition、Content-Encoding、Expires 这五个字自定义 Headers，推荐采用 objectMetadata.setHeader()
        //        objectMetadata.setHeader(key, "value");
        //若要设置 “x-cos-meta-[自定义后缀]” 这样的自定义 Header，推荐采用
        Map<String, String> userMeta = new HashMap<>();
        objectMetadata.setUserMetadata(userMeta);

        putObjectRequest.withMetadata(objectMetadata);


        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回 UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            log.info("{}", uploadResult);
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        }
        transferManager.shutdownNow(true);
    }

}