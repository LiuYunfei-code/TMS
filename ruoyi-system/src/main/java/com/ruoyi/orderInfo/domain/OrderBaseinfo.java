package com.ruoyi.orderInfo.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报表信息对象 baseinfo
 * 
 * @author liuyunfei
 * @date 2022-04-04
 */
public class OrderBaseinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    private String delFlag;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;

    /** 车号 */
    @Excel(name = "车号")
    private String carNumber;

    /** 客户名 */
    @Excel(name = "客户名")
    private String clientName;

    /** 装货地 */
    @Excel(name = "装货地")
    private String loadingPlace;

    /** 卸货地 */
    @Excel(name = "卸货地")
    private String unloadingPlace;

    /** 货物名称 */
    @Excel(name = "货物名称")
    private String goodsName;

    /** 运费 */
    @Excel(name = "运费")
    private Double freight;

    /** 卸货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "卸货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date unloadingTime;

    /** 付款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "付款时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date paymentTime;

    /** 司机 */
    @Excel(name = "司机")
    private String driverName;

    /** 趟次 */
    @Excel(name = "趟次")
    private Double trips;

    /** 是否标注 */
    @Excel(name = "是否标注")
    private String isMarked;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setOrderDate(Date orderDate) 
    {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() 
    {
        return orderDate;
    }
    public void setCarNumber(String carNumber) 
    {
        this.carNumber = carNumber;
    }

    public String getCarNumber() 
    {
        return carNumber;
    }
    public void setClientName(String clientName) 
    {
        this.clientName = clientName;
    }

    public String getClientName() 
    {
        return clientName;
    }
    public void setLoadingPlace(String loadingPlace) 
    {
        this.loadingPlace = loadingPlace;
    }

    public String getLoadingPlace() 
    {
        return loadingPlace;
    }
    public void setUnloadingPlace(String unloadingPlace) 
    {
        this.unloadingPlace = unloadingPlace;
    }

    public String getUnloadingPlace() 
    {
        return unloadingPlace;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }

    public void setUnloadingTime(Date unloadingTime) 
    {
        this.unloadingTime = unloadingTime;
    }

    public Date getUnloadingTime() 
    {
        return unloadingTime;
    }
    public void setPaymentTime(Date paymentTime) 
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() 
    {
        return paymentTime;
    }
    public void setDriverName(String driverName) 
    {
        this.driverName = driverName;
    }

    public String getDriverName() 
    {
        return driverName;
    }

    public void setIsMarked(String isMarked) 
    {
        this.isMarked = isMarked;
    }

    public String getIsMarked() 
    {
        return isMarked;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getTrips() {
        return trips;
    }

    public void setTrips(Double trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("orderDate", getOrderDate())
            .append("carNumber", getCarNumber())
            .append("clientName", getClientName())
            .append("loadingPlace", getLoadingPlace())
            .append("unloadingPlace", getUnloadingPlace())
            .append("goodsName", getGoodsName())
            .append("freight", getFreight())
            .append("remark", getRemark())
            .append("unloadingTime", getUnloadingTime())
            .append("paymentTime", getPaymentTime())
            .append("driverName", getDriverName())
            .append("trips", getTrips())
            .append("isMarked", getIsMarked())
            .toString();
    }
}
