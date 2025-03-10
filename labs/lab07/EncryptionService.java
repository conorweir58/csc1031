import java.util.Base64;

interface EncryptionStrategy
{
    String encrypt(String text);
}

class CaesarCipherEncryption implements EncryptionStrategy
{
    private final int shift;

    public CaesarCipherEncryption(int shift) {
        this.shift = shift;
    }

    @Override
    public String encrypt(String text)
    {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray())
        {
            if (Character.isLetter(ch))
            {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + this.shift) % 26 + base);
            }
            result.append(ch);
        }

        return result.toString();
    }
}

class Base64Encryption implements EncryptionStrategy
{
    @Override
    public String encrypt(String text)
    {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
}

class XOREncryption implements EncryptionStrategy
{
    private final int key;

    public XOREncryption(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String text)
    {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            result.append((char) (ch ^ this.key));
        }
        return result.toString();
    }
}

class ReverseStringEncryption implements EncryptionStrategy
{
    @Override
    public String encrypt(String text)
    {
        StringBuilder result = new StringBuilder(text);

        result.reverse();
        return result.toString();
    }
}

class DuplicateCharacterEncryption implements EncryptionStrategy
{
    @Override
    public String encrypt(String text)
    {
        StringBuilder result = new StringBuilder();

        for(char c : text.toCharArray())
        {
            result.append(c);
            result.append(c);
        }

        return result.toString();
    }
}

class EncryptionService {
    private EncryptionStrategy encryptionStrategy;

    public void setEncryptionStrategy(EncryptionStrategy strategy)
    {
        this.encryptionStrategy = strategy;
    }

    public String encrypt(String text)
    {
        return encryptionStrategy.encrypt(text);
    }
}

// class Main {
//     // public static void main(String[] args) {
//     //   // Create an encryption service that will handle different encryption strategies
//     //   EncryptionService service = new EncryptionService();

//     //   // Create a Caesar Cipher encryption strategy with a shift of 3
//     //   EncryptionStrategy caesar = new CaesarCipherEncryption(3);

//     //   // Set the encryption strategy to Caesar Cipher
//     //   service.setEncryptionStrategy(caesar);

//     //   // Encrypt three different strings using the same Caesar Cipher strategy
//     //   System.out.println(service.encrypt("HelloWorld"));       // Output: Encrypted text with shift 3
//     //   System.out.println(service.encrypt("StrategyPattern")); // Output: Encrypted text with shift 3
//     //   System.out.println(service.encrypt("EncryptionLab"));   // Output: Encrypted text with shift 3

//     //   // Change the encryption strategy to Reverse String Encryption
//     //   service.setEncryptionStrategy(new ReverseStringEncryption());

//     //   // Encrypt a different string using the Reverse String strategy
//     //   System.out.println(service.encrypt("Password")); // Output: "drowssaP" (reversed)
//     //   }

//     }