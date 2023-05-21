package com.dnaencrypt.dnaencrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import joelproject1.DNAdecryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Scanner;


public class Decryption {
	
	static String tempstring;//temporary variable to store DNA code
	static String revdnacode[];
	static String dnacode[];
	static int DNAlength;
	static int keylenth;
	static int asciikey[]; //to store key in ASCII code
	static String hexkey[]; //to store key in hexadecimal code
	static String rearr[]; //to store array rearranged in 2,4,1,3 order
	static String hex[]; //to store message in hexadecimal code in 1,2,3,4 order
	String optex=""; //to store plain text message

	public static String plaintext;
	
	
	//function to convert to ASCII
	static void ASCIISentence(String sentence,int ascii[])
	    {
	        int l = sentence.length();
	        int convert;
	        
	        for (int i = 0; i < l; i++)
	        {
	            convert = sentence.charAt(i);//converting to ASCII
	            ascii[i]=convert;
	            //System.out.print(ascii[i]);
	        }
	    }
	
	 //converting ASCII to hexadecimal
	 static void toHexadecimal(String arr[],int asciistr[],int len)
	 {
		 for (int i=0;i<len;i++) 
		 { 
			 arr[i]=Integer.toHexString(asciistr[i]); //converting to hexadecimal 
		 }
	 }
	 
	 //function to convert hexadecimal to binary coded decimal
	 public String hextoBCD(String s) 
	 {
		 String result="";
		 int len=s.length();
		 for(int i=0;i<len;i++) 
		 {
			 if(Character.isDigit(s.charAt(i))) 
			 {
				 String res=Integer.toBinaryString((int)s.charAt(i));
				 //System.out.print(res.substring(res.length() - 4) + " ");
				 result=result.concat(res.substring(res.length()-4));
			 }
			 else 
			 {
				 String res=Integer.toBinaryString((int)s.charAt(i)-55);
				 //System.out.print(res.substring(res.length() - 4) + " ");
				 result=result.concat(res.substring(res.length()-4));
			 }
		 }
		 return result;
	 }

	//function to x-or two numbers
	 static String xoring(String a,String b,int n) 
	 {
		 String ans="";
		 for(int i=0;i<n;i++) 
		 {
			if(a.charAt(i) == b.charAt(i)) 
				ans+="0";
			else
				ans+="1";
		 }
		return ans; 
	 }
	 
	 //function to convert DNA code to BCD
	 public String dnatobcd(String a) 
	 {
		 String ans="";
		 int alength=a.length();
		 for(int i=0;i<alength;i++) 
		 {
			 if(a.charAt(i)=='A')
				 ans+="00";
			 else if(a.charAt(i)=='C')
				 ans+="01";
			 else if(a.charAt(i)=='G')
				 ans+="10";
			 else if(a.charAt(i)=='T')
				 ans+="11";
		 }
		return ans;
		 
	 }
	 
	 //function to convert BCD to hex
	 public static String bcdtohex(String binaryString) {
		    String hexString = "";
		    int len = binaryString.length();
		    // pad the binary string with zeros to make its length a multiple of 4
		    while (len % 4 != 0) {
		        binaryString = "0" + binaryString;
		        len++;
		    }
		    // convert 4 binary digits at a time to a hexadecimal digit
		    for (int i = 0; i < len; i += 4) {
		        int decimalValue = Integer.parseInt(binaryString.substring(i, i + 4), 2);
		        hexString += Integer.toHexString(decimalValue).toUpperCase();
		    }
		    return hexString;
		}
	//rearranging columns in the order 1,2,3,4
	 static void rearrange(String arr[],String hexadeci[],int len) 
	 {
		 for(int i=0;i<len;i=i+4) 
		 {
			 arr[i+1]=hexadeci[i];
		 }
		 for(int i=1;i<len;i=i+4) 
		 {
			 arr[i+2]=hexadeci[i];
		 }
		 for(int i=2;i<len;i=i+4) 
		 {
			 arr[i-2]=hexadeci[i];
		 }
		 for(int i=3;i<len;i=i+4) 
		 {
			 arr[i-1]=hexadeci[i];
		 }
	 }
	 
	 //function to convert hexadecimal number to ASCII
	 public static String hextoascii(String hex) {
		    StringBuilder output = new StringBuilder();
		    for (int i = 0; i < hex.length(); i+=2) {
		        String str = hex.substring(i, i+2);
		        output.append((char) Integer.parseInt(str, 16));
		    }
		    return output.toString();
		}

	//String keyString;
	//public void recall
	public static String decrypt(String str1, String str2)throws InputMismatchException, IOException, Exception {
		// TODO Auto-generated method stub
		Decryption op = new Decryption();
		String ciphertext = str1;
//		Scanner sc=new Scanner(System.in);
//		System.out.println("Enter the message to be decoded");
//		String ciphertext=sc.nextLine();//to store message from input
//		System.out.println("Enter the key");
//		String skey=sc.next();//to store key
		String skey=str2;
		keylenth=skey.length();
//		sc.close();
		
		//the key value
        
        String keyString = "ABCDEF0123456789";

        // Convert the key string to bytes
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);

        // Create a SecretKeySpec object from the key bytes
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        
        // Decode the cipher text from Base64 back to bytes
        byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);

        // Create a Cipher object and initialize it for decryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // decrypt the cipher text using the Cipher object
        byte[] plaintextBytes = cipher.doFinal(ciphertextBytes);

        // Convert the plain text bytes to a string and print it
        plaintext = new String(plaintextBytes, StandardCharsets.UTF_8);
        //System.out.println("AES decrypted output: " + plaintext);
        
        
        //DNA decryption
        tempstring=plaintext;
    	DNAlength=tempstring.length();
    	int tlen=DNAlength/4;
    	revdnacode=new String[tlen];
    	
    	
    	//code to store DNA code in an array
    	int ctr=0;//counter variable
    	for(int i=0;i<tlen;i=i+4) 
    	{
    		revdnacode[i]=tempstring.substring(ctr,ctr+4);	
    		ctr+=4;
    	}
    	for(int i=1;i<tlen;i=i+4) 
    	{
    		revdnacode[i]=tempstring.substring(ctr,ctr+4);
    		ctr+=4;
    	}
    	for(int i=2;i<tlen;i=i+4) 
    	{
    		revdnacode[i]=tempstring.substring(ctr,ctr+4);	
    		ctr+=4;
    	}
    	for(int i=3;i<tlen;i=i+4) 
    	{
    		revdnacode[i]=tempstring.substring(ctr,ctr+4);
    		ctr+=4;
    	}
		
		
		//DNA code in 2,4,1,3 order
		dnacode=new String[tlen];
		for(int i=0;i<tlen;i++) 
		{
			if(i%4==0)
				dnacode[i+3]=revdnacode[i];
			else if(i%4==1)
				dnacode[i+1]=revdnacode[i];
			else if(i%4==2)
				dnacode[i-1]=revdnacode[i];
			else if(i%4==3)
				dnacode[i-3]=revdnacode[i];
		}
		
		
		
		//converting key to ASCII and printing->which is to be removed
		//System.out.println("key ASCII value:");
		asciikey=new int[keylenth];
		ASCIISentence(skey,asciikey);
		//System.out.println("\n");
		
		
		//convert key to hexadecimal and printing->which is to be removed
		hexkey=new String[keylenth];
		toHexadecimal(hexkey,asciikey,keylenth);
		
		
		//converting to rearranged code
		rearr=new String[tlen];
		int btr=0;
		for(int i=0;i<tlen;i++)
		{
			if(i%4==0)
				btr=0;
			String sptr=dnacode[i];
			String keyptr=hexkey[btr];
//			Decryption op=new Decryption();
			String dut=op.dnatobcd(sptr);
			String dut1=op.hextoBCD(keyptr);
			int k=dut.length();
			String xorval=xoring(dut,dut1,k);
			rearr[i]=bcdtohex(xorval);
			btr++;
		}
		
		
		
		
		//rearranging columns in the order 1,2,3,4
		hex= new String[tlen];
		rearrange(hex,rearr,tlen);
		
		
		//converting hex to ASCII
		for(int i=0;i<tlen;i++)
		{
			String t=hextoascii(hex[i]);
			op.optex+=t;
		}
		
		System.out.println("Output message:"+op.optex);
		
		return op.optex;
		
	}

}
