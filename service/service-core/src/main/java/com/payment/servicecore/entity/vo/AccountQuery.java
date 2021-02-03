package com.payment.servicecore.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Account查询对象", description = "账户查询对象封装")
@Data
public class AccountQuery {

    @ApiModelProperty(value = "账户名，模糊查询")
    private String name;

    @ApiModelProperty(value = "账户类型 1普通账户 2root")
    private Integer level;

    @ApiModelProperty(value = "最高工资")
    private String max;

    @ApiModelProperty(value = "最低工资")
    private String min;

}
