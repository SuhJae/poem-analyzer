/*
 * [Main.java]
 * This is a program that will get the poetry from the user and try to identify
 * the type of the poem based on the patterns.
 * @ author Jay Suh
 * @ version 1.0 Sep 13, 2023
 */

import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String word = input.nextLine();


        // =============== Identify The Syllable In a Word ===============
        int syllableCount = 0;

//        if (word.length() == 1) { // This is here to prevent out of bound error.
//            syllableCount = 1; // if the word is only one character, it is automatically one syllable.
//        } else {
            // count the number of the vowels.
            boolean lastCharIsVowel = false;

            for (int i = 0; i < word.length(); i++) {
                char currentChar = word.charAt(i); // before checking each Char, the vowel, make it lowercase, so we don't have to check for both case.
                currentChar = Character.toLowerCase(currentChar);

                if ("aeiou".indexOf(currentChar) != -1) { // checks if the current character is a vowel.
                    if (i == word.length() - 1 && currentChar == 'e') { // if the last character is 'e', it is not a syllable.
                        syllableCount--;
                    }
                    if (!lastCharIsVowel) { // if the last character is a vowel, this is dipthong, tripthong ...
                        syllableCount++;
                    }
                    lastCharIsVowel = true;
                } else {
                    lastCharIsVowel = false;
                }
            }

            // check if the word ends with: le, les, and the letter before the “le” is a consonant
            if (word.length() >= 3) {
                String lastThree = word.substring(word.length() - 3); // get last three characters
                char beforeLe = lastThree.charAt(0); // get the letter before 'le'
                if (lastThree.endsWith("le") && "aeiou".indexOf(beforeLe) == -1) {
                    syllableCount++;
                }
            }

            // check if syllable count is 0, then it is 1. for the word that has no vowel or other special cases
            if (syllableCount == 0) {
                syllableCount = 1;
            }
//        }
        System.out.println("The number of syllables in the word " + word + " is " + syllableCount);
        // =============== Identify The Syllable In a Word ===============

        input.close();
    }
}