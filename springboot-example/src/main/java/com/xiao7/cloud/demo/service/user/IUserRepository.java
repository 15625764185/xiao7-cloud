package com.xiao7.cloud.demo.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao7.cloud.demo.entity.user.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiao7
 * @since 2020-05-29
 */
public interface IUserRepository extends IService<User> {

     User getByIdInBill(String id);

    User getByIdDefault(String id);

    boolean deleteByIdInBill(String id);

    boolean deleteByIdDefault(String id);
}
