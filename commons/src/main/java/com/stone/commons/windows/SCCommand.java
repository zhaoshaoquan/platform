package com.stone.commons.windows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.stone.commons.ProcessInputStream;

public class SCCommand {
	
	public static final String STATE = "STATE";
	public static final String START_TYPE = "START_TYPE";
	public static final String DISABLED ="4   DISABLED";
	public static final Object STOPPED = "1  STOPPED";
	public static final Object RUNNING = "4  RUNNING";
	public static final Object START_PENDING = "2  START_PENDING";
	
	public Map<String, String> qc(String serverName) {
		return exec("sc qc " + serverName);
	}
	
	/**
	 * 检查指定的服务是否被禁用
	 * @param serverName 服务名称
	 * @return true 禁用 false 没有禁用
	 * @throws Exception
	 */
	public boolean checkServerDisabled(String serverName) throws Exception {
		Map<String,String> map = qc(serverName);
		String stateType = map.get(SCCommand.START_TYPE);
		return stateType==null || stateType.equals(DISABLED);
	}
	
	/**
	 * 检查指定的服务是否存在
	 * @param serverName 服务名称
	 * @return true 存在 false 没有该服务
	 * @throws Exception
	 */
	public boolean checkServerExist(String serverName) throws Exception {
		Map<String,String> map = query(serverName);
		String state = map.get(STATE);
		return state!=null;
	}
	
	/**
	 * 检查指定的服务是否在运行
	 * @param serverName 服务名称
	 * @return true 运行中 false 没有该服务或者没有运行
	 * @throws Exception
	 */
	public boolean checkServerRun(String serverName) throws Exception {
		Map<String,String> map = query(serverName);
		String state = map.get(STATE);
		return state!=null && state.equals(RUNNING);
	}
	
	/**
	 * 检查指定的服务是否停止
	 * @param serverName 服务名称
	 * @return true 停止 false 没有该服务
	 * @throws Exception
	 */
	public boolean checkServerStop(String serverName) throws Exception {
		Map<String,String> map = query(serverName);
		String state = map.get(STATE);
		return state!=null && state.equals(STOPPED);
	}
	
	public Map<String, String> query(String serverName) {
		return exec("sc query " + serverName);
	}
	
	public Map<String, String> start(String serverName) {
		return exec("sc start " + serverName);
	}

	public boolean delete(String serverName) {
		exec("sc delete " + serverName);
		return true;
	}

	public Map<String, String> stop(String serverName) {
		return exec("sc stop " + serverName);
	}
	
	protected Map<String, String> exec(String cmd) {
		Process p = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			p = Runtime.getRuntime().exec(cmd);
			new ProcessInputStream(p.getErrorStream()).start();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream(), "GBK"));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					String[] item = line.split(":");
					if (item.length == 2) {
						map.put(item[0].trim(), item[1].trim());
					}
				}
			}
		} catch (Exception e) {
			System.err.println("exec " + cmd + " failure：" + e.getMessage());
		} finally {
			destroyProcess(p);
		}
		return map;
	}


	public void destroyProcess(Process p) {
		if (p != null) {
			try {
				p.waitFor();
			} catch (Exception e) {
				System.err.println("exec Process waitFor failure："
						+ e.getMessage());
			} finally {
				p.destroy();
			}
		}
	}

}
