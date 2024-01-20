//package com.refore.our.member.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Slf4j
//public class MyFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        if (req.getMethod().equals("POST")) {
//            String headerAuth = req.getHeader("Authorization");
//            System.out.println("headerAuth = " + headerAuth);
//            if(headerAuth.equals("token")){
//                System.out.println("헤더에 토큰있다");
//                chain.doFilter(req,res);
//            }else{
//                System.out.println("헤더에 토큰없다");
//                PrintWriter out = res.getWriter();
//                out.println("no");
//                chain.doFilter(req,res);
//            }
//
//        }
//    }
//}
