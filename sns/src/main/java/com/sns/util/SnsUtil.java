package com.sns.util;

import java.io.File;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

public class SnsUtil {
	public static String uploadFile(String originalName, byte[] fileData, String path) throws Exception {
		UUID uuid = UUID.randomUUID();
		String saveName = uuid.toString() + "_" + originalName;
		File target = new File(path, saveName);
		FileCopyUtils.copy(fileData, target);
		return saveName;
	}
}
