package com.xiao7.cloud.demo.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao7.cloud.boot.jdbc.annotation.DataSource;
import com.xiao7.cloud.demo.entity.user.User;
import com.xiao7.cloud.demo.mapper.user.UserMapper;
import com.xiao7.cloud.demo.service.user.IUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xiao7
 * @since 2020-05-29
 */
@Service
public class UserRepositoryImpl extends ServiceImpl<UserMapper, User> implements IUserRepository {


    @Override
    @DataSource(name = "bill")
    public User getByIdInBill(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public User getByIdDefault(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    @DataSource(name = "bill")
    @Transactional
    public boolean deleteByIdInBill(String id) {
        int delete = baseMapper.deleteById(id);
        int i = 1/0;  //检查事务是否生效
        return delete > 0;
    }

    @Override
    public boolean deleteByIdDefault(String id) {
        return baseMapper.deleteById(id) > 0;
    }
}
