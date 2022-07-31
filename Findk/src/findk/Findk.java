package findk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Findk {

    private static SecretKey secretKey;
    public static void main(String[] args) throws IOException {

        //we should create the File class object by passing the path of ciphertext file and key file 
        File ciphertext2File = new File("ciphertext2.dat");
        File partialKeyFile = new File("partial-key.dat");
        
        // Define array of byte to represent the ciphertext and key in byte 
        byte cipherTextData[] = new byte[(int) ciphertext2File.length()];
        byte partialKeyData[] = new byte[(int) partialKeyFile.length()];

        // get input stream Files
        FileInputStream ciphertext = new FileInputStream(ciphertext2File);
        FileInputStream myKey = new FileInputStream(partialKeyFile);
          
        //Read the ciphertext file and key file and represent them as string to be printed
        ciphertext.read(cipherTextData);
        String s = new String(cipherTextData);
        System.out.println("cipherText: " + s);

        myKey.read(partialKeyData);
        String s1 = new String(partialKeyData);
        System.out.println("Partial Key: " + s1);


        long startTime = System.currentTimeMillis();
        guessKey(partialKeyData, cipherTextData);
        long endTime = System.currentTimeMillis();
        System.out.println("\nThe Decryption time is: " + ((endTime - startTime) / 1000F) + " seconds");

    }

    private static void guessKey(byte[] partialKeyData, byte[] cipherTextData) {

        boolean check = false; // variable to check if the key is found or not
        
        // 4 loop to find all possibilities of 4 bytes
        for (int w = 0; w < 256; w++) {
            for (int x = 0; x < 256; x++) {
                for (int y = 0; y < 256; y++) {
                    for (int z = 0; z < 256; z++) {
                        byte[] guessedKey = {(byte) w, (byte) x, (byte) y, (byte) z};

                        // Combined the partial key with guessing the missing part in the key
                        byte[] key = concatenate(partialKeyData, guessedKey);
          
                        //"secretKey" is a variable in cryptography that is used with an AES algorithm to encrypt the ciphertext
                        secretKey = new SecretKeySpec(key, "AES");

                        //Call "decrypt" method and send the ciphertext and secretKey to decrypt the ciphertext
                        String decryptedString = Findk.decrypt(cipherTextData, secretKey);

                        //check if the first word in decryption start with "Salam", then the key is correct and decryption process is successful
                        if (decryptedString.startsWith("Salam")) {
                            check = true;
                            decryptedString = decryptedString.replaceAll("", "");// to remove unwanted characters
                            System.out.println("\nThe missing part of key found of " + guessedKey.length + " bytes is: " + bytesToHex(guessedKey)
                                    + "\nKey length " + key.length + " byte is: " + bytesToHex(key));
                            System.out.println("\nPlainText: " + decryptedString);

                            //writes the outcome of the decryption process into an external file
                            writeFile(decryptedString);
                            return;
                        }
                    }
                    if (check) {
                        return;
                    }
                }
                if (check) {
                    return;
                }
            }
            if (check) {
                return;
            }
        }
    }

    private static byte[] concatenate(byte[] partialKeyData, byte[] guessedKey) {
        byte[] combined = new byte[partialKeyData.length + guessedKey.length];
        for (int i = 0; i < combined.length; ++i) {
            combined[i] = i < partialKeyData.length ? partialKeyData[i] : guessedKey[i - partialKeyData.length];
        }
        return combined;
    }

    private static String decrypt(byte[] cipherTextData, SecretKey secretKey) {

        try {
            //create an instance of the Cipher class
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

            // Initialize the same cipher for decrypting data
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // start processing data and return the decryption as string
            return (new String(cipher.doFinal(cipherTextData)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    private static void writeFile(String decryptedString) {
        // Generated a file output stream to write the Decryption msg to output File
        try {
            
            File plaintextFile = new File("PlainText2.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(plaintextFile);
            fileOutputStream.write(decryptedString.getBytes());
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
