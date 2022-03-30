package com.baobao.micro.common.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

/**
 * @author baobao
 * @create 2022-03-29 10:48
 * @description 包含数据添加用户和更新用户的Entity基类
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateUserBaseEntity extends BaseEntity {
    /**创建人*/
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**更新人*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}
