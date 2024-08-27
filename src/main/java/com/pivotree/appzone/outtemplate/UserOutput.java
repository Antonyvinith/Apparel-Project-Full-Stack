package com.pivotree.appzone.outtemplate;
import com.pivotree.appzone.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserOutput {
    private String username;
    private String email;
    private UserType type;

    public UserOutput(String username, String email, UserType type) {
        this.username = username;
        this.email = email;
        this.type = type;
    }
}

