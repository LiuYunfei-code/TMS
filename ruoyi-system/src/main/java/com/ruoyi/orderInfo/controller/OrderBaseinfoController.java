package com.ruoyi.orderInfo.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.orderInfo.domain.OrderBaseinfo;
import com.ruoyi.orderInfo.service.IOrderBaseinfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报表信息Controller
 * 
 * @author liuyunfei
 * @date 2022-04-04
 */
@RestController
@RequestMapping("/orderInfo/baseinfo")
public class OrderBaseinfoController extends BaseController
{
    @Autowired
    private IOrderBaseinfoService orderBaseinfoService;

    /**
     * 查询报表信息列表
     */
    @PreAuthorize("@ss.hasPermi('orderInfo:baseinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderBaseinfo orderBaseinfo)
    {
        startPage();
        List<OrderBaseinfo> list = orderBaseinfoService.selectOrderBaseinfoList(orderBaseinfo);
        return getDataTable(list);
    }

    /**
     * 导出报表信息列表
     */
    @PreAuthorize("@ss.hasPermi('orderInfo:baseinfo:export')")
    @Log(title = "报表信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderBaseinfo orderBaseinfo)
    {
        List<OrderBaseinfo> list = orderBaseinfoService.selectOrderBaseinfoList(orderBaseinfo);
        ExcelUtil<OrderBaseinfo> util = new ExcelUtil<OrderBaseinfo>(OrderBaseinfo.class);
        util.exportExcel(response, list, "报表信息数据");
    }

    /**
     * 获取报表信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('orderInfo:baseinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(orderBaseinfoService.selectOrderBaseinfoById(id));
    }

    /**
     * 新增报表信息
     */
    @PreAuthorize("@ss.hasPermi('orderInfo:baseinfo:add')")
    @Log(title = "报表信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderBaseinfo orderBaseinfo)
    {
        return toAjax(orderBaseinfoService.insertOrderBaseinfo(orderBaseinfo));
    }

    /**
     * 修改报表信息
     */
    @PreAuthorize("@ss.hasPermi('orderInfo:baseinfo:edit')")
    @Log(title = "报表信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderBaseinfo orderBaseinfo)
    {
        return toAjax(orderBaseinfoService.updateOrderBaseinfo(orderBaseinfo));
    }

    /**
     * 删除报表信息
     */
    @PreAuthorize("@ss.hasPermi('orderInfo:baseinfo:remove')")
    @Log(title = "报表信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(orderBaseinfoService.deleteOrderBaseinfoByIds(ids));
    }
}
