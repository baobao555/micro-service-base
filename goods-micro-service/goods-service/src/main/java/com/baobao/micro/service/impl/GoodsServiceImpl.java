package com.baobao.micro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.to.GoodsAddTO;
import com.baobao.micro.domain.to.GoodsUpdateTO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.baobao.micro.mapper.GoodsMapper;
import com.baobao.micro.service.GoodsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baobao
 * @create 2022-03-25 16:07
 * @description
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Override
    public void add(GoodsAddTO to) {
        Goods goods = BeanUtil.copyProperties(to, Goods.class);
        this.save(goods);
    }

    @Override
    public void update(Long id, GoodsUpdateTO to) {
        Goods goods = BeanUtil.copyProperties(to, Goods.class);
        goods.setId(id);
        this.updateById(goods);
    }

    @Override
    public List<GoodsListVO> list(GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = this.buildQueryWrapper(query);
        List<Goods> goodsList = this.list(wrapper);
        return BeanUtil.copyToList(goodsList, GoodsListVO.class);
    }

    @Override
    public Page<Goods> listPage(Integer pageNum, Integer pageSize, GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = this.buildQueryWrapper(query);
        Page<Goods> page = new Page<>(pageNum, pageSize);
        this.page(page, wrapper);
        return page;
    }

    private LambdaQueryWrapper<Goods> buildQueryWrapper(GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = Wrappers.lambdaQuery();
        if (query != null) {
            wrapper.like(StrUtil.isNotBlank(query.getName()), Goods::getName, query.getName());
            wrapper.eq(query.getType() != null, Goods::getType, query.getType());
            wrapper.ge(query.getProductionDateBegin() != null, Goods::getProductionDate, query.getProductionDateBegin());
            wrapper.le(query.getProductionDateEnd() != null, Goods::getProductionDate, query.getProductionDateEnd());
        }
        return wrapper;
    }
}
