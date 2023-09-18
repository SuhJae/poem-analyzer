/*
 * [Main.java]
 * This is a program that will get the poetry from the user and try to identify
 * the type of the poem based on the patterns.
 * @ author Jay Suh
 * @ version 1.0 Sep 13, 2023
 */

import java.util.Objects;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String line = input.nextLine();

        // =============== Identify The Syllable In a full sentence ===============
        int syllableCountInSentence = 0;
        String word = "";

        while (!line.isEmpty()) {
            int spaceIndex = line.indexOf(" ");

            if (spaceIndex != -1) {
                word = line.substring(0, spaceIndex);
                line = line.substring(spaceIndex + 1);
            } else {
                word = line;
                line = "";
            }


            // =============== Identify The Syllable In a Word ===============
            int syllableCount = 0;

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
                char beforeSuffix = lastThree.charAt(0); // get the letter before two last characters
                if (lastThree.endsWith("le") && "aeiou".indexOf(beforeSuffix) == -1) {
                    syllableCount++;
                }
                // check if it ends with ed. If so, check if before ed, t or d. If not, subtract 1 from the syllable count.
                if (lastThree.endsWith("ed") && ("td".indexOf(beforeSuffix) == -1)) {
                    syllableCount--;
                }

            }

            // check if syllable count is 0, then it is 1. for the word that has no vowel or other special cases
            if (syllableCount == 0) {
                syllableCount = 1;
            }
            // ============================================================================

            syllableCountInSentence += syllableCount;
        }

        System.out.println(syllableCountInSentence);

        input.close();
    }
}