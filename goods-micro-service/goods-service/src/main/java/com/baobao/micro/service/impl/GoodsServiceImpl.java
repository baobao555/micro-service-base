package com.baobao.micro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.domain.enums.GoodsTypeEnum;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.to.GoodsAddTO;
import com.baobao.micro.domain.to.GoodsUpdateTO;
import com.baobao.micro.domain.vo.backend.GoodsExcelVO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.baobao.micro.mapper.GoodsMapper;
import com.baobao.micro.service.GoodsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import net.dreamlu.mica.redis.cache.MicaRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author baobao
 * @create 2022-03-25 16:07
 * @description
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private MicaRedisCache redisCache;

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
    @Cacheable(cacheNames = "goods", key = "'list'")
    public List<GoodsListVO> list(GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = this.buildQueryWrapper(query);
        List<Goods> goodsList = this.list(wrapper);
        return BeanUtil.copyToList(goodsList, GoodsListVO.class);
    }

    @Override
    public PageVO<GoodsListVO> listPage(Integer pageNum, Integer pageSize, GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = this.buildQueryWrapper(query);
        Page<Goods> page = new Page<>(pageNum, pageSize);
        this.page(page, wrapper);
        PageVO<GoodsListVO> pageVO = BeanUtil.copyProperties(page, PageVO.class, "records");
        pageVO.setRecords(BeanUtil.copyToList(page.getRecords(), GoodsListVO.class));
        return pageVO;
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

    @Override
    public List<GoodsExcelVO> export(GoodsQuery query) {
        List<GoodsListVO> list = this.list(query);
        return list.stream().map(vo -> {
            GoodsExcelVO goodsExcelVO = BeanUtil.copyProperties(vo, GoodsExcelVO.class);
            goodsExcelVO.setType(vo.getType().getDesc());
            return goodsExcelVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ErrorMessage> importGoods(List<GoodsExcelVO> excelVOList) {
        List<Goods> goodsList = new ArrayList<>();
        List<ErrorMessage> errorMessageList = new ArrayList<>();
        // 自定义校验
        for (int i = 0; i < excelVOList.size(); i++) {
            Set<String> errors = new HashSet<>();
            GoodsExcelVO excelVO = excelVOList.get(i);
            if (!GoodsTypeEnum.contains(excelVO.getType())) {
                errors.add("商品类型不存在");
            }
            if (CollUtil.isEmpty(errors)) {
                // 校验通过，加入添加数据集合
                Goods goods = BeanUtil.copyProperties(excelVO, Goods.class);
                goods.setType(GoodsTypeEnum.get(excelVO.getType()));
                goodsList.add(goods);
            }else {
                // 校验不通过，加入错误消息集合
                errorMessageList.add(new ErrorMessage((long) (i + 2), errors));
            }
        }
        // 批量保存
        this.saveBatch(goodsList);
        return errorMessageList;
    }
}
