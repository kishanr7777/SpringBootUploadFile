package org.o7.planning.sbfileupload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.o7.planning.sbfileupload.form.MyUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyFileUploadController {

	@RequestMapping(value = "/")
	public String homePage() {
		return "index";
	}


	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
	public String uploadOneFileHandler(Model model) {
		MyUploadForm myUploadForm = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm);
		return "uploadOneFile";
	}


	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	public String uploadOneFileHandlerPost(HttpServletRequest request, Model model, @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm);
	}


	// GET: Show upload form page.
	@RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	public String uploadMultiFileHandler(Model model) {

		MyUploadForm myUploadForm = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm);

		return "uploadMultiFile";
	}

	// POST: Do Upload
	@RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm);

	}


	private String doUpload(HttpServletRequest request, Model model, MyUploadForm myUploadForm) {
		// TODO Auto-generated method stub
		
		String description = myUploadForm.getDescription();
		System.out.println("Description = "+description);
		
		//Root directory
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println("upload path = "+uploadRootPath);
		
		File uploadRootDir = new File(uploadRootPath);
		//create directory if not exists
		if(!uploadRootDir.exists())
			uploadRootDir.mkdirs();
		
		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		
		List<File> uploadFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		
		for(MultipartFile file: fileDatas) {
			
			//client file name
			String name = file.getOriginalFilename();
			System.out.println("Client file name = "+name);
			//create the file at server
			if(name != null && name.length()>0) {
				
				try {
					File serverFile = new File(uploadRootDir.getAbsolutePath()+File.separator+name);
					
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(file.getBytes());
					stream.close();
					
					uploadFiles.add(serverFile);
					System.out.println("Write file: "+serverFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Erro in File = "+name);
					failedFiles.add(name);
				}
				
			}
		}
		
		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadFiles);
		model.addAttribute("failedFiles", failedFiles);
		
		return "uploadResult";
	}

}
