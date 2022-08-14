package com.ruoyi.system.controller;

import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BackupLog;
import com.ruoyi.system.service.IBackupLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 备份日志Controller
 * 
 * @author ruoyi
 * @date 2022-08-14
 */
@RestController
@RequestMapping("/system/backupLog")
public class BackupLogController extends BaseController
{
    @Autowired
    private IBackupLogService backupLogService;

    /**
     * 查询备份日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:backupLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(BackupLog backupLog)
    {
        startPage();
        List<BackupLog> list = backupLogService.selectBackupLogList(backupLog);
        return getDataTable(list);
    }

    /**
     * 导出备份日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:backupLog:export')")
    @Log(title = "备份日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BackupLog backupLog)
    {
        List<BackupLog> list = backupLogService.selectBackupLogList(backupLog);
        ExcelUtil<BackupLog> util = new ExcelUtil<BackupLog>(BackupLog.class);
        util.exportExcel(response, list, "备份日志数据");
    }

    /**
     * 获取备份日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:backupLog:query')")
    @GetMapping(value = "/{backupTime}")
    public AjaxResult getInfo(@PathVariable("backupTime") Date backupTime)
    {
        return AjaxResult.success(backupLogService.selectBackupLogByBackupTime(backupTime));
    }

    /**
     * 新增备份日志
     */
    @PreAuthorize("@ss.hasPermi('system:backupLog:add')")
    @Log(title = "备份日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BackupLog backupLog)
    {
        return toAjax(backupLogService.insertBackupLog(backupLog));
    }

    /**
     * 修改备份日志
     */
    @PreAuthorize("@ss.hasPermi('system:backupLog:edit')")
    @Log(title = "备份日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BackupLog backupLog)
    {
        return toAjax(backupLogService.updateBackupLog(backupLog));
    }

    /**
     * 删除备份日志
     */
    @PreAuthorize("@ss.hasPermi('system:backupLog:remove')")
    @Log(title = "备份日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{backupTimes}")
    public AjaxResult remove(@PathVariable Date[] backupTimes)
    {
        return toAjax(backupLogService.deleteBackupLogByBackupTimes(backupTimes));
    }

    /**
     * 获取表数据
     */
	@GetMapping("/getTableData")
    public AjaxResult getTableData(@RequestParam("table") String table)
    {
        return AjaxResult.success(backupLogService.getTableData(table));
    }
}
