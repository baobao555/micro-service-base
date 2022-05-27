package com.baobao.micro.service.backend.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baobao.micro.common.domain.PageParam;
import com.baobao.micro.common.domain.PageVO;
import com.baobao.micro.domain.dto.GoodsAddDTO;
import com.baobao.micro.domain.dto.GoodsUpdateDTO;
import com.baobao.micro.domain.entity.Goods;
import com.baobao.micro.domain.query.GoodsQuery;
import com.baobao.micro.domain.vo.backend.GoodsExcelVO;
import com.baobao.micro.domain.vo.backend.GoodsListVO;
import com.baobao.micro.enums.GoodsTypeEnum;
import com.baobao.micro.service.backend.GoodsBackendService;
import com.baobao.micro.service.base.GoodsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.redis.cache.MicaRedisCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author baobao
 * @create 2022-05-26 17:05
 * @description
 */
@Service
@RequiredArgsConstructor
public class GoodsBackendServiceImpl implements GoodsBackendService {
    private final MicaRedisCache redisCache;
    private final GoodsService goodsService;

    @Override
    public void add(GoodsAddDTO to) {
        // 校验促销时间
        Assert.isTrue(to.getPromotionStart().before(to.getPromotionEnd()), "促销开始时间不能大于结束时间");
        Goods goods = BeanUtil.copyProperties(to, Goods.class);
        goodsService.save(goods);
    }

    @Override
    public void update(Long id, GoodsUpdateDTO to) {
        Goods goods = goodsService.getById(id);
        Assert.notNull(goods, "要修改的商品不存在");
        // 校验促销时间
        Date promotionStart = to.getPromotionStart() == null ? goods.getPromotionStart() : to.getPromotionStart();
        Date promotionEnd = to.getPromotionEnd() == null ? goods.getPromotionEnd() : to.getPromotionEnd();
        Assert.isTrue(promotionStart.before(promotionEnd), "促销开始时间不能大于结束时间");
        Goods updateGoods = BeanUtil.copyProperties(to, Goods.class);
        updateGoods.setId(id);
        goodsService.updateById(updateGoods);
    }

    @Override
    @Cacheable(cacheNames = "goods", key = "'list'")
    public List<GoodsListVO> list(GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = this.buildQueryWrapper(query);
        List<Goods> goodsList = goodsService.list(wrapper);
        return BeanUtil.copyToList(goodsList, GoodsListVO.class);
    }

    @Override
    public PageVO<GoodsListVO> page(PageParam pageParam, GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = this.buildQueryWrapper(query);
        int pageNum = pageParam.getPageNum() == null ? 1 : pageParam.getPageNum();
        int pageSize = pageParam.getPageSize() == null ? 10 : pageParam.getPageSize();
        Page<Goods> page = new Page<>(pageNum, pageSize);
        if (StrUtil.isNotBlank(pageParam.getOrderColumn())) {
            String columnName = StrUtil.toUnderlineCase(pageParam.getOrderColumn());
            page.addOrder(BooleanUtil.isTrue(pageParam.getIsDesc()) ? OrderItem.desc(columnName) :
                    OrderItem.asc(columnName));
        }
        goodsService.page(page, wrapper);
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
        goodsService.saveBatch(goodsList);
        return errorMessageList;
    }

    @Override
    public void removeByIds(Set<Long> ids) {
        goodsService.removeByIds(ids);
    }
}
