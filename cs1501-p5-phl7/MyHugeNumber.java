//Patrick Lyons
//CoE 1501
//Final Project
//April 21, 2017

import java.util.*;
import java.lang.*;
import java.nio.*;

public class MyHugeNumber{

    private RandomPrime rpg = new RandomPrime();

    public MyHugeNumber(){}

    public byte[] generatePrime(int bitLength){

        // Random generator
        Random rnd = new Random();
        int totalBytes = bitLength / 8;

        byte[] randPrime = new byte[totalBytes + 1];

        randPrime = rpg.generate(bitLength, rnd);

        return randPrime;
    }

    private String add(String Op1, String Op2){

      char posOP1;
      char posOP2;

      String sum = "";

      boolean carryOver = false;

      int limitingStringLength;

        if(Op2.length() < Op1.length()){ limitingStringLength = Op2.length(); }

        else{ limitingStringLength = Op1.length(); }

        // loop through from right to left
        for(int i = limitingStringLength - 1; i >= 0; i --)
        {

            posOP1 = Op1.charAt(i);
            posOP2 = Op2.charAt(i);

            if(posOP1 == '1')
            {
                if(posOP2 == '1')
                {
                    sum = "0" + sum;
                    carryOver = true;
                }
                else if(posOP2 == '0')
                {
                    if(carryOver){sum = "0" + sum;}
                    else{sum = "1" + sum;}
                }
            }

            else if(posOP1 == '0')
            {
                if(posOP2 == '1')
                {
                    if(carryOver)
                    {
                        sum = "0" + sum;
                        carryOver = false;
                    }
                    else{ sum = "1" + sum; }
                }

                else if(posOP2 == '0')
                {
                    if(carryOver)
                    {
                        sum = "1" + sum;
                        carryOver = false;
                    }
                    else{ sum = "0" + sum; }
                }
            }
        }

        return sum;
    }

    public byte[] subtract(byte[] Op1){

        char currBit1;
        char resBit;
        char currBit2;

        int position = -1;
        int finalSize;

        String result = "";
        String str1 = "";
        String str2 = "";

        byte[] finalArray = new byte[33];
        byte a;
        boolean carryBack = false;

        for(int i = 1; i < Op1.length; i ++){

            a = Op1[i];
            str2 = str2 + String.format("%8s", Integer.toBinaryString(a & 0xFF)).replace(' ', '0');
            str1 = "00000000" + str1;
        }

        str1 = str1 + "1";

        for(int i = 0; i < str2.length(); i ++)
        {

            currBit1 = str2.charAt(i);
            currBit2 = str1.charAt(i);

            if(currBit1 == '1')
            {
                if(currBit2 == '0'){ result = result + "1"; }
                else if(currBit2 == '1')
                {
                    if(carryBack)
                    {
                        result = result + "1";
                        carryBack = false;
                    }
                    else{ result = result + "0"; }
                }
            }

            else if(currBit1 == '0')
            {

                if(currBit2 == '0')
                {
                    if(carryBack)
                    {
                        result = result + "1";
                        carryBack = false;
                    }
                    else{ result = result + "0"; }
                }

                else if(currBit2 == '1')
                {

                    for(int j = 0; j < result.length(); j ++)
                    {
                        resBit = result.charAt(j);
                        if(resBit == '1') { position = j; }
                    }


                    finalSize = result.length();

                    if(position != -1)
                    {
                        result = result.substring(0, position);
                        result = result + "01";

                        for(int x = position; x < finalSize - 1; x ++)
                        {
                            if(x == finalSize - 2){ result = result + "1"; }
                            else{ result = result + "0"; }
                        }
                    }
                }
            }
        }

        int i = 0;
        int j = 0;
        String currentByte;

        while(i+8 <= result.length())
        {
            currentByte = result.substring(i, i+8);
            int temp =  Integer.parseInt(currentByte);
            finalArray[j] = (byte)temp;
            i += 8;
            j++;
        }
        return finalArray;
    }

    public byte[] multiply(byte[] Op1, byte[] Op2){

      byte firstMult;
      byte secMult;

      String bitstring1 = "";
      String bitstring2 = "";
      String tempResultString = "";
      String product = "";

      char currentBit;
      char curMultInput;
      int padding = 0;

      byte[] prodArray = new byte[33];
      ArrayList<String> tempResultArray = new ArrayList<String>();

      for(int i = 1; i < Op1.length; i ++)
      {
          firstMult = Op1[i];
          bitstring1 = bitstring1 + String.format("%8s", Integer.toBinaryString('a' & 0xFF)).replace(' ', '0');
      }

      // create the relative bitstring from byte array Op2
      for(int j = 1; j < Op2.length; j ++)
      {
          secMult = Op2[j];
          bitstring2 = bitstring2 +  String.format("%8s", Integer.toBinaryString(secMult & 0xFF)).replace(' ', '0');
      }



      for(int x = bitstring2.length() - 1; x >= 0; x --)
      {
          for(int p = 0; p <= padding; p ++) { tempResultString = tempResultString + "0"; }

          //CurrentBit
          currentBit = bitstring2.charAt(x);

          for(int y = bitstring1.length() - 1; y >= 0; y--)
          {
              curMultInput = bitstring1.charAt(y);

              if(curMultInput == '0'){ tempResultString = tempResultString + "0"; }

              else if(curMultInput == '1')
              {
                  if(currentBit == '1'){ tempResultString = tempResultString + "1"; }
                  else{ tempResultString = tempResultString + "0"; }
              }
          }

          tempResultArray.add(tempResultString);

          tempResultString = "";
          padding++;
      }

      product = tempResultArray.get(0);

      for(int t = 1; t < tempResultArray.size(); t ++){ product = add(product, tempResultArray.get(t)); }

      int i = 0;
      int j = 0;
      String currentByte;

      while(i+8 <= product.length())
      {
          currentByte = product.substring(i, i+8);
          int temp =  Integer.parseInt(currentByte);
          prodArray[j] = (byte)temp;
          i += 8;
          j++;
      }

      return prodArray;

    }

    public byte[] divide(byte[] div, byte[] divis){
      int i = 0;
      long quotient = 0;
      long divOne = convertByteLong(div);
      long divTwo = convertByteLong(divis);


      while (divTwo <= divOne && divTwo > 0)
      {
          //Bit shift left
          divTwo <<= 1;
          i++;
      }

      while (i-- > 0)
      {
          //Bit shift right
          divTwo >>= 1;

          if (divTwo <= divOne)
          {
              divOne = divOne - divTwo;
              quotient = (quotient << 1) + 1;
          }

          else quotient <<= 1;
      }

      return convertLongByte(quotient);
    }

    public byte[] E(){

        byte[] temp = new byte[1];
        String byteString = "1001";

        temp[0] = (byte) Integer.parseInt(byteString);

        return temp;
    }

    public byte[] D(){

        byte[] temp = new byte[1];
        String byteString = "1001";

        temp[0] = (byte) Integer.parseInt(byteString);

        return temp;
    }

    public byte[] convertLongByte(long a){
        ByteBuffer buffer = ByteBuffer.allocate(33);
        buffer.putLong(a);
        return buffer.array();
    }

    public long convertByteLong(byte[] bytes){
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}
