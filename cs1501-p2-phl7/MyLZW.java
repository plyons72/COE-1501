/******************************************************************************
 *  Compilation:  javac MyLZW.java
 *  Execution:    java MyLZW - < input.txt   (compress)
 *  Execution:    java MyLZW + < input.txt   (expand)
 *  Depencmpsizecies: BinaryIn.java BinaryOut.java
 *  Data files:   http://algs4.cs.princeton.edu/55compression/abraLZW.txt
 *                http://algs4.cs.princeton.edu/55compression/ababLZW.txt
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;



public class MyLZW {
    private static final int R = 256;  // number of input chars
    private static int L = 512;       // number of codewords = 2^W
    public static int W = 9;         // codeword width


    //Stores mode for compression
    private static String compressionMode;

    //Stores the previous and current ratios
    private static double oldRatio = 0;
    private static double newRatio = 0;

    //Size of data (uncompressed or compressed) that has been processed for ratio
    private static double uncmpsize = 0;
    private static double cmpsize = 0;

    //Checks if a ratio actually exists
    private static boolean noRatio = true;


    // Do not instantiate.
    private MyLZW() { }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     */
    public static void compress() {

      //Cases will determine which mode is being used
      //Based on mode, appropriately sends args to .write
      if(compressionMode.equals("r"))
        BinaryStdOut.write('r', 8);

      if(compressionMode.equals("m"))
        BinaryStdOut.write('m', 8);

      if(compressionMode.equals("n"))
        BinaryStdOut.write('n', 8);

      String input = BinaryStdIn.readString();

      TST<Integer> st = new TST<Integer>();

      for (int i = 0; i < R; i++) {
          st.put("" + (char) i, i);
        }

      int code = R+1;  // R is codeword for EOF

      while (input.length() > 0) {

        //Gives L its proper value (L = 2^W)
        L = (int)Math.pow(2, W);

        String s = st.longestPrefixOf(input);  // Find max prefix match s.
        uncmpsize += 8 * s.length();

        BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
        cmpsize += W;

        //Sets ratio
        newRatio = uncmpsize/cmpsize;

        int t = s.length();

        if (t < input.length() && code < L) {   // Add s to symbol table.
            st.put(input.substring(0, t + 1), code++);
          }

        int temp = (int)Math.pow(2, W);

        if (temp == code && 16 > W) {
          W++;
          L = (int)Math.pow(2, W);
          st.put(input.substring(0, t + 1), code++);
          }

        if (compressionMode.equals('r') && code == 65536) {
          st = new TST<Integer>();

          for (int i = 0; i < R; i++) {
              st.put("" + (char) i, i);
          }

          code = R + 1;

          W = 9;
          L = 512;
        }

        if (compressionMode.equals("m") && code == 65536) {

            if (noRatio) {
                oldRatio = newRatio;
                noRatio = false;
            }

            //Checks if the ratio is above the threshold
            if (oldRatio/newRatio > 1.1) {
                st = new TST<Integer>();

                for (int i = 0; i < R; i++) {
                  st.put("" + (char) i, i);
                }

                code = R + 1;

                W = 9;
                L = 512;

                //Resets ratios
                oldRatio = 0;
                newRatio = 0;

                //No ratio exists anykore
                noRatio = true;
            }
          }

        input = input.substring(t); // Scan past s in input.

      }//When input.length = 0

      BinaryStdOut.write(R, W);
      BinaryStdOut.close();
    } //End of Compression

    /**
     * Reads a sequence of bit encoded using LZW compression with
     * 12-bit codewords from standard input; expands them; and writes
     * the results to standard output.
     */

     public static void expand() {

      //Determines what type of expansion to perform
     char c;

     c = BinaryStdIn.readChar(8);

     //Sets mode based on char read
     if (c == 'r')
        compressionMode= "r";

     if (c == 'm')
        compressionMode= "m";

     String[] st = new String[(int) Math.pow(2, 16)];
     int i;

     // initialize symbol table with all 1-character strings
     for (i = 0; i < R; i++) {
      st[i] = "" + (char) i;
     }

     st[i++] = ""; // (unused) lookahead for EOF

     int codeword = BinaryStdIn.readInt(W);
     if (codeword == R) return; // expanded message is empty string
     String val = st[codeword];

     while (true) {

      BinaryStdOut.write(val);

      uncmpsize += val.length() * 8;
      codeword = BinaryStdIn.readInt(W);
      cmpsize += W;

      newRatio = uncmpsize / cmpsize;

      if (codeword == R)
        break;

      String s = st[codeword];

      if (i == codeword)
        s = val + val.charAt(0); // special case hack

      if (i < L - 1)
        st[i++] = val + s.charAt(0);

      if (16 > W && i == L - 1) {
       st[i++] = val + s.charAt(0);
       W++;
       L = (int) Math.pow(2, W);
      }

      val = s;

      if (i == 65535 &&compressionMode.equals("r")) {
       W = 9;
       L = 512;
       st = new String[(int) Math.pow(2, 16)];
       for (i = 0; i < R; i++) {
        st[i] = "" + (char) i;
       }

       st[i++] = "";


       codeword = BinaryStdIn.readInt(W);
       if (codeword == R) return; // expanded message is empty string
       val = st[codeword];
      }

      if (i == 65535 && compressionMode.equals("m")) {

       if (noRatio) {
        oldRatio = newRatio;
        noRatio = false;
       }

       System.err.println(oldRatio / newRatio);

       if (oldRatio / newRatio > 1.1) {

         W = 9;
         L = 512;

         st = new String[(int)Math.pow(2, 16)];

         for (i = 0; i < R; i++) {
          st[i] = "" + (char) i;
         }
         st[i++] = "";


        codeword = BinaryStdIn.readInt(W);
        if (codeword == R)
          return; // expanded message is empty string

        val = st[codeword];

        oldRatio = 0;
        newRatio = 0;
        noRatio = true;
       }
      }


     }
     BinaryStdOut.close();
    }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */

     //Main will determine what to do with the file given based on arguments provided
     public static void main(String[] args) {
      if (args[0].equals("-") && args[1].equals("r")) {
       compressionMode= "r";
       compress();
      }

      else if (args[0].equals("-") && args[1].equals("m")) {
        compressionMode= "n";
        compress();
      }

      else if (args[0].equals("-") && args[1].equals("n")) {
       compressionMode= "n";
       compress();
      }

      else if (args[0].equals("+"))
        expand();



      else throw new IllegalArgumentException("Illegal command line argument");
     }

}
