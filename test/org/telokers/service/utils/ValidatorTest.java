/**
 * 
 */
package org.telokers.service.utils;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import junit.framework.Assert;

import org.junit.Test;

/**
 * @author User
 *
 */
public class ValidatorTest {
	@Test
	public void test_xxx() {
		boolean ret = Validator.isAlphabet("huynh Thien Tam");
		System.out.println(ret);
		Assert.assertTrue(ret);
	}
	@Test
	public void test_yyy() {
		RSA r=new RSA(100);
		System.out.println("modules  " +r.getModules());
		System.out.println("private key  " +r.getPrivateKey());
		System.out.println("public key " +r.getPublicKey());
		
		BigInteger cc= new BigInteger("4444444444444444");
		BigInteger cc_encrypted= r.encrypt(cc);
		System.out.println(cc_encrypted);
		System.out.println(r.decrypt(cc_encrypted));
	}
}
