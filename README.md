# Decrypt_Message

# AES-128
This project was made as an implementation of the Advanced Encryption Standard (AES) cipher algorithm which is one of the most robust and used currently in symmetric key cryptography, because of the security and ease of implementation that provides.

# Description 
The DECRYPT program was used an AES-128 bit algorithm with ECB mode to decrypt any file containing the encrypted message with the key file of the encryption message to be decrypted.
The secretKey variable was constructed from a byte array for a key that can be represented as a byte array which is used with an AES algorithm.
For decrypt method, it is taking the ciphertext and the secretKey as input after they have been converted to bytes array then a decrypted these data is return in a string of a decrypted text, then writes the outcome of the decryption process into an external file created through a file output stream to write the message.

FINDK program used the AES-128-bit algorithm with ECB mode to decrypt any file containing the encrypted message with the part of the secretKey file where only the first 96 bits of the secretKey are known out of 128 bits.
Brute force is used to decrypt text, the last 4 bytes of the secretKey are unknown as the missing part of the secretKey is searched using the natural order, the partial key is combined with the guess of the missing part of the secretKey and then the decryption process begins as in DECRYPT program. If the message begins with "Salam" then the encryption process is successfully.

# Running Test
The running time in both programs varies depending on the secretKey status, in the FINDK program, brute-force is used to searches for 4 unknown bytes in the secretKey where the search is done using natural order as it takes longer than the DECRYPT program.
