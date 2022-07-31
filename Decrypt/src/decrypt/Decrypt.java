package decrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {

    private static SecretKey secretKey;

    public static void main(String[] args) {
        try {

            //we should create the File class object by passing the path of ciphertext file and key file 
            File ciphertextFile = new File("ciphertext.dat");
            File keyFile = new File("key.dat");

            // Define array of byte to represent the ciphertext and key in byte 
            byte cipherTextData[] = new byte[(int) ciphertextFile.length()];
            byte KeyData[] = new byte[(int) keyFile.length()];

            // get input stream Files
            FileInputStream ciphertext = new FileInputStream(ciphertextFile);
            FileInputStream myKey = new FileInputStream(keyFile);

            //Read the ciphertext file and key file and represent them as string to be printed
            ciphertext.read(cipherTextData);
            String cipher = new String(cipherTextData);
            System.out.println("cipherText: \n" + cipher);

            myKey.read(KeyData);
            String key = new String(KeyData);
            System.out.println("\nKey: \n" + key);

            //"secretKey" is a variable in cryptography that is used with an AES algorithm to encrypt the ciphertext
            secretKey = new SecretKeySpec(KeyData, "AES");

            //Call "decrypt" method and send the ciphertext and secretKey to decrypt the ciphertext
            String decryptedString = Decrypt.decrypt(cipherTextData, secretKey);
            System.out.println("\nPlaintext:\n" + decryptedString);

            //writes the outcome of the decryption process into an external file
            writeFile(decryptedString);
        } catch (Exception e) {
            System.out.print(e);
        }
        System.exit(0);
    }

    public static String decrypt(byte[] cipherTextData, SecretKey secretKey) {

        try {
            //create an instance of the Cipher class
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // Initialize the same cipher for decrypting data
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // start processing data and return the decryption as string
            return (new String(cipher.doFinal(cipherTextData)));

        } catch (Exception e) {
            System.out.println("Error while decrypting:\n" + e.toString());
        }
        return null;
    }

    private static void writeFile(String decryptedString) {
        // Generated a file output stream to write the Decryption msg to output File
        try {

            File plaintextFile = new File("PlainText.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(plaintextFile);
            fileOutputStream.write(decryptedString.getBytes());

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
