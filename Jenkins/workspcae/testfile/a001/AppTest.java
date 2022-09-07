package edu.selab;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 Unit test for simple App.
 */
public class AppTest{
@Test
public  void  testMain1() {
String ss =  "2";
assertEquals(ss,App.main("3"));
}
@Test
public  void  testMain2() {
String ss =  "8";
assertEquals(ss,App.main("6"));
}
@Test
public  void  testMain3() {
String ss =  "1548008755920";
assertEquals(ss,App.main("60"));
}
@Test
public  void  testMain4() {
String ss =  "2504730781961";
assertEquals(ss,App.main("61"));
}
@Test
public  void  testMain5() {
String ss =  "12586269025";
assertEquals(ss,App.main("50"));
}
@Test
public  void  testMain6() {
String ss =  "3524578";
assertEquals(ss,App.main("33"));
}
@Test
public  void  testMain7() {
String ss =  "1779979416004714189";
assertEquals(ss,App.main("89"));
}
@Test
public  void  testMain8() {
String ss =  "5527939700884757";
assertEquals(ss,App.main("77"));
}
@Test
public  void  testMain9() {
String ss =  "8944394323791464";
assertEquals(ss,App.main("78"));
}
@Test
public  void  testMain10() {
String ss =  "14472334024676221";
assertEquals(ss,App.main("79"));
}
}