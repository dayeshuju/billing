package com.daye.sys.service.dsrw;

import com.daye.common.annotation.RequiredLog;
import com.daye.sys.mapper.SysLogMapper;
import com.daye.sys.mapper.TbCbjlMapper;
import com.daye.sys.mapper.TbJfjlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DsrwServiceImpl {

    @Autowired
    TbCbjlMapper tbCbjlMapper;
    @Autowired
    TbJfjlMapper tbJfjlMapper;
    @Autowired
    SysLogMapper sysLogMapper;


    //https://www.cnblogs.com/pejsidney/p/9046818.html
    //@Scheduled(cron = "0 09 18 ? * *")
    @RequiredLog(operation = "清理两年以上数据")
    public void qlTask(){
        tbCbjlMapper.cleanUp2Y();
        tbJfjlMapper.cleanUp2Y();
        sysLogMapper.cleanUp2Y();
    }

    // https://blog.csdn.net/wilson_m/article/details/81227079
    // https://blog.csdn.net/cnxieyang/article/details/42916311
    //@Scheduled(cron = "0 0 17 ? * *")
    @RequiredLog(operation = "定期备份mysql数据库")
    public void backupDatabase(){
        String filePath="";
        String dbName="billing";
        String[] cmd=new String[]{"/bin/sh ","-c ","/usr/bin/mysqldump -uroot  -padmin minas >/usr/2.sql "};
        try {
            Process process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
