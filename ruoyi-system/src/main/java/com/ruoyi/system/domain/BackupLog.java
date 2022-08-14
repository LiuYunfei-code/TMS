package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 备份日志对象 backup_log
 * 
 * @author ruoyi
 * @date 2022-08-14
 */
public class BackupLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 备份时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "备份时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date backupTime;

    /** $column.columnComment */
    private String id;

    public void setBackupTime(Date backupTime) 
    {
        this.backupTime = backupTime;
    }

    public Date getBackupTime() 
    {
        return backupTime;
    }
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("backupTime", getBackupTime())
            .append("id", getId())
            .toString();
    }
}
