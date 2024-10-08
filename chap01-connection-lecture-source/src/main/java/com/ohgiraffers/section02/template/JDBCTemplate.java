package com.ohgiraffers.section02.template;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {

    /* title. jdbc 커넥션 정보를 하나의 틀로 만들어 필요한 곳에서 호출 */
    // 싱글톤 메소드 : 공유하기 위한 목적으로 메소드를 만들었다 .
    public static Connection getConnection(){

        Connection con = null;

        Properties prop = new Properties();

        try {

            prop.load(new FileReader("src/main/java/com/ohgiraffers/config/connection-info.properties"));

            String driver =  prop.getProperty("driver");
            String url = prop.getProperty("url");

            Class.forName(driver);

            con = DriverManager.getConnection(url,prop);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } // finally close 하면 안된다.

        return con;
    }

    public static void close(Connection con){

        //비트연산자 => 좌항과 우항을 모두 비교
        //&는 &&는 왼쪽항이 false 면 뒤에 항은 비교하지도 않는데
        //비트연산자 &는 왼쪽항이 false 도 둘다 비교한다
        try {
            if(con != null & !con.isClosed()){
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

// 어플리케이션과 dbms 를 연결하기 위한 것을 jdbc 라고 부릅니다.
// jdbc 에서 가장 중요한 것은 연결 connection 입니다
// 어플리케이션에서 작성한 쿼리를 데이터베이스에서 실행해줘 statement
// 실행후에 결과문을 돌려주는 과정을 resultSet 으로 담아줍니다.
// 연결통로 만들고 쿼리문 보내주고 결과를 담아주고 3가지 과정이 있습니다.

