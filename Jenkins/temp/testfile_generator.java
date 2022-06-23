import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
//javac -cp json-20220320.jar testfile_generator.java
//java -cp "json-20220320.jar;" testfile_generator test2
public class testfile_generator
{
	public static HttpURLConnection conn;
	public static String[] testinput=new String[11];
	public static String[] testoutput=new String[11];
	public static void main(String[] args)throws Exception{
		String path = "./testfile/"+args[0]+"/AppTest.java";
		//System.out.println(path);
		File writename = new File(path);
		writename.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		String input = "package edu.selab;\nimport static org.junit.Assert.*;\nimport org.junit.Test;\n/**\n *\n Unit test for simple App.\n */\npublic class AppTest{\n";
		GetTestData(args[0]);
		for(int i=1 ;i<11; i++){
			input += "@Test\npublic  void  testMain"+i+"() {\nString ss =  \""+testoutput[i]+"\";\nassertEquals(ss,App.main("+"\""+testinput[i]+"\""+"));\n}\n";
		}
		input+="}";
		out.write(input);
		out.flush();
		out.close();
	}

	public static void GetTestData(String name)throws Exception{
		String urls = "http://120.108.204.152:3000/api/testdata/"+name;
	    BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		URL url = new URL(urls);
		conn = (HttpURLConnection) url.openConnection();
		// Request setup
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
		conn.setReadTimeout(5000);
			
		// Test if the response from the server is successful
		int status = conn.getResponseCode();
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = reader.readLine()) != null) {
			responseContent.append(line);
		}
		reader.close();
		System.out.println("response code: " + status);
		//System.out.println(responseContent.toString());
		parse(responseContent);
   }

   public static void parse(StringBuilder responseBody) {
		JSONArray jsonarray = new JSONArray( responseBody.toString());
		for (int i = 0; i < jsonarray.length(); i++) {
    		JSONObject jsonobject = jsonarray.getJSONObject(i);
			for(int j=1;j<11;j++){
				testinput[j] = jsonobject.getString("input"+j);
				testoutput[j] = jsonobject.getString("output"+j);
			}
		}
		/*for(int i =1; i<11;i++){
			System.out.println("input"+i+": "+testinput[i]);
		}
		for(int i =1; i<11;i++){
			System.out.println("output"+i+": "+testoutput[i]);
		}*/
		
	}
}
