package com.abelhzo.upload.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	@RequestMapping("/")
	public String inicio() {
		return "index";
	}

	@RequestMapping(value = "/uploadSingle", method = RequestMethod.POST)
	public String uploadSingleFile(HttpServletRequest request, @RequestParam("file1") MultipartFile fileUpload) {

		String ruta = createFile(request).toString();

		System.out.println(ruta);
		System.out.println(fileUpload.getOriginalFilename());
		System.out.println(fileUpload.getContentType());
		System.out.println(fileUpload.getSize());

		File file = new File(ruta + File.separator + fileUpload.getOriginalFilename());

		try {
			fileUpload.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";

	}

	private File createFile(HttpServletRequest request) {
		String path = request.getServletContext().getRealPath("/") + "\\WEB-INF\\uploads";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@RequestMapping(value = "/uploadMultiple", method = RequestMethod.POST)
	private String uploadMultipleFile(HttpServletRequest request, @RequestParam("files") MultipartFile[] filesUpload) {
			
		System.out.println(filesUpload.length);
		if(filesUpload.length != 0) {
			for(MultipartFile file : filesUpload) {
				System.out.println(file.getOriginalFilename());
				System.out.println(file.getContentType());
				System.out.println(file.getSize());
			}
		}
		
		return "success";
	}

}
