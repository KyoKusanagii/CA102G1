package com.emp.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class empLoginFilter implements Filter {
	private static final long serialVersionUID = 1L;
	
	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// �i���o session�j
		HttpSession session = req.getSession();
		// �i�q session �P�_�����u�O�_�n�J�L�j
		Object emp = session.getAttribute("emp");
		if (emp == null) {
			res.sendRedirect(req.getContextPath() + "/BackEnd/Login.jsp");
			return;
		}else {
			chain.doFilter(req,res);	//�o�椣���
		}
	}
}