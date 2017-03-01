package com.stone.commons;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class CipherUtilTest {
	String content = "DES";

	@Test
	public void testMD5Encrypt() throws Exception {
		byte[] md5b1 = CipherUtil.MD5Encrypt(content);
		byte[] md5b2 = CipherUtil.MD5Encrypt(content);
		BigInteger md5 = new BigInteger(md5b1);
		System.out.println(content + "-md5 = " + md5.toString());
		System.out.println(content + "-md5 = " + md5.toString(16));
		System.out.println(content + "-md5 = " + md5.toString(32));
		assertArrayEquals(md5b1, md5b2);
	}

	@Test
	public void testBASE64Encrypt() throws Exception {
		String base64Encrypt = CipherUtil.BASE64Encrypt(content.getBytes());
		System.out.println(content + ":base64Encrypt:" + base64Encrypt);
		byte[] base64b1 = CipherUtil.BASE64Decrypt(base64Encrypt);
		assertEquals(content, new String(base64b1));
	}

	@Test
	public void testSHAEncrypt() throws Exception {
		byte[] shab1 = CipherUtil.SHAEncrypt(content);
		BigInteger sha = new BigInteger(shab1);
		System.out.println(content + "-sha = " + sha.toString());
		System.out.println(content + "-sha = " + sha.toString(16));
		System.out.println(content + "-sha = " + sha.toString(32));
	}

	@Test
	public void testHMACEncrypt() throws Exception {
		String key = CipherUtil.generateMACKey();
		System.out.println("key=" + key);
		byte[] hmacb1 = CipherUtil.HMACEncrypt(content.getBytes(), key);
		byte[] hmacb2 = CipherUtil.HMACEncrypt(content.getBytes(), key);
		assertArrayEquals(hmacb1, hmacb2);
		BigInteger mac = new BigInteger(CipherUtil.HMACEncrypt(
				content.getBytes(), content));
		System.out.println("mac = " + mac.toString(16));
	}

	@Test
	public void testDESEncrypt() throws Exception {
		String seed = "forever";
		String key = CipherUtil.generateDESKey(CipherUtil.BASE64Encrypt(seed
				.getBytes()));
		System.out.println("content=" + content);
		System.out.println("key=" + key);
		byte[] desb1 = CipherUtil.DESEncrypt(content.getBytes(), key);
		String inputData = CipherUtil.BASE64Encrypt(desb1);
		System.out.println("DESEncrypt=" + inputData);
		byte[] outputData = CipherUtil.DESDecrypt(desb1, key);
		System.out.println("DESDecrypt=" + new String(outputData));

	}

	@Test
	public void testPBEEncrypt() throws Exception {
		System.out.println("content:" + content);
		byte[] salt = CipherUtil.generatePBESalt();
		String password = "cj861105";
		System.out.println("password: " + password);
		byte[] input = content.getBytes();
		byte[] data = CipherUtil.PBEEncrypt(input, password, salt);
		System.out.println("PBEEncrypt = " + CipherUtil.BASE64Encrypt(data));
		byte[] output = CipherUtil.PBEDecrypt(data, password, salt);
		String outputStr = new String(output);
		System.out.println("PBEDecrypt: " + outputStr);
		assertEquals(content, outputStr);
	}

}
