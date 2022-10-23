package com.user.account.api.dto;

import com.techbank.account.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseResponse {

    private  String id;
    public UserResponse(String message, String id){
        super(message);
        this.id=id;

    }


}
