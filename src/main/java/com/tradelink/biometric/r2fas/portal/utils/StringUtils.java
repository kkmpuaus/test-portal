package com.tradelink.biometric.r2fas.portal.utils;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.tradelink.biometric.r2fas.portal.constant.GalleryType;
import com.tradelink.biometric.r2fas.portal.constant.ImageType;

public class StringUtils {
	
	public static boolean isValidImageType(String str) {
		str = str.toUpperCase();
	    for (ImageType i : ImageType.values()) {
	        if (i.name().equals(str)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static boolean isValidGalleryType(String str) {
	    for (GalleryType g : GalleryType.values()) {
	        if (g.name().equals(str)) {
	            return true;
	        }
	    }
	    return false;
	}	
	
	public static boolean isNullOrEmpty(String str) { 
	    return str == null || str.trim().length() == 0;
	}

	public static String findStringValue(JsonNode node, String fieldName) {
		if (node.isArray() && node.size() > 0) {
			return findStringValue(node.get(0), fieldName);
		} else if (node.isObject()) {
			if (node.has(fieldName)) {
				return node.get(fieldName).asText();
			} else {
				Iterator<JsonNode> subNode = node.iterator();
				while (subNode.hasNext()) {
					String subNodeResult;
					if ((subNodeResult = findStringValue(subNode.next(), fieldName)) != null) {
						return subNodeResult;
					}
				}
			}
		}
		return null;
	}

}
