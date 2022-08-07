package com.ruoyi.carinfo.controller;

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
import com.ruoyi.carinfo.domain.Carinfo;
import com.ruoyi.carinfo.service.ICarinfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车辆信息Controller
 * 
 * @author liuyunfei
 * @date 2022-05-29
 */
@RestController
@RequestMapping("/carInfo/carinfo")
public class CarinfoController extends BaseController
{
    @Autowired
    private ICarinfoService carinfoService;

    /**
     * 查询车辆信息列表
     */
    @PreAuthorize("@ss.hasPermi('carInfo:carinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Carinfo carinfo)
    {
        startPage();
        List<Carinfo> list = carinfoService.selectCarinfoList(carinfo);
        return getDataTable(list);
    }

    /**
     * 导出车辆信息列表
     */
    @PreAuthorize("@ss.hasPermi('carInfo:carinfo:export')")
    @Log(title = "车辆信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Carinfo carinfo)
    {
        List<Carinfo> list = carinfoService.selectCarinfoList(carinfo);
        ExcelUtil<Carinfo> util = new ExcelUtil<Carinfo>(Carinfo.class);
        util.exportExcel(response, list, "车辆信息数据");
    }

    /**
     * 获取车辆信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('carInfo:carinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(carinfoService.selectCarinfoById(id));
    }

    /**
     * 新增车辆信息
     */
    @PreAuthorize("@ss.hasPermi('carInfo:carinfo:add')")
    @Log(title = "车辆信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Carinfo carinfo)
    {
        return toAjax(carinfoService.insertCarinfo(carinfo));
    }

    /**
     * 修改车辆信息
     */
    @PreAuthorize("@ss.hasPermi('carInfo:carinfo:edit')")
    @Log(title = "车辆信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Carinfo carinfo)
    {
        return toAjax(carinfoService.updateCarinfo(carinfo));
    }

    /**
     * 删除车辆信息
     */
    @PreAuthorize("@ss.hasPermi('carInfo:carinfo:remove')")
    @Log(title = "车辆信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(carinfoService.deleteCarinfoByIds(ids));
    }
}
