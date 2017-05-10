//Patrick Lyons
//Coe 1501
//Final Project
//April 21, 2017

import java.io.*;
import java.nio.file.*;
import java.nio.*;
import java.lang.*;
import java.security.MessageDigest;

public class RsaSign{

  public static void main(String[] args) {

        if (args.length != 2)
        {
          System.out.println("Must add arguments for sign option and filename");
          System.exit(0);
        }

        String opt = args[0];
        String fileName = args[1];

        if(opt.equals("v") || opt.equals("V")) { verify(fileName); }
        else if(opt.equals("s") || opt.equals("S")){ sign(fileName); }
        else
        {
          System.out.println("Invalid Option"); 
          System.exit(0);
        }
    }//End of main

  public static void verify(String file) {

      String hString = "";
      try
      {
        Path pathStr = Paths.get(file);
        byte[] byteData = Files.readAllBytes(pathStr);

        MessageDigest mdigest = MessageDigest.getInstance("SHA-256");

        mdigest.update(byteData);

        byte[] digestOrig = mdigest.digest();

        Path pathStr2 = Paths.get(file + ".sig");
        byte[] byteData2 = Files.readAllBytes(pathStr2);


        MessageDigest mdigest2 = MessageDigest.getInstance("SHA-256");
        mdigest.update(byteData2);

        byte[] digestSignature= mdigest2.digest();

        FileInputStream fileInput = new FileInputStream(new File("pubkey.rsa"));

        byte[] n = new byte[33];
        byte[] e = new byte[8];

        int r = fileInput.read(e);
        r = fileInput.read(n);

        double d_sign = ByteBuffer.wrap(digestSignature).getDouble();
        double d_orig = ByteBuffer.wrap(digestOrig).getDouble();

        //values for e and n
        double dub_e = ByteBuffer.wrap(e).getDouble();
        double dub_n = ByteBuffer.wrap(n).getDouble();

        d_sign = Math.pow(d_sign, dub_e) % dub_n;

        if(d_sign == d_orig){ System.out.println(file + " is valid"); }
        else{ System.out.println(file + " is invalid");	}

      } catch(Exception exc) { System.out.println("File does not exist");	}
    }//End of Verify

  public static void sign(String file) {

      String hString = "";

		  try
      {

  			Path pathStr = Paths.get(file);
  			byte[] byteData = Files.readAllBytes(pathStr);

  			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");

  			mdigest.update(byteData);
  			byte[] digest = mdigest.digest();

  			for (byte b : digest) {	hString = hString + String.format("%02x", b); }

  			FileInputStream fileInput = new FileInputStream(new File("privkey.rsa"));

  			byte[] d = new byte[8];
  			byte[] n = new byte[33];

  			int i = fileInput.read(d);
  			i = fileInput.read(n);

  			double dig = ByteBuffer.wrap(digest).getDouble();
  			double dub_d = ByteBuffer.wrap(d).getDouble();
  			double dub_n = ByteBuffer.wrap(n).getDouble();

  			dig = Math.pow(dig, dub_d) % dub_n;

  			byte[] digestBytes = new byte[8];
        ByteBuffer.wrap(digestBytes).putDouble(dig);


  			FileOutputStream w = new FileOutputStream(file + ".sig");

        w.write(digestBytes);
        w.close();


      } catch(Exception exc) { System.out.println("Error! Key does not exist"); }

    }//End of Sign



}//End of file
