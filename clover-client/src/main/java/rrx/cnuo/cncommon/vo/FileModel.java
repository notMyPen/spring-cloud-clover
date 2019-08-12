package rrx.cnuo.cncommon.vo;

import java.io.Serializable;

public class FileModel implements Serializable{
	
	private static final long serialVersionUID = 7179819726772682702L;

	private String originalFileName;
	
	private String fileName;

	private String fileWithLogoName;
	
	private String thumbFileName;
	
	private FileType fileType;

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getThumbFileName() {
		return thumbFileName;
	}

	public void setThumbFileName(String thumbFileName) {
		this.thumbFileName = thumbFileName;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getFileWithLogoName() {
		return fileWithLogoName;
	}

	public void setFileWithLogoName(String fileWithLogoName) {
		this.fileWithLogoName = fileWithLogoName;
	}
}
