package com.xiao7.cloud.demo.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiao7.cloud.boot.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xiao7
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
@Document(indexName = "example", type = "user", shards = 1, replicas = 0)
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("user_name")
    @Field(type = FieldType.Keyword)
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    @Field(type = FieldType.Keyword)
    private String password;

    /**
     * 年龄
     */
    @TableField("age")
    @Field(type = FieldType.Keyword)
    private Integer age;

    /**
     * 描述
     */
    @TableField("content")
    @Field(type = FieldType.Text)
    private String content;


    @Field(type = FieldType.Text)
    private String fileData;

    private Attachment attachment;
}
