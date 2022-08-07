package com.ruoyi.carinfo.service;

import java.util.List;
import com.ruoyi.carinfo.domain.Carinfo;

/**
 * 车辆信息Service接口
 * 
 * @author liuyunfei
 * @date 2022-05-29
 */
public interface ICarinfoService 
{
    /**
     * 查询车辆信息
     * 
     * @param id 车辆信息主键
     * @return 车辆信息
     */
    public Carinfo selectCarinfoById(String id);

    /**
     * 查询车辆信息列表
     * 
     * @param carinfo 车辆信息
     * @return 车辆信息集合
     */
    public List<Carinfo> selectCarinfoList(Carinfo carinfo);

    /**
     * 新增车辆信息
     * 
     * @param carinfo 车辆信息
     * @return 结果
     */
    public int insertCarinfo(Carinfo carinfo);

    /**
     * 修改车辆信息
     * 
     * @param carinfo 车辆信息
     * @return 结果
     */
    public int updateCarinfo(Carinfo carinfo);

    /**
     * 批量删除车辆信息
     * 
     * @param ids 需要删除的车辆信息主键集合
     * @return 结果
     */
    public int deleteCarinfoByIds(String[] ids);

    /**
     * 删除车辆信息信息
     * 
     * @param id 车辆信息主键
     * @return 结果
     */
    public int deleteCarinfoById(String id);
}
