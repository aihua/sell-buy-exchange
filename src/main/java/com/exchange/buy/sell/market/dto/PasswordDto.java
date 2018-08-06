package com.exchange.buy.sell.market.dto;

import com.exchange.buy.sell.registration.annotation.ValidPassword;

/**
 * Created by oleht on 22.07.2018
 */
public class PasswordDto {
    private String oldPassword;

    @ValidPassword
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
