package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Date;
import java.util.Map;

import com.ruoyi.system.domain.BackupLog;

/**
 * 备份日志Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-14
 */
public interface BackupLogMapper 
{
    /**
     * 查询备份日志
     * 
     * @param backupTime 备份日志主键
     * @return 备份日志
     */
    public BackupLog selectBackupLogByBackupTime(Date backupTime);

    /**
     * 查询备份日志列表
     * 
     * @param backupLog 备份日志
     * @return 备份日志集合
     */
    public List<BackupLog> selectBackupLogList(BackupLog backupLog);

    /**
     * 新增备份日志
     * 
     * @param backupLog 备份日志
     * @return 结果
     */
    public int insertBackupLog(BackupLog backupLog);

    /**
     * 修改备份日志
     * 
     * @param backupLog 备份日志
     * @return 结果
     */
    public int updateBackupLog(BackupLog backupLog);

    /**
     * 删除备份日志
     * 
     * @param backupTime 备份日志主键
     * @return 结果
     */
    public int deleteBackupLogByBackupTime(Date backupTime);

    /**
     * 批量删除备份日志
     * 
     * @param backupTimes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBackupLogByBackupTimes(Date[] backupTimes);

    /**
     * 查询表数据
     * @param table 表
     * @return 数据
     */
    List<Map<String, Object>> getTableData(String table);
}
