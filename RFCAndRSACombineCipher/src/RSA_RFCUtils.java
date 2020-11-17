
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSA_RFCUtils extends RailFenceCipher
{

	public PublicKey getPublicKey(String base64PublicKey)
	{
		PublicKey publicKey = null;
		try
		{
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return publicKey;
	}

	public PrivateKey getPrivateKey(String base64PrivateKey)
	{
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try
		{
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		try
		{
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return privateKey;
	}

	public String encryptRSA(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
			InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
	}

	public String decryptRSA(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public String decryptRSA(String data, String base64PrivateKey) throws IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		return decryptRSA(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
	}

	public String encryptRFC_RSA(String encrypteddata, int numrails, String publicKeyEncode) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		String rsa_encryptedText = encryptRSA(encrypteddata, publicKeyEncode);
		// System.out.println("RSA Encrypted:" + rsa_encryptedText);

		String rfcE = getRFCEncryptedData(rsa_encryptedText, numrails);
		System.out.println("RFC_RSA Encrypted:" + rfcE);

		return rfcE;
	}

	public String decryptRFC_RSA(String decryptdata, int numrails, String privateKeyEncode) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		String rfcD = getRFCDecryptedData(decryptdata, numrails);
		// System.out.println("RFC Decrypted:" + rfcD);

		String rsa_descryptedText = decryptRSA(rfcD, privateKeyEncode);
		System.out.println("RFC_RSA Decrypted:" + rsa_descryptedText);

		return rsa_descryptedText;
	}

	// Get RSA keys. Uses key size of 2048.
	private Map<String, Object> getRSAKeys() throws Exception
	{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("private", privateKey);
		keys.put("public", publicKey);
		return keys;
	}

	public String[] generateRSAKeyPairWithBase64() throws Exception
	{
		String[] keypairs = new String[2];
		// Generate public and private keys using RSA
		Map<String, Object> keys = getRSAKeys();

		PrivateKey privateKey = (PrivateKey) keys.get("private");
		String privateKeyEncode = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		keypairs[0] = privateKeyEncode;
		// System.out.println("Private Key: " + keypairs[0]);

		PublicKey publicKey = (PublicKey) keys.get("public");
		String publicKeyEncode = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		keypairs[1] = publicKeyEncode;
		// System.out.println("Public Key: " + keypairs[1]);

		return keypairs;
	}

}