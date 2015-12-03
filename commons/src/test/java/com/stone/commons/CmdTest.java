package com.stone.commons;

import java.io.IOException;

import org.junit.Test;

public class CmdTest {
	Process process;

	@Test
	public void testMaven() throws Exception {
		try {
			process = Runtime.getRuntime().exec("mvn.bat -version");
			Thread.UncaughtExceptionHandler eh = new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.err.println("执行deploy:deploy-file命令时出现错误：" + e.getMessage());
					destroyProcess();
				}
			};
			Thread t1 = new ProcessInputStream(process.getInputStream());
			t1.setUncaughtExceptionHandler(eh);
			t1.start();
			Thread t2 = new ProcessInputStream(process.getErrorStream());
			t2.setUncaughtExceptionHandler(eh);
			t2.start();
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		} finally {
			destroyProcess();
		}
	}

	public void destroyProcess() {
		if (process != null) {
			try {
				process.waitFor();
				int i = process.exitValue();
				if (i == 0) {
					System.out.println("执行成功");
				} else {
					System.out.println("执行失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("----------destroy Process-----------");
				process.destroy();
			}
		}
	}

}
