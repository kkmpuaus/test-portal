package com.tradelink.biometric.r2fas.portal.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FilenameUtils;

public class FileUtils {

	public static String ImagetoBase64(byte[] data, String fileName) {
		StringBuilder sb = new StringBuilder();
		String ext = FilenameUtils.getExtension(fileName);
		String mimeType = null;
		
		switch(ext) {
			case "jp2" : mimeType = "image/x-jp2";
			case "pbm" : mimeType = "image/x-portable-bitmap";
			case "pgm" : mimeType = "image/x-portable-bitmap";
			case "ppm" : mimeType = "image/x-portable-bitmap";
			case "sr" : mimeType = "image/cmu-raster";
			case "ras" : mimeType = "image/cmu-raster";
			default : mimeType = "image/" + ext; 
		}
	
		sb.append("data:" + mimeType + ";base64,");
		sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(data, false)));
		return sb.toString();
	}

}
