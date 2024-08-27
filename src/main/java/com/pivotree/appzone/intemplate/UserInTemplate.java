package com.pivotree.appzone.intemplate;

import com.pivotree.appzone.enums.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInTemplate {
    private String username;
    private String password;
    private UserType type;

}
