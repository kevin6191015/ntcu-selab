package edu.selab;
import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static final int BIG_NUM = 2000000000;
	public static final int MOD = 1000000007;

	public static String main(String args) {
		int n;
		Scanner sc = new Scanner(args);
		try{
			n = sc.nextInt();
		}finally {       
			sc.close();
		}
		if(n <= 0){
			return "-1";
		}

		long[] table = new long[n+1];

		table[0] = 1;
		table[1] = 1;

		for(int i = 2; i <= n; i++){
			table[i] = table[i-1]+table[i-2];
		}
		return Long.toString(table[n]);		
	}



}