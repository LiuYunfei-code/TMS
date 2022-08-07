package com.ruoyi.carinfo.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carinfo.mapper.CarinfoMapper;
import com.ruoyi.carinfo.domain.Carinfo;
import com.ruoyi.carinfo.service.ICarinfoService;

/**
 * 车辆信息Service业务层处理
 * 
 * @author liuyunfei
 * @date 2022-05-29
 */
@Service
public class CarinfoServiceImpl implements ICarinfoService 
{
    @Autowired
    private CarinfoMapper carinfoMapper;

    /**
     * 查询车辆信息
     * 
     * @param id 车辆信息主键
     * @return 车辆信息
     */
    @Override
    public Carinfo selectCarinfoById(String id)
    {
        return carinfoMapper.selectCarinfoById(id);
    }

    /**
     * 查询车辆信息列表
     * 
     * @param carinfo 车辆信息
     * @return 车辆信息
     */
    @Override
    public List<Carinfo> selectCarinfoList(Carinfo carinfo)
    {
        return carinfoMapper.selectCarinfoList(carinfo);
    }

    /**
     * 新增车辆信息
     * 
     * @param carinfo 车辆信息
     * @return 结果
     */
    @Override
    public int insertCarinfo(Carinfo carinfo)
    {
        carinfo.setCreateTime(DateUtils.getNowDate());
        carinfo.setId(UUID.fastUUID().toString().replaceAll("-", ""));
        return carinfoMapper.insertCarinfo(carinfo);
    }

    /**
     * 修改车辆信息
     * 
     * @param carinfo 车辆信息
     * @return 结果
     */
    @Override
    public int updateCarinfo(Carinfo carinfo)
    {
        carinfo.setUpdateTime(DateUtils.getNowDate());
        return carinfoMapper.updateCarinfo(carinfo);
    }

    /**
     * 批量删除车辆信息
     * 
     * @param ids 需要删除的车辆信息主键
     * @return 结果
     */
    @Override
    public int deleteCarinfoByIds(String[] ids)
    {
        return carinfoMapper.deleteCarinfoByIds(ids);
    }

    /**
     * 删除车辆信息信息
     * 
     * @param id 车辆信息主键
     * @return 结果
     */
    @Override
    public int deleteCarinfoById(String id)
    {
        return carinfoMapper.deleteCarinfoById(id);
    }
}
