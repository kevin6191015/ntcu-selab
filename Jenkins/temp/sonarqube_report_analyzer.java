import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

public class sonarqube_report_analyzer
{
     public static String[] sonar_property=new String[11];
     public static String[] sonar_value=new String[11];
     public static String projectName;
     public static HttpURLConnection conn;
     
     
     public static void main(String[] args)throws Exception{
          GetStudentID(args[0]);
          GetSourceCode(args[0]);
          GetSonarData(args[0]);
     }
     public static void  GetStudentID(String name)throws Exception{
          sonar_property[8]="projectName";//sonar_value[9] in GetSonarData
          sonar_property[9]="StudentID";
          sonar_value[9]=name.substring(name.lastIndexOf("_") + 1);
          sonar_property[10]="AnalyzeDate";

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          sdf.setTimeZone(TimeZone.getDefault());
          sonar_value[10]=sdf.format(calendar.getTime());
     }

     public static void GetSourceCode(String name)throws Exception{
          BufferedReader reader;
		int line;
		StringBuilder responseContent = new StringBuilder();
          String urls = "http://120.108.204.152:9000/api/sources/raw?key=edu.selab:"+name+":src/main/java/edu/selab/App.java";
          String token = "281773e636d8065856593e8633545903a553de1c"+":";
          String basicAuth = "Basic "+new String(Base64.getEncoder().encode(token.getBytes("UTF-8")));
          URL url = new URL(urls);
          conn = (HttpURLConnection) url.openConnection();
          conn.setRequestProperty("Authorization", basicAuth);
          conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
		conn.setReadTimeout(5000);
          int status = conn.getResponseCode();
          reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = reader.read()) != -1) {
			responseContent.append((char)line);
		}
		reader.close();
		System.out.println("response code: " + status);
          parseSourceCode(responseContent);
     }
     
     public static void parseSourceCode(StringBuilder response){
          sonar_property[7] = "Source Code";
          sonar_value[7]=response.toString();
     }

     public static void GetSonarData(String name)throws Exception{
          BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();

          String urls = "http://120.108.204.152:9000/api/measures/component?component=edu.selab:"+name+"&metricKeys=sqale_rating,security_rating,security_review_rating,reliability_rating,code_smells,bugs,vulnerabilities";
          //System.out.println(urls);
          String token = "281773e636d8065856593e8633545903a553de1c"+":";
          String basicAuth = "Basic "+new String(Base64.getEncoder().encode(token.getBytes("UTF-8")));

          URL url = new URL(urls);
          conn = (HttpURLConnection) url.openConnection();
          conn.setRequestProperty("Authorization", basicAuth);
          conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
		conn.setReadTimeout(5000);
          int status = conn.getResponseCode();
          reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = reader.readLine()) != null) {
			responseContent.append(line);
		}
		reader.close();
		System.out.println("response code: " + status);
          parseData(responseContent);
     }
     
     public static void parseData(StringBuilder response) {
          JSONObject responseBody = new JSONObject( response.toString());
          sonar_value[8] = responseBody.getJSONObject("component").getString("name");
          JSONArray arr = responseBody.getJSONObject("component").getJSONArray("measures");
          for (int i = 0; i < arr.length(); i++)
          {
               sonar_property[i] = arr.getJSONObject(i).getString("metric");
               sonar_value[i] = arr.getJSONObject(i).getString("value");
          }
          for(int i =0; i<11;i++){
			System.out.println(sonar_property[i]+": "+sonar_value[i] );
		}
     }
}

