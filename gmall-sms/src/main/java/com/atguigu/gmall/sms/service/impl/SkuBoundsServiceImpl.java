package com.atguigu.gmall.sms.service.impl;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.sms.dao.SkuBoundsDao;
import com.atguigu.gmall.sms.dao.SkuFullReductionDao;
import com.atguigu.gmall.sms.dao.SkuLadderDao;
import com.atguigu.gmall.sms.entity.SkuBoundsEntity;
import com.atguigu.gmall.sms.entity.SkuFullReductionEntity;
import com.atguigu.gmall.sms.entity.SkuLadderEntity;
import com.atguigu.gmall.sms.service.SkuBoundsService;
import com.atguigu.gmall.sms.vo.SaleVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;


@Service("skuBoundsService")
public class SkuBoundsServiceImpl extends ServiceImpl<SkuBoundsDao, SkuBoundsEntity> implements SkuBoundsService {
@Autowired
    private SkuLadderDao ladderDao;
@Autowired
private SkuFullReductionDao fullReductionDao;
    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuBoundsEntity> page = this.page(
                new Query<SkuBoundsEntity>().getPage(params),
                new QueryWrapper<SkuBoundsEntity>()
        );

        return new PageVo(page);
    }
  @GlobalTransactional
    @Override
    public void saveSales(SaleVO saveVo) {
        //3、营销相关的信息
        //3、1  skuBonds积分
        SkuBoundsEntity skuBoundsEntity = new SkuBoundsEntity();
        BeanUtils.copyProperties(saveVo,skuBoundsEntity);
        List<String> work = saveVo.getWork();
        skuBoundsEntity.setWork(new Integer(work.get(0))+new Integer(work.get(1))*2+new Integer(work.get(2))*4+new Integer(work.get(3))*8);
        save(skuBoundsEntity);
        //3.2  skuladder打折
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(saveVo,skuLadderEntity);
        skuLadderEntity.setAddOther(saveVo.getLadderAddOther());
        this.ladderDao.insert(skuLadderEntity);

        //3、3  FullRedction 满减
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(saveVo,skuFullReductionEntity);
        skuFullReductionEntity.setAddOther(saveVo.getFullAddOther());
        this.fullReductionDao.insert(skuFullReductionEntity);
    }

}