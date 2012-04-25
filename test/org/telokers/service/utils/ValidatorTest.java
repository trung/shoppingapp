/**
 * 
 */
package org.telokers.service.utils;
import java.io.*;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author User
 *
 */
public class ValidatorTest {
	@Test
	public void test_xxx() {
		int ret = Validator.isValidPW("admin","pass33s");
		System.out.println(ret);
		Assert.assertEquals(3, ret);
	}
}
