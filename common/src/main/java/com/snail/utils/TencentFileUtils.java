package com.snail.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.*;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;

import java.util.List;
import java.util.TreeMap;

public class TencentFileUtils {
    // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
    /**
     *
     */


    public static void main(String[] args) {

        Response response = get();
        String tmpSecretId = response.credentials.tmpSecretId;
        String tmpSecretKey = response.credentials.tmpSecretKey;
        String sessionToken = response.credentials.sessionToken;
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的地域
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
        Region region = new Region("ap-shanghai"); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        List<Bucket> buckets = cosClient.listBuckets();
        for(Bucket bucketElement : buckets){
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.out.println(bucketName);
            System.out.println(bucketLocation);
        }

//
//        // Bucket 的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
//        String bucketName = "ed-fs-1301197907";
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
//        // 设置 bucket 名称
//        listObjectsRequest.setBucketName(bucketName);
//        // prefix 表示列出的 object 的 key 以 prefix 开始
//        listObjectsRequest.setPrefix("images/");
//        // deliter 表示分隔符, 设置为/表示列出当前目录下的 object, 设置为空表示列出所有的 object
//        listObjectsRequest.setDelimiter("/");
//        // 设置最大遍历出多少个对象, 一次 listobject 最大支持1000
//        listObjectsRequest.setMaxKeys(1000);
//        ObjectListing objectListing = null;
//        do {
//            try {
//                objectListing = cosClient.listObjects(listObjectsRequest);
//            } catch (CosServiceException e) {
//                e.printStackTrace();
//                return;
//            } catch (CosClientException e) {
//                e.printStackTrace();
//                return;
//            }
//            // common prefix 表示被 delimiter 截断的路径, 如 delimter 设置为/, common prefix 则表示所有子目录的路径
//            List<String> commonPrefixs = objectListing.getCommonPrefixes();
//
//
//            // object summary 表示所有列出的 object 列表
//            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
//            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
//                // 文件的路径 key
//                String key = cosObjectSummary.getKey();
//                // 文件的 etag
//                String etag = cosObjectSummary.getETag();
//                // 文件的长度
//                long fileSize = cosObjectSummary.getSize();
//                // 文件的存储类型
//                String storageClasses = cosObjectSummary.getStorageClass();
//            }
//
//
//            String nextMarker = objectListing.getNextMarker();
//            listObjectsRequest.setMarker(nextMarker);
//        } while (objectListing.isTruncated());

    }

    public static Response get() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();


        try {
            //这里的 SecretId 和 SecretKey 代表了用于申请临时密钥的永久身份（主账号、子账号等），子账号需要具有操作存储桶的权限。
            String secretId = "AKIDb9UbVAcomW1HI5edPyKT4QXQ6XkjY9Vc";//用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
            String secretKey = "00mJcgV5QWN0OYBdrqeHhtseBTXLtDHA";//用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
            // 替换为您的云 api 密钥 SecretId
            config.put("secretId", secretId);
            // 替换为您的云 api 密钥 SecretKey
            config.put("secretKey", secretKey);


            // 设置域名:
            // 如果您使用了腾讯云 cvm，可以设置内部域名
            //config.put("host", "sts.internal.tencentcloudapi.com");


            // 临时密钥有效时长，单位是秒，默认 1800 秒，目前主账号最长 2 小时（即 7200 秒），子账号最长 36 小时（即 129600）秒
            config.put("durationSeconds", 1800);


            // 换成您的 bucket
            config.put("bucket", "ed-fs-1301197907");
            // 换成 bucket 所在地区
            config.put("region", "ap-shanghai");


            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径
            // 列举几种典型的前缀授权场景：
            // 1、允许访问所有对象："*"
            // 2、允许访问指定的对象："a/a1.txt", "b/b1.txt"
            // 3、允许访问指定前缀的对象："a*", "a/*", "b/*"
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefixes", new String[] {
                    "*"
            });


            // 密钥的权限列表。必须在这里指定本次临时密钥所需要的权限。
            // 简单上传、表单上传和分块上传需要以下的权限，其他权限列表请参见 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分块上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload",

                    "name/cos:GetBucketACL"
            };
            config.put("allowActions", allowActions);
            /**
             * 设置condition（如有需要）
             //# 临时密钥生效条件，关于condition的详细设置规则和COS支持的condition类型可以参考 https://cloud.tencent.com/document/product/436/71307
             final String raw_policy = "{\n" +
             "  \"version\":\"2.0\",\n" +
             "  \"statement\":[\n" +
             "    {\n" +
             "      \"effect\":\"allow\",\n" +
             "      \"action\":[\n" +
             "          \"name/cos:PutObject\",\n" +
             "          \"name/cos:PostObject\",\n" +
             "          \"name/cos:InitiateMultipartUpload\",\n" +
             "          \"name/cos:ListMultipartUploads\",\n" +
             "          \"name/cos:ListParts\",\n" +
             "          \"name/cos:UploadPart\",\n" +
             "          \"name/cos:CompleteMultipartUpload\"\n" +
             "        ],\n" +
             "      \"resource\":[\n" +
             "          \"qcs::cos:ap-shanghai:uid/1250000000:examplebucket-1250000000/*\"\n" +
             "      ],\n" +
             "      \"condition\": {\n" +
             "        \"ip_equal\": {\n" +
             "            \"qcs:ip\": [\n" +
             "                \"192.168.1.0/24\",\n" +
             "                \"101.226.100.185\",\n" +
             "                \"101.226.100.186\"\n" +
             "            ]\n" +
             "        }\n" +
             "      }\n" +
             "    }\n" +
             "  ]\n" +
             "}";


             config.put("policy", raw_policy);
             */
            Response response = CosStsClient.getCredential(config);
            System.out.println(response.credentials.tmpSecretId);
            System.out.println(response.credentials.tmpSecretKey);
            System.out.println(response.credentials.sessionToken);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
