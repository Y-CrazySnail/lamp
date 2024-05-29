package com.yeem.lamp.application.command;

import lombok.Data;

@Data
public class CreateAladdinMemberCommand {
    private String email;
    private String password;
}
