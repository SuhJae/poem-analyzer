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
        // =============== Initialize variables ===============
        Scanner input = new Scanner(System.in);
        String line = "";
        String syllablePerLine = "";
        int totalSyllable = 0;
        int lineCount = 0;

        // =============== Get multiple lines of input ===============

        while (true) {
            line = input.nextLine();
            if (line.equals("quit")) {
                break;
            }
            lineCount++;
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
            syllablePerLine += syllableCountInSentence + "-";
            totalSyllable += syllableCountInSentence;
        }

        // =============== Print out the result ===============

        System.out.println("Total lines in the poem: " + lineCount);
        System.out.println("Total syllables in the poem: " + totalSyllable);
        System.out.println("Syllables per line: " + syllablePerLine.substring(0, syllablePerLine.length() - 1));

        // =============== prediction ===============

        String haikuLines = "5-7-5-";
        String tankaLines = "5-7-5-7-7-";
        String sijoLines = "14-16-14-";
        String cinquainLines = "2-4-6-8-2-";
        String nonetLines = "9-8-7-6-5-4-3-2-1-";

        if (lineCount == 3) { // check for haiku vs sijo
            double haikuPercentageError = 0;
            double sijoPercentageError = 0;

            int thisLineSyllable = Integer.parseInt(syllablePerLine.substring(0, syllablePerLine.indexOf("-")));
            int thisHaikuLineSyllable = Integer.parseInt(haikuLines.substring(0, haikuLines.indexOf("-")));
            int thissijoLineSyllable = Integer.parseInt(sijoLines.substring(0, sijoLines.indexOf("-")));

            // Check for haiku vs sijo by comparing the percentage error of syllable count each line.
            for (int i = 0; i < 2; i++) {
                haikuPercentageError += Math.abs((double) (thisLineSyllable - thisHaikuLineSyllable) / thisHaikuLineSyllable * 100);
                sijoPercentageError += Math.abs((double) (thisLineSyllable - thissijoLineSyllable) / thissijoLineSyllable * 100);

                syllablePerLine = syllablePerLine.substring(syllablePerLine.indexOf("-") + 1);
                haikuLines = haikuLines.substring(haikuLines.indexOf("-") + 1);
                sijoLines = sijoLines.substring(sijoLines.indexOf("-") + 1);

                thisLineSyllable = Integer.parseInt(syllablePerLine.substring(0, syllablePerLine.indexOf("-")));
                thisHaikuLineSyllable = Integer.parseInt(haikuLines.substring(0, haikuLines.indexOf("-")));
                thissijoLineSyllable = Integer.parseInt(sijoLines.substring(0, sijoLines.indexOf("-")));
            }

            haikuPercentageError = (double) Math.round((haikuPercentageError / 3 * 100)) / 100;
            sijoPercentageError = (double) Math.round((sijoPercentageError / 3 * 100)) / 100;

            System.out.println("Haiku percentage error: " + haikuPercentageError + "%");
            System.out.println("sijo percentage error: " + sijoPercentageError + "%\n");

            // pridiect the type of poem based on the percentage error.
            if (haikuPercentageError < sijoPercentageError) {
                System.out.println("This is most likely a Haiku.");
            } else {
                System.out.println("This is most likely a Sanjo.");
            }
        } else if (lineCount == 5) { // check for tanka vs cinquain
            double tankaPercentageError = 0;
            double cinquainPercentageError = 0;

            int thisLineSyllable = Integer.parseInt(syllablePerLine.substring(0, syllablePerLine.indexOf("-")));
            int thisTankaLineSyllable = Integer.parseInt(tankaLines.substring(0, tankaLines.indexOf("-")));
            int thisCinquainLineSyllable = Integer.parseInt(cinquainLines.substring(0, cinquainLines.indexOf("-")));

            // Check for tanka vs cinquain by comparing the percentage error of syllable count each line.
            for (int i = 0; i < 4; i++) {
                tankaPercentageError += Math.abs((double) (thisLineSyllable - thisTankaLineSyllable) / thisTankaLineSyllable * 100);
                cinquainPercentageError += Math.abs((double) (thisLineSyllable - thisCinquainLineSyllable) / thisCinquainLineSyllable * 100);

                syllablePerLine = syllablePerLine.substring(syllablePerLine.indexOf("-") + 1);
                tankaLines = tankaLines.substring(tankaLines.indexOf("-") + 1);
                cinquainLines = cinquainLines.substring(cinquainLines.indexOf("-") + 1);

                thisLineSyllable = Integer.parseInt(syllablePerLine.substring(0, syllablePerLine.indexOf("-")));
                thisTankaLineSyllable = Integer.parseInt(tankaLines.substring(0, tankaLines.indexOf("-")));
                thisCinquainLineSyllable = Integer.parseInt(cinquainLines.substring(0, cinquainLines.indexOf("-")));
            }

            tankaPercentageError = (double) Math.round((tankaPercentageError / 5 * 100)) / 100;
            cinquainPercentageError = (double) Math.round((cinquainPercentageError / 5 * 100)) / 100;

            System.out.println("Tanka percentage error: " + tankaPercentageError + "%");
            System.out.println("Cinquain percentage error: " + cinquainPercentageError + "%\n");

            // pridiect the type of poem based on the percentage error.
            if (tankaPercentageError < cinquainPercentageError) {
                System.out.println("This is most likely a Tanka.");
            } else {
                System.out.println("This is most likely a Cinquain.");
            }
        } else if (lineCount == 9) { // since nonet is the only poem with 9 lines, we don't have to compare it with other poems.
            System.out.println("This is most likely a Nonet.");
        } else {
            System.out.println("Unknown type of poem.");
        }

        input.close();
    }
}