package com.baobao.micro.service.backend;

import com.baobao.micro.common.domain.PageParam;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.dto.GoodsAddDTO;
import com.baobao.micro.domain.dto.GoodsUpdateDTO;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.vo.backend.GoodsExcelVO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;

import java.util.List;
import java.util.Set;

/**
 * @author baobao
 * @create 2022-05-26 17:04
 * @description 后台商品Service
 */
public interface GoodsBackendService {
    /**
     * 添加商品
     * @param to
     */
    void add(GoodsAddDTO to);

    /**
     * 修改商品
     * @param id 商品id
     * @param to
     */
    void update(Long id, GoodsUpdateDTO to);

    /**
     * 条件查询商品
     * @param query 条件参数
     * @return 商品列表
     */
    List<GoodsListVO> list(GoodsQuery query);

    /**
     * 条件分页查询商品
     * @param pageParam 分页参数
     * @param query 查询条件
     * @return 商品列表
     */
    PageVO<GoodsListVO> page(PageParam pageParam, GoodsQuery query);

    /**
     * 导出商品信息
     * @param query 查询条件
     * @return 商品excel信息
     */
    List<GoodsExcelVO> export(GoodsQuery query);

    /**
     * 导入商品信息
     * @param excelVOList 导入数据
     * @return 导入校验失败信息
     */
    List<ErrorMessage> importGoods(List<GoodsExcelVO> excelVOList);

    /**
     * 批量删除商品
     * @param ids id集合
     */
    void removeByIds(Set<Long> ids);
}
