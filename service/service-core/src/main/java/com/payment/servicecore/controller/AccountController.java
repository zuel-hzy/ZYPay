package com.payment.servicecore.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.payment.commonutils.result.Result;
import com.payment.servicecore.entity.Account;
import com.payment.servicecore.entity.vo.AccountQuery;
import com.payment.servicecore.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hzy
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/servicecore/account")
@Api(description = "账户管理")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //1.查询所有数据
    @GetMapping("findAll")
    @ApiOperation(value = "账户列表")
    public Result findAllAccount(){
        List<Account> list = accountService.list(null);
//        int i =10/0;
        return Result.ok().data("items",list);
    }

    //2.删除
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除账户")
    public Result removeAccount(
            @ApiParam(name = "id",value = "账户id",required = true)
            @PathVariable int id){
        boolean flag = accountService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //3.分页查询
    @GetMapping("/{current}/{size}")
    @ApiOperation(value = "分页查询账户")
    public Result pageListAccount(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable int current,
            @ApiParam(name = "size",value = "页面大小",required = true)
            @PathVariable int size){

        Page<Account> accountPage = new Page<>(current,size);
        accountService.page(accountPage,null);
        long total = accountPage.getTotal();
        List<Account> records = accountPage.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return Result.ok().data(map);

//        return Result.ok().data("total",total).data("rows",records);
    }

    //4.条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageAccountCondition/{current}/{size}")
    public Result pageAccountCondition(@ApiParam(name = "current",value = "当前页",required = true)
                                           @PathVariable int current,
                                       @ApiParam(name = "size",value = "页面大小",required = true)
                                           @PathVariable int size,
                                       @ApiParam(name = "accountQuery",value = "账户查询对象封装",required = false)
                                            @RequestBody(required = false) AccountQuery accountQuery){
        //创建page对象
        Page<Account> pageAccount = new Page<>(current, size);

        //构建条件
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        //wrapper多条件组合查询
        String name = accountQuery.getName();
        Integer level = accountQuery.getLevel();
        String max = accountQuery.getMax();
        String min = accountQuery.getMin();

        //判断条件是否为空，若不空则拼接条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("character",level);
        }
        if(!StringUtils.isEmpty(min)){
            wrapper.ge("money",min);
        }
        if(!StringUtils.isEmpty(max)){
            wrapper.le("money",max);
        }
        //调用方法实现条件查询分页
        accountService.page(pageAccount,wrapper);
        long total = pageAccount.getTotal();
        List<Account> records = pageAccount.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }
}

