package me.june.mvc.validationgroup;

import javax.validation.constraints.NotEmpty;

public class Bean {

    @NotEmpty(groups = {User.class, Admin.class})
    String name;

    @NotEmpty(groups = {User.class})
    String userId;

    @NotEmpty(groups = {Admin.class})
    String adminId;

}
