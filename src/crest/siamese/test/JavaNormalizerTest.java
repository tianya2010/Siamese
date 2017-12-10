package crest.siamese.test;

import crest.siamese.helpers.JavaNormalizer;
import crest.siamese.helpers.JavaTokenizer;
import crest.siamese.helpers.nGramGenerator;
import crest.siamese.settings.Settings;
import crest.siamese.settings.NormalizerMode;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * Created by Chaiyong on 7/27/16.
 */
public class JavaNormalizerTest {

    @org.junit.Test
    public void checkTokenizationFromString() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString(
                "public static void main ( String[] args ) { String inpstring = \"hello\";");

        String[] expectedTokens = {"public", "static", "void", "main", "(", "String", "[", "]"
                , "args", ")", "{", "String", "inpstring", "=", "\"hello\"", ";"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
            assertEquals(expectedTokens[i], tokens.get(i));
        }
    }

    @org.junit.Test
    public void checkSpecialCharacters() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString(
                "!= += ++ -- *= /= <= >= == () {}");

        String[] expectedTokens = {"!=", "+=", "++", "--", "*=", "/=", "<=", ">=", "==", "(", ")", "{", "}"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + "|");
            assertEquals(expectedTokens[i], tokens.get(i));
        }
    }

    @org.junit.Test
    public void checkDatatypeNormalisation() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        mode.setDatatype(Settings.Normalize.DATATYPE_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString(
                "public static void main ( String[] args ) { int x = 10;");

        String[] expectedTokens = {"public", "static", "void", "main", "(", "String", "[", "]"
                , "args", ")", "{", "D", "x", "=", "10", ";"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
            assertEquals(expectedTokens[i], tokens.get(i));
        }
    }

    @org.junit.Test
    public void checkJavaClassNormalisation() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        mode.setJavaClass(Settings.Normalize.JAVACLASS_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString(
                "public static void main ( String[] args ) { ArrayList<String> x = new ArrayList<>();");

        String[] expectedTokens = {"public", "static", "void", "main", "(", "J", "[", "]"
                , "args", ")", "{", "J", "<", "J", ">", "x", "=", "new", "J", "<", ">"
                , "(", ")", ";"};

//        ArrayList<String> tokens = tokenizer.getTokensFromString("Arrays");
//        String[] expectedTokens = {"J"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " "); // + ":" + expectedTokens[i]);
            assertEquals(expectedTokens[i],tokens.get(i));
        }
    }

    @org.junit.Test
    public void checkJavaPackageNormalisation() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        mode.setJavaPackage(Settings.Normalize.JAVAPACKAGE_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString("Dynamic\n" +
                "DynamicAny\n" +
                "IOP\n" +
                "Messaging\n" +
                "NamingContextExtPackage\n" +
                "NamingContextPackage\n" +
                "ORBInitInfoPackage\n" +
                "ORBPackage");
        String[] expectedTokens = {"P", "P", "P", "P", "P", "P", "P", "P"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
            assertEquals(expectedTokens[i], tokens.get(i));
        }
    }

    @org.junit.Test
    public void checkWordNormalisation() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        mode.setWord(Settings.Normalize.WORD_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString("int x = y; MyVar");
        String[] expectedTokens = {"int", "W", "=", "W", ";", "W"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
            assertEquals(tokens.get(i), expectedTokens[i]);
        }
    }

    @org.junit.Test
    public void checkStringNormalisation() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        mode.setString(Settings.Normalize.STRING_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString("String x = \"Hello world!\"");
        String[] expectedTokens = {"String", "x", "=", "S"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
            assertEquals(tokens.get(i), expectedTokens[i]);
        }
    }

    @org.junit.Test
    public void checkDJKPSVWNormalisation() throws Exception {
        NormalizerMode mode = new NormalizerMode();
        mode.setDatatype(Settings.Normalize.DATATYPE_NORM_ON);
        mode.setJavaClass(Settings.Normalize.JAVACLASS_NORM_ON);
        mode.setKeyword(Settings.Normalize.KEYWORD_NORM_ON);
        mode.setJavaPackage(Settings.Normalize.JAVAPACKAGE_NORM_ON);
        mode.setString(Settings.Normalize.STRING_NORM_ON);
        mode.setWord(Settings.Normalize.WORD_NORM_ON);
        mode.setValue(Settings.Normalize.VALUE_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString("while (true) { int y = 0; String x = \"Hello world!\"; }");
        String[] expectedTokens = {"K", "(", "V", ")", "{", "D", "W", "=", "V", ";"
                , "J", "W", "=", "S", ";", "}"};

        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
            assertEquals(tokens.get(i), expectedTokens[i]);
        }
    }

    @org.junit.Test
    public void TestDefaultNormalizeMode() throws Exception {
        NormalizerMode mode = new NormalizerMode();
//        mode.setDatatype(Settings.Normalize.DATATYPE_NORM_ON);
//        mode.setJavaClass(Settings.Normalize.JAVACLASS_NORM_ON);
//        mode.setKeyword(Settings.Normalize.KEYWORD_NORM_ON);
//        mode.setJavaPackage(Settings.Normalize.JAVAPACKAGE_NORM_ON);
//        mode.setString(Settings.Normalize.STRING_NORM_ON);
//        mode.setWord(Settings.Normalize.WORD_NORM_ON);
        JavaTokenizer tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        ArrayList<String> tokens = tokenizer.getTokensFromString("    public static void BubbleSortInt1(int[] num) {\n" +
                "        boolean flag = true; // set flag to true to begin first pass\n" +
                "        int temp; // holding variable\n" +
                "\n" +
                "        while (flag) {\n" +
                "            flag = false; // set flag to false awaiting a possible swap\n" +
                "            for (int j = 0; j < num.length - 1; j++) {\n" +
                "                if (num[j] > num[j + 1]) // change to > for ascending sort\n" +
                "                {\n" +
                "                    temp = num[j]; // swap elements\n" +
                "                    num[j] = num[j + 1];\n" +
                "                    num[j + 1] = temp;\n" +
                "                    flag = true; // shows a swap occurred\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }");

//        HashSet<String> tokenSet = new HashSet<>(tokens);
//        tokens = new ArrayList<>(tokenSet);
//        Collections.sort(tokens);

        System.out.println(tokens.size());
        for (int i=0; i<tokens.size(); i++) {
            System.out.print(tokens.get(i) + " ");
        }

        System.out.println("\n\n");

        mode.setDatatype(Settings.Normalize.DATATYPE_NORM_ON);
        mode.setJavaClass(Settings.Normalize.JAVACLASS_NORM_ON);
        mode.setKeyword(Settings.Normalize.KEYWORD_NORM_ON);
        mode.setJavaPackage(Settings.Normalize.JAVAPACKAGE_NORM_ON);
        mode.setString(Settings.Normalize.STRING_NORM_ON);
        mode.setWord(Settings.Normalize.WORD_NORM_ON);
        tokenizer = new JavaTokenizer(new JavaNormalizer(mode));

        tokens = tokenizer.getTokensFromString("    public static void BubbleSortInt1(int[] num) {\n" +
                "        boolean flag = true; // set flag to true to begin first pass\n" +
                "        int temp; // holding variable\n" +
                "\n" +
                "        while (flag) {\n" +
                "            flag = false; // set flag to false awaiting a possible swap\n" +
                "            for (int j = 0; j < num.length - 1; j++) {\n" +
                "                if (num[j] > num[j + 1]) // change to > for ascending sort\n" +
                "                {\n" +
                "                    temp = num[j]; // swap elements\n" +
                "                    num[j] = num[j + 1];\n" +
                "                    num[j + 1] = temp;\n" +
                "                    flag = true; // shows a swap occurred\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }");

        // initialise the n-gram generator
        nGramGenerator ngen = new nGramGenerator(4);
        ArrayList<String> ntokens = ngen.generateNGramsFromJavaTokens(tokens);
//        tokenSet = new HashSet<>(ntokens);
//        tokens = new ArrayList<>(tokenSet);
//        Collections.sort(tokens);

        System.out.println(ntokens.size());
        for (int i=0; i<ntokens.size(); i++) {
            System.out.print(ntokens.get(i) + " ");
        }
    }
}