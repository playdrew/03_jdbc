package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application4 {

    public static void main(String[] args) {

        /* title. 전체 사원 정보를 EmployeeDTO 를 통해 객체에 담아서 출력
        *   */

        Connection con = getConnection();

        Statement stmt = null;

        ResultSet rset = null;

        // 회원 한 명의 정보를 담을 DTO
        EmployeeDTO emp = null;
        // 한명의 정보들을 하나의 인스턴스로 묶기 위한 List
        List<EmployeeDTO> empList = null; // 반복문 밖에서 생성한 이유가 있다. 반복문안에 있으면 arraylist 가 생성되기 때문에

        String query = "SELECT * FROM EMPLOYEE";

        try {
            stmt = con.createStatement();

            rset = stmt.executeQuery(query);

            empList = new ArrayList<>();

            while(rset.next()){
                emp = new EmployeeDTO(); // 인스턴스 생성하는 구문 ; 값 담고 돌고 인스턴스 만들고 값 담고 돌고 인스턴스 만들고
                emp.setEmpId(rset.getString("EMP_ID"));
                emp.setEmpName(rset.getString("EMP_NAME"));
                emp.setEmpNo(rset.getString("EMP_NO"));
                emp.setEmail(rset.getString("EMAIL"));
                emp.setPhone(rset.getString("PHONE"));
                emp.setDeptCode(rset.getString("DEPT_CODE"));
                emp.setJobCode(rset.getString("JOB_CODE"));
                emp.setSalLevel(rset.getString("SAL_LEVEL"));
                emp.setSalary(rset.getInt("SALARY"));
                emp.setBonus(rset.getDouble("BONUS"));
                emp.setManagerId(rset.getString("MANAGER_ID"));
                emp.setHireDate(rset.getDate("HIRE_DATE"));
                emp.setEntDate(rset.getDate("ENT_DATE"));
                emp.setEntYn(rset.getString("ENT_YN"));

                empList.add(emp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
            close(stmt);
            close(rset);
        }

        for(EmployeeDTO oneEmployee : empList){
            System.out.println("oneEmployee = " + oneEmployee);
        }
    }
}

// statement 란 sql 을 실행하는 문입니다.
// 부모와 자식을 구분하기 좋은 것은 exception 클래스가 있으면 nullPointException 자식인것처럼 
// statement 가 부모 preparedStatement 가 자식 더 많은 기능 가짐
// 스트링타입으로 쿼리문을 보내면 마이에스큐엘은 문자열을 제거하거 쿼리문을 만들고 실행
// 마이에큐엘은 문자열 "" 을 제거하고 실행합니다 이게 바로 파싱이라는 작업 번역
// 똑같은 쿼리문을 보내면 그전에 파싱했던 것을 기억해서 캐싱을 합니다.
// 프리페어스테이트먼트는 캐싱작업을 할 수 있어요

// 쿼리문자열 보내면 번역
// 2회차 호출시에는 저장된 정보를 가져오는 것이에요
