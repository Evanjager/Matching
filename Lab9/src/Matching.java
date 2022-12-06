import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Matching { // All methods except text() method is from Geeks for Geeks.
                        // Referances:
                        // KMP: https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/#video 
                        // Boyer Moore: https://www.geeksforgeeks.org/boyer-moore-algorithm-for-pattern-searching/ 
                        // Brute: https://www.geeksforgeeks.org/naive-algorithm-for-pattern-searching/ 
                        // Rabin karp: https://www.geeksforgeeks.org/rabin-karp-algorithm-for-pattern-searching/

    static int chars = 256;

    public int findKMP(String text, String pattern) {
        int M = pattern.length();
        int N = text.length();

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int lps[] = new int[M];
        int j = 0; // index for pattern[]
        int count = 0;

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pattern, M, lps);

        int i = 0; // index for text[]
        while ((N - i) >= (M - j)) {
            count++;
            if (pattern.charAt(j) == text.charAt(i)) {
               
                j++;
                i++;
            }
            if (j == M) {
                j = lps[j - 1];

                // return count;
                // uncomment for first occurance
             
            }

            // mismatch after j matches
            else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return count;
    }

    void computeLPSArray(String pattern, int M, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pattern[i] != pattern[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    public static int findBoyerMoore(char[] text, char[] pattern) {
        int m = pattern.length;
        int n = text.length;
        int count = 0;

        int badchar[] = new int[chars];

        /*
         * Fill the bad character array by calling
         * the preprocessing function badCharHeuristic()
         * for given pattern
         */
        badCharHeuristic(pattern, m, badchar);

        int s = 0; // s is shift of the pattern with
                   // respect to text
        // there are n-m+1 potential alignments
        while (s <= (n - m)) {
            int j = m - 1;
        

            /*
             * Keep reducing index j of pattern while
             * characters of pattern and text are
             * matching at this shift s
             */
            while (j >= 0 && pattern[j] == text[s + j])
                j--;
                count++;
             
           
            /*
             * If the pattern is present at current
             * shift, then index j will become -1 after
             * the above loop
             */
            if (j < 0) {
              
               
                /*
                 * Shift the pattern so that the next
                 * character in text aligns with the last
                 * occurrence of it in pattern.
                 * The condition s+m < n is necessary for
                 * the case when pattern occurs at the end
                 * of text
                 */
                // text[s+m] is character after the pattern in text
                s += (s + m < n) ? m - badchar[text[s + m]] : 1;
            
              
            //    return count;
            //    uncmment this for algorithm to stop after first pattern found
            }

            else{
                
                
                /*
                 * Shift the pattern so that the bad character
                 * in text aligns with the last occurrence of
                 * it in pattern. The max function is used to
                 * make sure that we get a positive shift.
                 * We may get a negative shift if the last
                 * occurrence of bad character in pattern
                 * is on the right side of the current
                 * character.
                 */
                s += max(1, j - badchar[text[s + j]]);
                count++;
            }
            count--;
        }
        return count;
    }

    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    static void badCharHeuristic(char[] str, int size, int badchar[]) {

        // Initialize all occurrences as -1
        for (int i = 0; i < chars; i++)
            badchar[i] = -1;

        // Fill the actual value of last occurrence
        // of a character (indices of table are ascii and values are index of
        // occurrence)
        for (int i = 0; i < size; i++)
            badchar[(int) str[i]] = i;
            
    }

    public static int findBrute(String text, String pattern) {

        int l1 = text.length();
        int l2 = pattern.length();
        int count = 0;

        int i = 0, j = l2 - 1;

        for (i = 0, j = l2 - 1; j < l1;) {
            count++;
            if (pattern.equals(text.substring(i, j + 1))) {
                // return count;
                //un comment for first occurance
            }
            i++;
            j++;

        }
        return count;
    }

    public static int findRabinKarp(String text, String pattern) {
        int M = pattern.length();
        int N = text.length();
        int i, j;
        int p = 0; // hash value for pattern
        int t = 0; // hash value for text
        int h = 1;
        int q = 101; // prime number

        int count = 0;

        // The value of h would be "pow(chars, M-1)%q"
        for (i = 0; i < M - 1; i++)
            h = (h * chars) % q;

        // Calculate the hash value of pattern and first
        // window of text
        for (i = 0; i < M; i++) {
            p = (chars * p + pattern.charAt(i)) % q;
            t = (chars * t + text.charAt(i)) % q;
        }

        // Slide the pattern over text one by one
        for (i = 0; i <= N - M; i++) {
            
            // Check the hash values of current window of
            // text and pattern. If the hash values match
            // then only check for characters one by one
            if (p == t) {
                /* Check for characters one by one */
                for (j = 0; j < M; j++) {
                    count++;
                    if (text.charAt(i + j) != pattern.charAt(j))
                        break;
                }

                // if p == t and pattern[0...M-1] = text[i, i+1,
                // ...i+M-1]
                if (j == M){
                    // return count;
                    //in comment for first occurance
                }
                    
            }

            // Calculate hash value for next window of text:
            // Remove leading digit, add trailing digit
            if (i < N - M) {
                t = (chars * (t - text.charAt(i) * h)
                        + text.charAt(i + M))
                        % q;

                // We might get negative value of t,
                // converting it to positive
                if (t < 0)
                    t = (t + q);
            }
        }
        return count;
    }

    public void text() throws IOException {// reads all the words and calls the hash code method
        String webaddress = "https://github.com/dwyl/english-words/raw/master/words.txt";
        URL url = new URL(webaddress);
        Scanner scan = new Scanner(url.openStream());
        Matching m = new Matching();

        String word = "";
        int count = 0;

        while (scan.hasNext() && count < 200) {
            word += scan.next().toUpperCase();

            count++;
        }

    //    System.out.println("KMP comparisons:" +m.findKMP(word, "POINT"));
    //    System.out.println("Boyer Moore comparisons:" +m.findBoyerMoore(word.toCharArray(), "POINT".toCharArray()));
    //    System.out.println("Brute comparisons:" +m.findBrute(word, "POINT"));
    //    System.out.println("Rabin Karp comparisons:" +m.findRabinKarp(word, "POINT"));

    }
    public static void main(String[] args) throws IOException {
        Matching m = new Matching();
        m.text();
    }
}
