package com.stone.dao.mybatis;

/**
 * @author 赵少泉
 * @date 2015年8月26日 下午12:38:30
 */
public enum Dialect {
	
    mysql, mariadb, sqlite, oracle, hsqldb, postgresql, sqlserver, db2, informix;

    public static Dialect of(String dialect) {
        try{
            Dialect d = Dialect.valueOf(dialect);
            return d;
        }catch(IllegalArgumentException e){
            String dialects = null;
            for(Dialect d : Dialect.values()){
                if(dialects == null){
                    dialects = d.toString();
                }else{
                    dialects += "," + d;
                }
            }
            throw new IllegalArgumentException("Mybatis page plugin params error,value is[" +dialects+ "]");
        }
    }

    public static String[] dialects() {
        Dialect[] dialects = Dialect.values();
        String[] ds = new String[dialects.length];
        for(int i = 0; i < dialects.length; i++){
            ds[i] = dialects[i].toString();
        }
        return ds;
    }

    public static String fromJdbcUrl(String jdbcUrl) {
        String[] dialects = dialects();
        for(String dialect : dialects){
            if(jdbcUrl.indexOf(":" +dialect+ ":") != -1){
                return dialect;
            }
        }
        return null;
    }
}
