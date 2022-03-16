package com.baobao.micro.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author baobao
 * @create 2021-09-03 16:29
 * @description Entity基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class BaseEntity {
	/**主键*/
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**创建时间*/
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;

	/**创建人*/
	@TableField(value = "create_by", fill = FieldFill.INSERT)
	private String createBy;

	/**更新时间*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date updateTime;

	/**更新人*/
	@TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	/**逻辑删除字段*/
	@TableField(value = "is_deleted", select = false)
	@TableLogic
	private Boolean deleted;

	/**乐观锁字段*/
	/*@TableField(value = "version")
	@Version
	private Integer version;*/
}
