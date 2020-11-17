
public class RailFenceCipher
{

	public String getRFCDecryptedData(String data, int rails)
	{
		char[] decrypted = new char[data.length()];
		int n = 0;
		for (int k = 0; k < rails; k++)
		{
			int index = k;
			boolean down = true;
			while (index < data.length())
			{
				// System.out.println(k + " " + index+ " "+ n );
				decrypted[index] = data.charAt(n++);

				if (k == 0 || k == rails - 1)
				{
					index = index + 2 * (rails - 1);
				} else if (down)
				{
					index = index + 2 * (rails - k - 1);
					down = !down;
				} else
				{
					index = index + 2 * k;
					down = !down;
				}
			}
		}
		return new String(decrypted);
	}

	public String getRFCEncryptedData(String data, int numRails)
	{
		char[] encrypted = new char[data.length()];
		int n = 0;

		for (int k = 0; k < numRails; k++)
		{
			int index = k;
			boolean down = true;
			while (index < data.length())
			{
				// System.out.println(k + " " + index+ " "+ n );
				encrypted[n++] = data.charAt(index);

				if (k == 0 || k == numRails - 1)
				{
					index = index + 2 * (numRails - 1);
				} else if (down)
				{
					index = index + 2 * (numRails - k - 1);
					down = !down;
				} else
				{
					index = index + 2 * k;
					down = !down;
				}
			}
		}
		return new String(encrypted);
	}
}
