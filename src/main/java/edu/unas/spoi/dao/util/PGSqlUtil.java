/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.dao.util;

/**
 * The type Pg sql util.
 *
 * @author Danny
 */
public class PGSqlUtil {
    
    private static JDBCPostGresSQL jdbcPostGresSQL;

    /**
     * Gets jdbc post gres sql.
     *
     * @return the jdbc post gres sql
     */
    public static JDBCPostGresSQL getJdbcPostGresSQL() {
        if(jdbcPostGresSQL == null ) {
            jdbcPostGresSQL = new JDBCPostGresSQL();
        }
        return jdbcPostGresSQL;
    }

    /**
     * Sets jdbc post gres sql.
     *
     * @param jdbcPostGresSQL the jdbc post gres sql
     */
    public static void setJdbcPostGresSQL(JDBCPostGresSQL jdbcPostGresSQL) {
        PGSqlUtil.jdbcPostGresSQL = jdbcPostGresSQL;
    }
    
}
