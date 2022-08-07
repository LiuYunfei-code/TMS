package com.ruoyi.orderInfo.service.impl;

import java.util.List;

import com.ruoyi.carinfo.domain.Carinfo;
import com.ruoyi.carinfo.service.ICarinfoService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.orderInfo.mapper.OrderBaseinfoMapper;
import com.ruoyi.orderInfo.domain.OrderBaseinfo;
import com.ruoyi.orderInfo.service.IOrderBaseinfoService;

/**
 * 报表信息Service业务层处理
 * 
 * @author liuyunfei
 * @date 2022-04-04
 */
@Service
public class OrderBaseinfoServiceImpl implements IOrderBaseinfoService 
{
    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;

    @Autowired
    private ICarinfoService carinfoService;

    /**
     * 查询报表信息
     * 
     * @param id 报表信息主键
     * @return 报表信息
     */
    @Override
    public OrderBaseinfo selectOrderBaseinfoById(String id)
    {
        return orderBaseinfoMapper.selectOrderBaseinfoById(id);
    }

    /**
     * 查询报表信息列表
     * 
     * @param orderBaseinfo 报表信息
     * @return 报表信息
     */
    @Override
    public List<OrderBaseinfo> selectOrderBaseinfoList(OrderBaseinfo orderBaseinfo)
    {
        return orderBaseinfoMapper.selectOrderBaseinfoList(orderBaseinfo);
    }

    /**
     * 新增报表信息
     * 
     * @param orderBaseinfo 报表信息
     * @return 结果
     */
    @Override
    public int insertOrderBaseinfo(OrderBaseinfo orderBaseinfo)
    {
        orderBaseinfo.setCreateTime(DateUtils.getNowDate());
        orderBaseinfo.setId(UUID.fastUUID().toString().replaceAll("-", ""));
        insertNewCar(orderBaseinfo);
        return orderBaseinfoMapper.insertOrderBaseinfo(orderBaseinfo);
    }

    /**
     * 修改报表信息
     * 
     * @param orderBaseinfo 报表信息
     * @return 结果
     */
    @Override
    public int updateOrderBaseinfo(OrderBaseinfo orderBaseinfo)
    {
        orderBaseinfo.setUpdateTime(DateUtils.getNowDate());
        insertNewCar(orderBaseinfo);
        return orderBaseinfoMapper.updateOrderBaseinfo(orderBaseinfo);
    }

    /**
     * 批量删除报表信息
     * 
     * @param ids 需要删除的报表信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderBaseinfoByIds(String[] ids)
    {
        return orderBaseinfoMapper.deleteOrderBaseinfoByIds(ids);
    }

    /**
     * 删除报表信息信息
     * 
     * @param id 报表信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderBaseinfoById(String id)
    {
        return orderBaseinfoMapper.deleteOrderBaseinfoById(id);
    }

    /**
     * 保存新的车牌号
     * @param orderBaseinfo 订单信息
     */
    private void insertNewCar(OrderBaseinfo orderBaseinfo) {
        // 查询这个车牌是是否已经存在
        Carinfo carinfoQuery = new Carinfo();
        carinfoQuery.setCarNumber(orderBaseinfo.getCarNumber());
        List<Carinfo> existedCarList = carinfoService.selectCarinfoList(carinfoQuery);
        if (existedCarList.isEmpty()) {
            // 不存在，保存新车牌
            carinfoService.insertCarinfo(carinfoQuery);
        }
    }
}
