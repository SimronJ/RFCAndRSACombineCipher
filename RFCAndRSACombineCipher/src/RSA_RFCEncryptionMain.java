
import java.util.Scanner;

// Java 8 example for RSA encryption/decryption.
// Uses strong encryption with 2048 key size.
public class RSA_RFCEncryptionMain
{

	public static void main(String[] args) throws Exception
	{

		Scanner scan = new Scanner(System.in);
		RSA_RFCUtils util = new RSA_RFCUtils();
		
		int DNumRails;
		String dPrivatekey, dData;
		
		while(true)
		{
			System.out.println("Choose One of the following:\n1: Encrypt data\n2: Decrypt Data\n3: Exit");
			int choice = scan.nextInt();
			scan.nextLine();
			switch (choice)
			{
			case 1:
			{
				System.out.println("Enter the text to encrypt(Make sure it's not bigger than 254 bytes):");
				String input = scan.nextLine();

				// To makesure numbers of Rails is not smaller than 2. If it is than it change
				// to 2.
				// Also depending on the length of the text it multiples by .6 to get unique
				// number of rails.
				int numrails = ((int) (input.length() * .6) < 2) ? 2 : (int) (input.length() * .6);

				System.out.println("You Input:" + input + "\nYour Number of Rails are:" + numrails);

				String[] keypairs = util.generateRSAKeyPairWithBase64();
				System.out.println("Your Private Key: " + keypairs[0]);
				System.out.println("Your Public Key: " + keypairs[1]);

				String encryptedRFC_RSA = util.encryptRFC_RSA(input, numrails, keypairs[1]);

				util.decryptRFC_RSA(encryptedRFC_RSA, numrails, keypairs[0]);
				System.out.println("\n------------------------");
				break;
			}
			case 2:
			{
				System.out.println("Enter your private key:");
				dPrivatekey = scan.nextLine();
				System.out.println("Enter your Ciphertext:");
				dData = scan.nextLine();
				System.out.println("Enter number of rail:");
				DNumRails = scan.nextInt();
				
				util.decryptRFC_RSA(dData, DNumRails, dPrivatekey);
				System.out.println("\n------------------------");
				break;
			}
			case 3:
			{
				System.out.println("Exiting the Program.");
				scan.close();
				System.exit(0);
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + choice );
			}
		}
		
		//test data
		//String privatek = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDG5LkPD+H4k64bbRB8gKS2kZ10KdyoEBBlnqmynO7NKDQo6OleTtP8RmLbDAKXO8xlndpVj/fYFbG9s+dQE5JDk0Glhy0BSGgpXElzqnvG7Ik8aVcPDEqjueqeVqmOlfeR6imJPAVCHfy9dXKbP7SuJX9yT2tVwiWmlHs/NyNakFPfsXHhJem8syJi8yCLuGd6CB7WhieTFQhAV87L/mCaOsh2TtBPDQ1ybqCPQ/0byOM+If3epWvX9Zn1OrDz9c30/4+fLkNkbYubwwfkxCDwsGJyEtuGbQKC+3QEVmrQTnwGmPgrzYQONRdMiuqNk0khfn8geDrOkizxn5K1tSzJAgMBAAECggEBAIW7kPshsM1Vb0IdqXIthZMEghxzcVzHwF6zumLQxnjHyZqgURoKYttD8IBcqPlLSfohk4bokLuIisSFTMk4/6XUQ9Wq+IXeS7tTxEezoBp3PSeTpOgHWcCQ58KjfsrS524DRv5xjw+P3jr915uv0CswJ2uTHxbAvlQtjllJF8pyBPVHj/G9zBsjAfwFACUeQLGIEPNVh8In8fpsdwQDBoFa1LZHNQQHN5cEd+NwxTqYanzQ579d7/47jFf+m2JQI0qAYambpNT5k9HEeP4wRhY6P4eZooU/nNqd953DDZrjjNcLSKy7qN2z8QyH9HeFcGfWI8WWFfTvi283E3cDjCECgYEA8SXyM2FOk5zG/Ysuau7KfuFLQSPcckjPHv2ALcvE0BYaJ7Au7eNeaZS6c/gyqZFKvODOOd2q3H+yJ/NPSkawOrbzr2gh5cFsdEIMlJn8CiIWFflbV0W+dE3UI2TWvXLNRZ+xN6Af5OKB1rJakqZp/tuI1Nz1Ji7fADmnW2CrIZ0CgYEA0ySRii1z6gFM0Y1mx+900S+MEE1F9wI21DqLNuTcrgOvGVlKWxN7Q21w+gNvBH1LtK2AWxhJxRrHWg1J+KDpeWLzNTgfC+TYrQp+O8jc6Jt0SHDPKRLhFe1VCLoFWFmPKCRf4caSmTPIREJv0syrsJr+1a9t9cIMYfm04WXkdh0CgYBQidvaTSVxJ2tOQq2+CQKYXaZEmzWE9cw3DXNB2ExjMv6JQs5ppQZjPMkI8lv5Rg0wB+TYbrAh9IQL07cnl3REJ9Y7PwwTMAaIzYZmGweSWtAFhsJ641A4h8XSDFur6iuklTEa4t/EEeJLUkXttKax96A7d5BosD7KVHcQjbiaAQKBgE4caC87M3etdXLMoepzXr8InQNm4uxHiGspIVl0f2Ns/3kcPFLHOUGvmi0T2h1KERjNWv/YU2o0xu08n1H4jYcXoSveEDZBOhuCF0McwwTWhcVK3oiucFDBVUT+NH4D298Y3dPTt2Yv7vW5sk7E4vtZy87XsJFLcXNJgq4kGMnNAoGAASpUAGm88+GkQcgZKTJznkwF/9uMSxlQPuCy51hgXXDe7YBlCvSv/UFt+KOER5G9nIjPwrJ1xtyswZYDwB9/Zm4dPC8M3+Km2+foVSKuP/95nCGghNTOgmyi47mTLM54+iSDPCAiWTB2bvJka2SxxlC72qH5W8c6r7T6mw+VRqM=";
		//String data = "A1zFqvx7VaoDbz7933B/1ohqIcWyb2K1jSJNMNC1fAsbdTQpBwe6ds+YdV6HLlS2SRtusm9moL6ujMJLETQWoNmAGgqhon5/eNj5xjGPwE03m2zZlpzEyZJJ89+rUnjW5QKUfXfagpgnBTPt3iDtHv6NTWcKT076petm+JQf/VzVGZb6xyr9y8+cTyn7QLyBdaeT9KrYWXHor+SjNYnjr8r9OMHwWXCbZxwToIZtDTtDgjzUEtt3qbVf42SEqLeQfw8ZnGjpQffl757gdW8PH73=4bUxSUtpLzjgmbUBQWDbFNKMvpGuremcj0oUBUHpPE=afZx0h3lNzs7E0SFusnxO";
		//util.decryptRFC_RSA(data, 9, privatek);
	}

}