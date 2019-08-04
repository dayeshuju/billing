package com.daye;

import com.daye.sys.entity.TbYhlx;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {

    @Test
    public void DBtest() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mariadb://www.tdd-nas.cn:3306/daye_billing","daye","daye2019");

        Statement stat = conn.createStatement();

        String sql = "select * from tbYhlx";

        ResultSet rs = stat.executeQuery(sql);

        while(rs.next()){
            TbYhlx yhlx = new TbYhlx();
            yhlx.setId(rs.getLong(1));
            yhlx.setUserType(rs.getString(2));
            yhlx.setTate(rs.getString(3));
            System.out.println(yhlx);
        }

        rs.close();
        stat.close();
        conn.close();

    }
}
