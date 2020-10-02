package hw4;
import  java.util.*;
import java.io.*;

public class WebToPigLatin
{

    public final static String delimiter = "|.&^?!/();:,\"";
    public final static String vowel = "aeiouy";
    public final static String firstVowel = "aeiouAEIOU";

    public static void main(String args[]) throws Exception
    {
        if(args.length != 2)
        {
            System.out.println("Usage: java WebToPigLatin inputFile outputFile");
                   System.out.println(" Must have two command-line parameters");
                   return;
        }



    try
    {
       Reader in = new BufferedReader(new FileReader(args[0]));
        Writer out = new BufferedWriter(new FileWriter(args[1]));
        int intRead;

        boolean shouldIgnore = false;

        String word = "";

        while((intRead = in.read()) != -1)
        {
            char c = (char) intRead;

            if(c == '<' || c == '&') {

                if(!word.equals(""))
                    out.write(translate(word));
                word = "";

                out.write(c);
                shouldIgnore = true;
            }
            else if(c == '>' || c == ';') {
                out.write(c);
                shouldIgnore = false;
            }
            else if (Character.isLetter(c) && !shouldIgnore)
            {
                word += c;
            }
            else if (!word.equals("") && !shouldIgnore) {
                // flush
                {
                    out.write(translate(word));
                }
                word = "";
                out.write(c);
            } else {
                out.write(c);
            }
        }


        out.close();
    }
    catch (Exception e)
    {
        e.printStackTrace();
        return;
    }





    }
    private static boolean isPunctuation(char c)
    {
        return delimiter.contains(String.valueOf(c));
    }
    private static boolean isVowel(char c)
    {
        return vowel.contains(String.valueOf(c));

    }
    private static boolean isNumber(char c)
    {
        return (c >= '0' && c <= '9');
    }
    private static boolean isCapital(char c)
    {
        return (c >= 'A' && c <= 'Z');

    }

    private static boolean isFirstVowel(char c)
    {
        return firstVowel.contains(String.valueOf(c));

    }
    private static boolean isLetter(char c)
    {
        return ( (c >='A' && c <='Z') || (c >='a' && c <='z'));
    }

    private static String translate(String word)
    {

        int index = -1;
        int puncSize = 0;
        char c;
        int capIndex = -1;
        String punctuation = "";
        StringBuilder s = new StringBuilder();
        String number = "";
        boolean loop = true;
        boolean capital = false;
        int isAbbreviation = 0, isSingleLetter = 0;



        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            if(isFirstVowel(word.charAt(0)) && loop == true)
            {

                index = 0;
                loop = false;
                isAbbreviation++;
            }
            if(isCapital(c))
            {
                capIndex = i;
                capital = true;


            }

            if(isPunctuation(c))
            {

                punctuation += c;
                puncSize++;
            }

            if(isNumber(c))
            {
                number += c;
            }


            if (isVowel(c) && i > 0 && loop == true)
            {
                index = i;
                loop = false;
                isAbbreviation++;

            }
            if(isLetter(c))
                isSingleLetter++;
        }
        if(isAbbreviation == 0)
            return word;

       else if(index == -1 && !number.isEmpty())
        {
            return number;
        }
        else if(index == -1 && !punctuation.isEmpty())
        {
            return punctuation;
        }

        else if (index == 0) {
            s.append(word);

            if(!punctuation.isEmpty())
                s.deleteCharAt(s.length()-1);

            return s + "yay" + punctuation;
        }
        else if(isSingleLetter == 1)
        {
            return word;
        }
        else {

            String b = "";
            try {
            b = word.substring(0,index);

            } catch(StringIndexOutOfBoundsException e) {
                System.out.println(e);
            }
             b =  b.toLowerCase();
            String a = word.substring(index);
           a = a.toLowerCase();
            s.append(a+b);

            if(!punctuation.isEmpty())
            {
                for(int i = 1; i <= puncSize; i++)
                    s.deleteCharAt(a.length() - i);
            }



            if(capital)
            {
                char capitalized;
                capitalized = Character.toUpperCase(word.charAt(index));
                s.setCharAt(capIndex,capitalized);

            }

            return s + "ay" + punctuation;
        }
    }



    }


