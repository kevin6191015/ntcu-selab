package ntcu.selab.SpringServer.service;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class SonarQubeReportAnalyzer {
    private final String[] sonar_property=new String[14];
    private final String[] sonar_value=new String[14];
    private String postparm="";
    public void  GetJenkinsData(String name,String submit_time,String unit_test_result,String compile_result){
        sonar_property[11]="submit_times";
        sonar_property[12]="unit_test_result";
        sonar_property[13]="compile_result";
        sonar_value[11]=submit_time;
        sonar_value[12]=unit_test_result;
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
    }
    public  void  WriteData() throws Exception{
        String urls = "http://120.108.204.152:3000/api/sqreport/add";
        System.out.println("\n"+postparm);
        byte[] postData = postparm.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
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
        String urls = "http://120.108.204.152:9000/api/sources/raw?key=edu.selab:"+name+":src/main/java/edu/selab/App.java";
        String token = "ef304f59cfec3c82accdd9ade9983d97dd81cf36"+":";
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
        sonar_value[7]=responseContent.toString();
    }

    public  void GetSonarData(String name)throws Exception{
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        String urls = "http://120.108.204.152:9000/api/measures/component?component=edu.selab:"+name+"&metricKeys=sqale_rating,security_rating,security_review_rating,reliability_rating,code_smells,bugs,vulnerabilities";
        //System.out.println(urls);
        String token = "ef304f59cfec3c82accdd9ade9983d97dd81cf36"+":";
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
        }
        for(int i =0; i<11;i++){
            System.out.println(sonar_property[i]+": "+sonar_value[i] );
        }
    }
}
