package org.telokers.service.utils;
import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {
	
	private final static BigInteger one      = new BigInteger("1");
	private final static SecureRandom random = new SecureRandom();
	private static final int N = 100;
	
	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;
	
	public RSA() {
	      BigInteger p = BigInteger.probablePrime(N/2, random);
	      BigInteger q = BigInteger.probablePrime(N/2, random);
	      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

	      modulus    = p.multiply(q);                                  
	      publicKey  = new BigInteger("65537");     
	      privateKey = publicKey.modInverse(phi);
	   }
	public BigInteger getPrivateKey()
	{
		return privateKey;
	}
	public BigInteger getPublicKey()
	{
		return publicKey;
	}
	
	public BigInteger getModules()
	{
		return modulus;
	}
	
	// input PAN Number in 16 digit format (without -)
	public BigInteger encrypt(BigInteger message) {
	     return message.modPow(publicKey, modulus);
	}

	public BigInteger decrypt(BigInteger encrypted, String encryptKeyString) {
		if (encryptKeyString.length() > 0){
			String[] split = encryptKeyString.split("#");
			BigInteger privateKey = new BigInteger(split[0]);
			BigInteger modulus = new BigInteger(split[1]);
			return encrypted.modPow(privateKey, modulus);
		}
		return null;
	}
	
	public String getComboString(){
		/*System.out.println(privateKey.toString());
		System.out.println(modulus.toString());*/
		
		return privateKey.toString() + "#" + modulus.toString();
	}
	
	public static void main(String[] args){
		RSA rsa = new RSA();
		
		String encryptKey = rsa.getComboString();
		
		System.out.println(encryptKey.length());
		
		String cardNo = "4119110031238673";
		
		String encryptedCardNo = (rsa.encrypt(new BigInteger(cardNo))).toString();
		String decryptedCardNo = (rsa.decrypt(new BigInteger(encryptedCardNo), encryptKey)).toString();

		System.out.println(encryptKey);
		System.out.println(encryptedCardNo);
		System.out.println(decryptedCardNo);
		
	}
	
}
