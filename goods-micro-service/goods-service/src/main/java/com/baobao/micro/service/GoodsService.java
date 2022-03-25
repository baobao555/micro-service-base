package com.baobao.micro.service;

import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.to.GoodsAddTO;
import com.baobao.micro.domain.to.GoodsUpdateTO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author baobao
 * @create 2022-03-25 16:06
 * @description
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 添加商品
     * @param to
     */
    void add(GoodsAddTO to);

    /**
     * 修改商品
     * @param id 商品id
     * @param to
     */
    void update(Long id, GoodsUpdateTO to);

    /**
     * 条件查询商品
     * @param query 条件参数
     * @return 商品列表
     */
    List<GoodsListVO> list(GoodsQuery query);

    /**
     * 条件分页查询商品
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @param query 查询条件
     * @return 商品列表
     */
    Page<Goods> listPage(Integer pageNum, Integer pageSize, GoodsQuery query);
}
