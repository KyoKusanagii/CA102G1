package com.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.carousel.model.CarouselService;
import com.carousel.model.CarouselVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/MainRollImage")
public class MainRollImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainRollImage() {
        super();

    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		
		
		String outStr = "";
		List<byte[]> rollImages = new ArrayList<>();
		
		CarouselService service = new CarouselService();
		List<CarouselVO> carousels = service.getAll();
		for(CarouselVO carousel : carousels) {
			String image = gson.toJson(carousel.getCarousel_pic());
			rollImages.add(gson.fromJson(image, byte[].class));
		}
		
		outStr = gson.toJson(rollImages);

		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(outStr);		
		
	}

}
