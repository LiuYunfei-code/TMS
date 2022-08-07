package com.ruoyi.orderInfo.service;

import java.util.List;
import com.ruoyi.orderInfo.domain.OrderBaseinfo;

/**
 * 报表信息Service接口
 * 
 * @author liuyunfei
 * @date 2022-04-04
 */
public interface IOrderBaseinfoService 
{
    /**
     * 查询报表信息
     * 
     * @param id 报表信息主键
     * @return 报表信息
     */
    public OrderBaseinfo selectOrderBaseinfoById(String id);

    /**
     * 查询报表信息列表
     * 
     * @param orderBaseinfo 报表信息
     * @return 报表信息集合
     */
    public List<OrderBaseinfo> selectOrderBaseinfoList(OrderBaseinfo orderBaseinfo);

    /**
     * 新增报表信息
     * 
     * @param orderBaseinfo 报表信息
     * @return 结果
     */
    public int insertOrderBaseinfo(OrderBaseinfo orderBaseinfo);

    /**
     * 修改报表信息
     * 
     * @param orderBaseinfo 报表信息
     * @return 结果
     */
    public int updateOrderBaseinfo(OrderBaseinfo orderBaseinfo);

    /**
     * 批量删除报表信息
     * 
     * @param ids 需要删除的报表信息主键集合
     * @return 结果
     */
    public int deleteOrderBaseinfoByIds(String[] ids);

    /**
     * 删除报表信息信息
     * 
     * @param id 报表信息主键
     * @return 结果
     */
    public int deleteOrderBaseinfoById(String id);
}
