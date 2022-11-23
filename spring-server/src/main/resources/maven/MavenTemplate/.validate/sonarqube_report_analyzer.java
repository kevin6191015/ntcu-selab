import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

public class sonarqube_report_analyzer
{
     private String[] sonar_property=new String[16];
     private String[] sonar_value=new String[16];

     private String[] score_property=new String[6];
     private String[] score_value=new String[6];
     private String sonar_issues = "" ;
     private Double[] index = new Double[4];
     private String postparm="";

     private String scoreparm="";


     public static void main(String[] args)throws Exception{
          sonarqube_report_analyzer sq = new sonarqube_report_analyzer();
          sq.GetSonarIssue(args[0]);
          sq.GetStudentID(args[0]);
          sq.GetSonarData(args[0]);
          sq.GetJenkinsData(args[0],args[1],args[2],args[3]);
          sq.GetSourceCode(args[0]);
          sq.GetPostparm();
          sq.GetScoreparm(args[0],args[2]);
          sq.WriteData();
          sq.WriteScore();
     }
     public void  GetJenkinsData(String name,String submit_time,String unit_test_result,String compile_result){
          sonar_property[11]="submit_times";
          sonar_property[12]="unit_test_result";
          sonar_property[13]="compile_result";
          sonar_property[14]="report_suggestion";
          sonar_property[15]="suggestion_code";
          sonar_value[11]=submit_time;
          sonar_value[12]=unit_test_result;
          sonar_value[15]="";
          if(compile_result.equals("0")){
               sonar_value[13]="compile error";
          }else{
               sonar_value[13]="compile success";
          }
     }

     public  void  GetPostparm(){
          for(int i=0;i<sonar_property.length;i++){
               if(i==(sonar_property.length-1)){
                    String temp=sonar_property[i]+"="+sonar_value[i];
                    postparm+=temp;
               }else{
                    String temp=sonar_property[i]+"="+sonar_value[i]+"&";
                    postparm+=temp;
               }
          }
          for(int i =0; i<sonar_property.length;i++){
               System.out.println(sonar_property[i]+": "+sonar_value[i] );
          }
     }

     public  void  GetScoreparm(String name,String unit_result){
          score_property[0]="student_id";
          score_property[1]="project_name";
          score_property[2]="submit_times";
          score_property[3]="analysis_date";
          score_property[4]="unit_test_score";
          score_property[5]="code_quality";
          score_value[0]=sonar_value[9];
          score_value[1]=sonar_value[8];
          score_value[2]=sonar_value[11];
          score_value[3]=sonar_value[10];
          if(Integer.parseInt(unit_result.substring(unit_result.indexOf(":")+1,unit_result.indexOf(",")))!=0) {
               score_value[4] = String.valueOf(Integer.parseInt(unit_result.substring(unit_result.lastIndexOf(":") + 1)) * (100 / Integer.parseInt(unit_result.substring(unit_result.indexOf(":") + 1, unit_result.indexOf(",")))));
               score_value[5] = String.valueOf((30 - index[0] * 5) + (30 - index[1] * 5) + (30 - index[2] * 5) + (30 - index[3] * 5));
          }else{
               score_value[4]= "0";
               score_value[5]= "0";
          }
          System.out.println(score_value[5]);
          for(int i=0;i<score_property.length;i++){
               if(i==(score_property.length-1)){
                    String temp=score_property[i]+"="+score_value[i];
                    scoreparm+=temp;
               }else{
                    String temp=score_property[i]+"="+score_value[i]+"&";
                    scoreparm+=temp;
               }
          }
     }
     public  void  WriteScore() throws Exception {
          String urls = "{Mysql-url}score/add";
          System.out.println("\n"+scoreparm);
          byte[] scoreData = scoreparm.getBytes( StandardCharsets.UTF_8 );
          int scoreDataLength = scoreData.length;
          URL url = new URL(urls);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          conn.setRequestProperty("Content-Length",String.valueOf(scoreDataLength));
          conn.setRequestProperty("charset", "utf-8");
          conn.setRequestMethod("POST");
          conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
          conn.setReadTimeout(5000);
          conn.setDoOutput(true);
          try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
               wr.write( scoreData );
          }
          InputStream stream = null;
          stream = conn.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
          String result = reader.readLine();

     }
     public  void  WriteData() throws Exception{
          String urls = "{Mysql-url}sqreport/add";
          System.out.println("\n"+postparm);
          byte[] postData = postparm.getBytes( StandardCharsets.UTF_8 );
          int postDataLength = postData.length;
          URL url = new URL(urls);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          conn.setRequestProperty("Content-Length",String.valueOf(postDataLength));
          conn.setRequestProperty("charset", "utf-8");
          conn.setRequestMethod("POST");
          conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
          conn.setReadTimeout(5000);
          conn.setDoOutput(true);
          try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
               wr.write( postData );
          }
          InputStream stream = null;
          stream = conn.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
          String result = reader.readLine();

     }
     public  void  GetStudentID(String name)throws Exception{
          sonar_property[8]="project_name";//sonar_value[9] in GetSonarData
          sonar_property[9]="student_id";
          sonar_value[9]=name.substring(name.lastIndexOf("_") + 1);
          sonar_property[10]="analysis_date";

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
          sonar_value[10]=sdf.format(calendar.getTime());
     }

     public void GetSourceCode(String name)throws Exception{
          BufferedReader reader;
          int line;
          StringBuilder responseContent = new StringBuilder();
          String urls = "{Sonar-url}/api/sources/raw?key=edu.selab:"+name+":src/main/java/edu/selab/App.java";
          String token = "{Sonar-token}"+":";
          String basicAuth = "Basic "+new String(Base64.getEncoder().encode(token.getBytes("UTF-8")));
          URL url = new URL(urls);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestProperty("Authorization", basicAuth);
          conn.setRequestMethod("GET");
          conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
          conn.setReadTimeout(5000);
          reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          while ((line = reader.read()) != -1) {
               responseContent.append((char)line);
          }
          reader.close();
          sonar_property[7] = "source_code";
          sonar_value[7]=responseContent.toString().replaceAll("\\+","`");
     }
     private void GetSonarIssue(String name)throws Exception{
          BufferedReader reader;
          int line;
          StringBuilder responseContent = new StringBuilder();
          String urls = "{Sonar-url}/api/issues/search?componentKeys=edu.selab:"+name+"&scope=MAIN&types=CODE_SMELL,BUG&resolved=false";
          String token = "{Sonar-token}"+":";
          String basicAuth = "Basic "+new String(Base64.getEncoder().encode(token.getBytes("UTF-8")));
          URL url = new URL(urls);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestProperty("Authorization", basicAuth);
          conn.setRequestMethod("GET");
          conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
          conn.setReadTimeout(5000);
          reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          while ((line = reader.read()) != -1) {
               responseContent.append((char)line);
          }
          parseIssue(responseContent);
          reader.close();
     }

     public  void parseIssue(StringBuilder response) {
          JSONObject responseBody = new JSONObject( response.toString());
          JSONArray arr = responseBody.getJSONArray("issues");
          for (int i = 0; i < arr.length(); i++)
          {
               if( i != 0 )
                    sonar_issues += "|" +"Line: "+ arr.getJSONObject(i).getInt("line")+"  " + arr.getJSONObject(i).getString("message");
               else
                    sonar_issues += "Line: "+ arr.getJSONObject(i).getInt("line")+"  " + arr.getJSONObject(i).getString("message");
          }
          sonar_value[14]=sonar_issues;
     }

     public  void GetSonarData(String name)throws Exception{
          BufferedReader reader;
          String line;
          StringBuilder responseContent = new StringBuilder();

          String urls = "{Sonar-url}/api/measures/component?component=edu.selab:"+name+"&metricKeys=sqale_rating,security_rating,security_review_rating,reliability_rating,code_smells,bugs,vulnerabilities";
          //System.out.println(urls);
          String token = "{Sonar-token}"+":";
          String basicAuth = "Basic "+new String(Base64.getEncoder().encode(token.getBytes("UTF-8")));

          URL url = new URL(urls);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

     public  void parseData(StringBuilder response) {
          JSONObject responseBody = new JSONObject( response.toString());
          sonar_value[8] = responseBody.getJSONObject("component").getString("name");
          JSONArray arr = responseBody.getJSONObject("component").getJSONArray("measures");
          for (int i = 0; i < arr.length(); i++)
          {
               sonar_property[i] = arr.getJSONObject(i).getString("metric");
               sonar_value[i] = arr.getJSONObject(i).getString("value");
               if(sonar_property[i].equals("security_rating")){
                    index[0]=Double.parseDouble(sonar_value[i]);
               }else if(sonar_property[i].equals("reliability_rating")){
                    index[1]=Double.parseDouble(sonar_value[i]);
               }else if(sonar_property[i].equals("sqale_rating")){
                    index[2]=Double.parseDouble(sonar_value[i]);
               }else if(sonar_property[i].equals("security_review_rating")){
                    index[3]=Double.parseDouble(sonar_value[i]);
               }
          }
     }
}

