package rrx.cnuo.cncommon.vo;

import org.springframework.util.StringUtils;

public enum FileType {
	PIC(0), VIDEO(1);

	private int type;
	private FileType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public static FileType valueOf(Integer type ) {
		if ( null == type ) return FileType.PIC;
		if ( type == 0 ) return FileType.PIC;
		if ( type == 1 ) return FileType.VIDEO;
		return FileType.PIC;
	}
	
	public static FileType valueOfFileName(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}
		int index = fileName.lastIndexOf(".");
		String suffix = fileName;
		if ( index >= 0 ) {
			suffix = fileName.substring(index+1);
		}
		if ( "jpg".equalsIgnoreCase(suffix) || "jpeg".equalsIgnoreCase(suffix) 
			|| "bmp".equalsIgnoreCase(suffix) || "tiff".equalsIgnoreCase(suffix)
			|| "gif".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix) ) {
			return FileType.PIC;
		}
		if ( "aiff".equalsIgnoreCase(suffix) || "avi".equalsIgnoreCase(suffix) 
			|| "mov".equalsIgnoreCase(suffix) || "mpeg".equalsIgnoreCase(suffix) 
			|| "mpg".equalsIgnoreCase(suffix) || "qt".equalsIgnoreCase(suffix) 
			|| "ram".equalsIgnoreCase(suffix) || "viv".equalsIgnoreCase(suffix) 
			|| "mp4".equalsIgnoreCase(suffix) || "wmv".equalsIgnoreCase(suffix)
			|| "mpeg1".equalsIgnoreCase(suffix) || "mpeg2".equalsIgnoreCase(suffix) 
			|| "mpg4".equalsIgnoreCase(suffix) || "3gp".equalsIgnoreCase(suffix) 
			|| "mkv".equalsIgnoreCase(suffix) || "flv".equalsIgnoreCase(suffix) 
			|| "rmvb".equalsIgnoreCase(suffix) || "vob".equalsIgnoreCase(suffix)
			|| "swf".equalsIgnoreCase(suffix) ) {
			return FileType.VIDEO;
		}
		return null;
	}
	
}
