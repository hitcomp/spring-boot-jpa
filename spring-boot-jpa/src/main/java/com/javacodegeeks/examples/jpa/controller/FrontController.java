//
//package com.javacodegeeks.examples.jpa.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.javacodegeeks.examples.jpa.model.Response;
//import com.javacodegeeks.examples.jpa.model.Users;
//import com.javacodegeeks.examples.jpa.service.UserLoginServiceInt;
//
//@Component
//@RestController
//public class FrontController extends HandlerInterceptorAdapter {
//
//	@Autowired
//	HttpSession session;
//
//	@Autowired
//	private UserLoginServiceInt loginservice;
//	Response<Users> responsese = null;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
//
//		HttpSession session = request.getSession(false);
//
//		String path = request.getRequestURI().substring(request.getContextPath().length());
//
//		if (path.equals("/User/login")) {
//
//			return true;
//		}
//		
//		
//		else if (session == null || session.getAttribute("sessionUser") == null) {
//			request.setAttribute("emassage", "login failed");
//			ObjectMapper mapper = new ObjectMapper();
//			Response res = new Response();
//			res.setMessage(HttpStatus.UNAUTHORIZED);
//			res.setStatus(false);
//			response.setContentType("application/json");
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.getWriter().write(mapper.writeValueAsString(res));
//			return false;
//
//		} else {
//			return true;
//		}
//
//	}
//
//}