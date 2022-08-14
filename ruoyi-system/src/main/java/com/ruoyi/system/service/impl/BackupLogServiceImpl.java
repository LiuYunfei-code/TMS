package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Date;
import java.util.Map;

import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BackupLogMapper;
import com.ruoyi.system.domain.BackupLog;
import com.ruoyi.system.service.IBackupLogService;

/**
 * 备份日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-14
 */
@Service
public class BackupLogServiceImpl implements IBackupLogService 
{
    @Autowired
    private BackupLogMapper backupLogMapper;

    /**
     * 查询备份日志
     * 
     * @param backupTime 备份日志主键
     * @return 备份日志
     */
    @Override
    public BackupLog selectBackupLogByBackupTime(Date backupTime)
    {
        return backupLogMapper.selectBackupLogByBackupTime(backupTime);
    }

    /**
     * 查询备份日志列表
     * 
     * @param backupLog 备份日志
     * @return 备份日志
     */
    @Override
    public List<BackupLog> selectBackupLogList(BackupLog backupLog)
    {
        return backupLogMapper.selectBackupLogList(backupLog);
    }

    /**
     * 新增备份日志
     * 
     * @param backupLog 备份日志
     * @return 结果
     */
    @Override
    public int insertBackupLog(BackupLog backupLog)
    {
        backupLog.setId(UUID.randomUUID().toString().replace("-", ""));
        return backupLogMapper.insertBackupLog(backupLog);
    }

    /**
     * 修改备份日志
     * 
     * @param backupLog 备份日志
     * @return 结果
     */
    @Override
    public int updateBackupLog(BackupLog backupLog)
    {
        return backupLogMapper.updateBackupLog(backupLog);
    }

    /**
     * 批量删除备份日志
     * 
     * @param backupTimes 需要删除的备份日志主键
     * @return 结果
     */
    @Override
    public int deleteBackupLogByBackupTimes(Date[] backupTimes)
    {
        return backupLogMapper.deleteBackupLogByBackupTimes(backupTimes);
    }

    /**
     * 删除备份日志信息
     * 
     * @param backupTime 备份日志主键
     * @return 结果
     */
    @Override
    public int deleteBackupLogByBackupTime(Date backupTime)
    {
        return backupLogMapper.deleteBackupLogByBackupTime(backupTime);
    }

    /**
     * 备份表
     * @param table 表
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> getTableData(String table) {
        return backupLogMapper.getTableData(table);
    }


}
