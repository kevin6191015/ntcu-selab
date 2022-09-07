import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
//javac -cp json-20220320.jar testfile_generator.java
//java -cp "json-20220320.jar;" testfile_generator test2
//sudo cp json-20220320.jar testfile_generator.java sonarqube_report_analyzer.java template.txt /home/selab/server/jenkins/home/workspace

public class testfile_generator
{
	private String[] testinput=new String[11];
	private String[] testoutput=new String[11];
	public static void main(String[] args)throws Exception{
		testfile_generator tg = new testfile_generator();
		tg.GetTestData(args[0]);
		tg.Write_testdata(args[0]);
	}
	public  void Write_testdata(String name)throws Exception{
		String input="";
		String path = "./testfile/"+name+"/AppTest.java";
		String template_path="template.txt";
		//System.out.println(path);
		File writename = new File(path);
		writename.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		try (FileReader fr = new FileReader(template_path))
        {
            int content;
            while ((content = fr.read()) != (int)'@') {
                input+=(char) content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		for(int i=1 ;i<11; i++){
			input += "@Test\npublic  void  testMain"+i+"() {\nString ss =  \""+testoutput[i]+"\";\nassertEquals(ss,App.main("+"\""+testinput[i]+"\""+"));\n}\n";
		}
		input+="}";
		out.write(input);
		out.flush();
		out.close();
	}
	public  void GetTestData(String name)throws Exception{
		String urls = "http://120.108.204.152:3000/api/testdata/"+name;
		System.out.println(urls);
	    BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		URL url = new URL(urls);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

   public void parse(StringBuilder responseBody) {
		JSONArray jsonarray = new JSONArray( responseBody.toString());
    	JSONObject jsonobject = jsonarray.getJSONObject(0);
		for(int j=1;j<11;j++){
			testinput[j] = jsonobject.getString("input"+j);
			testoutput[j] = jsonobject.getString("output"+j);
		}
		for(int i =1; i<11;i++){
			System.out.println("input"+i+": "+testinput[i]);
		}
		for(int i =1; i<11;i++){
			System.out.println("output"+i+": "+testoutput[i]);
		}
		
	}
}
