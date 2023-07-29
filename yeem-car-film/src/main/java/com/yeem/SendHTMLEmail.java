package com.yeem;

import cn.hutool.extra.mail.MailUtil;

public class SendHTMLEmail
{
    public static void main(String [] args)
    {
        MailUtil.send("1422486920@qq.com", "测试123312321", "312邮件来自Hutool测试", false);
    }
}