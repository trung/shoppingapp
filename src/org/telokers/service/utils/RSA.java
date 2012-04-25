package org.telokers.service.utils;
import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {
	
	private final static BigInteger one      = new BigInteger("1");
	private final static SecureRandom random = new SecureRandom();
	
	
	private static BigInteger privateKey;
	private static BigInteger publicKey;
	private static BigInteger modulus;
	
	RSA(int N) {
	      BigInteger p = BigInteger.probablePrime(N/2, random);
	      BigInteger q = BigInteger.probablePrime(N/2, random);
	      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

	      modulus    = p.multiply(q);                                  
	      publicKey  = new BigInteger("65537");     
	      privateKey = publicKey.modInverse(phi);
	   }
	public static BigInteger getPrivateKey()
	{
		return privateKey;
	}
	public static BigInteger getPublicKey()
	{
		return publicKey;
	}
	
	public static BigInteger getModules()
	{
		return modulus;
	}
	
	// input PAN Number in 16 digit format (without -)
	public static BigInteger encrypt(BigInteger message) {
	     
	return message.modPow(publicKey, modulus);
	   
	}

	public static BigInteger decrypt(BigInteger encrypted) {
	
		return encrypted.modPow(privateKey, modulus);
	}
}
