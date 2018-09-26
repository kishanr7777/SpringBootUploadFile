package org.o7.planning.sbfileupload.form;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {
	
	private String description;
	
	//upload files
	private MultipartFile[] fileDatas;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(MultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}
	
	

}
