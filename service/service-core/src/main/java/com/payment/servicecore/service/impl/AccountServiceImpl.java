package com.payment.servicecore.service.impl;

import com.payment.servicecore.entity.Account;
import com.payment.servicecore.mapper.AccountMapper;
import com.payment.servicecore.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hzy
 * @since 2021-02-02
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
