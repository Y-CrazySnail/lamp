package com.snail.auth.dto;

import lombok.Data;

@Data
public class PhoneNumberDTO {
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Watermark watermark;
}
