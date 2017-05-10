//Patrick Lyons
//Coe 1501
//Final Project
//April 21, 2017

import java.io.FileOutputStream;

public class RsaKeyGen{

    private static final int BITSIZE = 256;

    //Byte variables for p q and n
    private static byte[] p;
    private static byte[] q;
    private static byte[] n;

    //Byte variables for (p-1), (q-1), and phi(n)
    private static byte[] phiP;
    private static byte[] phiQ;
    private static byte[] phiN;

    //Byte variables for e and d
    private static byte[] e;
    private static byte[] d;

    public static void main(String[] args){

        MyHugeNumber hNum = new MyHugeNumber();

        //Gets byte arrays for p and q
        p = hNum.generatePrime(BITSIZE);
        q = hNum.generatePrime(BITSIZE);

        //Solves for n by multiplying p and q
        n = hNum.multiply(p, q);

        //gets p-1 and q-1 to solve for phi(n)
        phiP = hNum.subtract(p);
        phiQ = hNum.subtract(q);

        //Solves for phi(n) by multiplying (p-1) * (q-1)
        phiN = hNum.multiply(phiP, phiQ);

        //Gets values for E and D
        e = hNum.E();
        d = hNum.D();

        System.out.println(d.length);

        //Attempts to write to key files. Exits if not able to
        try
        {
           System.out.println("Generating Public Key...");
           FileOutputStream out = new FileOutputStream("pubkey.rsa");
           out.write(e);
           out.write(n);
           out.close();

           System.out.println("Generating Private Key...");
           FileOutputStream out2 = new FileOutputStream("privkey.rsa");
           out2.write(d);
           out2.write(n);
           out2.close();
        }
        catch(Exception ex)
        {
            System.out.println("Successfully created rsa files");
            System.exit(1);
        }

    }//End of main
}//End of file
