package com.baobao.micro.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author baobao
 * @create 2021-09-03 16:29
 * @description Entity基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
	/**主键*/
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**创建时间*/
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**更新时间*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	/**逻辑删除字段*/
	@TableField(value = "is_deleted", select = false)
	@TableLogic
	private Boolean deleted;

	/**乐观锁字段*/
	/*@TableField(value = "version")
	@Version
	private Integer version;*/
}
