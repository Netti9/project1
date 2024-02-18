package ua.javarush.caesar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import static ua.javarush.caesar.Alphabet.ALPHABET_FULL;
import static ua.javarush.caesar.Alphabet.ALPHABET_FULL_SIZE;

public class CaesarCipher {
    private static String encode(String encryptTextBefore, int key) {

        char[] symbolsCharBefore = encryptTextBefore.toCharArray();
        char[] symbolsCharAfter = new char[symbolsCharBefore.length];
        boolean isPresence = false;
        for (int i = 0; i < symbolsCharBefore.length; i++) {
            for (int j = 0; j < ALPHABET_FULL_SIZE; j++) {
                if (symbolsCharBefore[i] == ALPHABET_FULL[j]) {
                    isPresence = true;
                }
                if (isPresence) {
                    symbolsCharAfter[i] = ALPHABET_FULL[(j + key) % ALPHABET_FULL_SIZE];
                    isPresence = false;
                }
            }
        }
        return new String(symbolsCharAfter);
    }

    private static String decode(String decryptTextBefore, int key) {
        char[] symbolsCharBefore1 = decryptTextBefore.toCharArray();
        char[] symbolsCharAfter1 = new char[symbolsCharBefore1.length];
        boolean isPresence = false;
        for (int i = 0; i < symbolsCharBefore1.length; i++) {
            for (int j = 0; j < ALPHABET_FULL_SIZE; j++) {
                if (symbolsCharBefore1[i] == ALPHABET_FULL[j]) {
                    isPresence = true;
                }
                if (isPresence) {
                    if ((j - key) > 0) {
                        symbolsCharAfter1[i] = ALPHABET_FULL[(j - key) % ALPHABET_FULL_SIZE];
                    } else {
                        symbolsCharAfter1[i] = ALPHABET_FULL[(ALPHABET_FULL_SIZE + (j - key)) % ALPHABET_FULL_SIZE];
                    }
                    isPresence = false;
                }
            }
        }

        return new String(symbolsCharAfter1);
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the path to encrypt the file:");
            Path inputFile = Path.of(reader.readLine());

            System.out.println("Enter the text encryption key:");
            int key = Integer.parseInt(reader.readLine());

            System.out.println("Enter the path to create the file and write the ciphertext:");
            Path outputFile = Path.of(reader.readLine());

            String inputText = Files.readString(inputFile);
            String encryptedText = encode(inputText, key);
            Files.writeString(outputFile, encryptedText);

            System.out.println("The text was successfully encrypted and written to a file.");

            System.out.println("Enter the path to create the file and record the decrypted text:");
            Path decryptFile = Path.of(reader.readLine());

            String decryptedText = decode(encryptedText, key);
            Files.writeString(decryptFile, decryptedText);

            System.out.println("The text was successfully decrypted and written to a file.");
        } catch (IOException e) {
            System.err.println("Error when working with files: " + e.getMessage());
        }
    }
}

