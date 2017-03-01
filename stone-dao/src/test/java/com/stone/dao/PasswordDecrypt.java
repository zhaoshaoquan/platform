package com.stone.dao;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * 对数据库密码进行加密解密操作
 * @author 赵少泉
 * @date 2015年9月15日 下午2:00:25
 */
public class PasswordDecrypt{
	public static void main(String[] args){
		try{
			//加密
			System.out.println(ConfigTools.encrypt("password"));
			
			//解密
			System.out.println(ConfigTools.decrypt("V1YGs87ezBBN+Nr0ivTre9tqte/5QVeygWmmseN0rlb4ntkS23IrNCZ3Ien2TdjdW1RbeTwglQWtCgr6fKqALQ=="));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
