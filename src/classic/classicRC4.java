package classic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.InputMap;

public class classicRC4 
{
	public static int keylen=64;       //key size in bytes
	public static int len=512;         //key stream size
	public static Integer []K=new Integer[len];
	public static Character []Kchar=new Character[len];
	public static int []S=new int[256];
	public static int []key=new int[keylen];
	public static Character hex[]={'0','1', '2', '3', '4', '5', '6', '7','8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	public static String []byteval=new String[256];
	public static int a[][]=new int[256][2];
	
	public static void int_key()
	{
		for(int i=0;i<keylen;i++)
		{
			float temp=(float) Math.random()*100;
			key[i]=(int) temp%16;
		}
	}
	
	public static void int_key_manual() throws IOException
	{
		System.out.print("Enter key (HEX) :");
		BufferedReader bin=new BufferedReader(new InputStreamReader(System.in));
		String key_s=bin.readLine();
		keylen=key_s.length();
		for(int i=0;i<keylen;i++)
		{
			Character tmp=key_s.charAt(i);
			key[i]=Integer.parseInt(tmp.toString(),16);
		}
	}
	
	public static void int_S()
	{
		for(int i=0;i<256;i++)
			S[i]=i;
	}
	
	public void KSA(String ch) throws IOException
	{
		
		if(ch.compareToIgnoreCase("1")==0)
		int_key();
		if(ch.compareToIgnoreCase("2")==0)
		int_key_manual();
		int_S();
		int i,j=0,temp;
		for(i=0;i<256;i++)
		{
			j=(j+S[i]+key[i%keylen])%256;
			temp=S[i];
			S[i]=S[j];
			S[j]=temp;
		}
	}
	
	public static void tohex()   //convert and print
	{
		a[0][0]=0;
		a[0][1]=0;
		int temp = 0;
		int temp1=0;
		for(int i=1;i<256;i++)
		{
			temp++;
			a[i][0]=temp;
			a[i][1]=temp1;
			if(temp!=0 && temp%16==0)
			{
				temp1++;
				a[i][1]=temp1;
				a[i][0]=0;
				temp=0;
			}
		}
		for(int i=0;i<256;i++)
			byteval[i]=hex[a[i][1]].toString().concat(hex[a[i][0]].toString());
	}
	
	public void PRGA()
	{
		int i=0,j=0;
		int temp=0;
		for(int f=0;f<len;f++)
		{
			i=(i+1)%256;
			j=(j+S[i])%256;
			temp=S[i];
			S[i]=S[j];
			S[j]=temp;
			K[f]=S[(S[i]+S[j])%256];
		}
		for(int f=0;f<len;f++)		
			System.out.print(byteval[K[f]]+" ");
	}
	
	public static void main(String[] args) throws IOException 
	{
		System.out.print("Enter test size :");
		BufferedReader bin=new BufferedReader(new InputStreamReader(System.in));
		int testcase=Integer.parseInt(bin.readLine());
		System.out.println("1.Automatic key \n2. Manual key");
		String ch=new BufferedReader(new InputStreamReader(System.in)).readLine();
		classicRC4.tohex();
		int count=0;
		for(int i=0;i<testcase;i++)
		{
			new classicRC4().KSA(ch);
			new classicRC4().PRGA();
			System.out.println();
			Character t=classicRC4.byteval[K[0]].charAt(0);
			if(Integer.parseInt(t.toString(),16)<8)
				count++;
		}
		float countf=(float)count/testcase *100;
		System.out.println(countf);
	}
}
