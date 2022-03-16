package com.baobao.micro.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一Controller返回的json数据格式
 * @author baobao
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("请求响应对象")
public class AjaxResult<T> {

	public interface ResultCode {
		int SUCCESS = 20000;
		int ERROR = 40000;
	}

	@ApiModelProperty(value = "是否成功")
	private Boolean success;

	@ApiModelProperty(value = "返回码")
	private Integer code;

	@ApiModelProperty(value = "返回消息")
	private String message;

	@ApiModelProperty(value = "返回数据")
	private T data;

	/**
	 * 把构造方法私有
	 */
	private AjaxResult() {}

	/**
	 * 返回成功消息
	 * @param message  返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static <T> AjaxResult<T> success(String message, T data) {
		return new AjaxResult<>(true, ResultCode.SUCCESS, message, data);
	}

	/**
	 * 返回成功消息
	 * @param message 返回内容
	 * @return 成功消息
	 */
	public static AjaxResult<Void> success(String message) {
		return AjaxResult.success(message, null);
	}

	/**
	 * 返回成功消息
	 * @param data 数据
	 * @return 成功消息
	 */
	public static <T> AjaxResult<T> success(T data) {
		return AjaxResult.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 * @return 成功消息
	 */
	public static AjaxResult<Void> success() {
		return AjaxResult.success("操作成功");
	}

	/**
	 * 返回错误消息
	 *
	 * @param code 状态码
	 * @param message  返回内容
	 * @param data 数据
	 * @return 错误消息
	 */
	public static <T> AjaxResult<T> error(int code, String message, T data) {
		return new AjaxResult<>(false, code, message, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @param message  返回内容
	 * @param data 数据对象
	 * @return 错误消息
	 */
	public static <T> AjaxResult<T> error(String message, T data) {
		return AjaxResult.error(ResultCode.ERROR, message, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @param code 状态码
	 * @param message  返回内容
	 * @return 错误消息
	 */
	public static AjaxResult<Void> error(int code, String message) {
		return AjaxResult.error(code, message,null);
	}

	/**
	 * 返回错误消息
	 * @param message 消息内容
	 * @return 错误消息
	 */
	public static AjaxResult<Void> error(String message) {
		return AjaxResult.error(ResultCode.ERROR, message);
	}

	/**
	 * 返回错误消息
	 * @return 错误消息
	 */
	public static AjaxResult<Void> error() {
		return AjaxResult.error("操作失败");
	}
}
