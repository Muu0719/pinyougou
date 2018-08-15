package com.pinyougou.shop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pinyougou.common.FastDFSClient;
import com.pinyougou.common.Result;

@RestController
@RequestMapping("upload")
public class UploadController {

	//@Value注解是读取外部属性文件中的数据，根据Key来找Value并映射到变量上！
	@Value("${IMAGE_URL}")
	private String IMAGE_URL;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result uploadFile(MultipartFile file){
		try {
			//1、获取文件全名
			String filename = file.getOriginalFilename();
			//2、获取扩展名:lijin.henshuai.png
			String ext = filename.substring(filename.lastIndexOf(".")+1);
			//3、上传文件，返回Path
			FastDFSClient client = new FastDFSClient("classpath:config/tracker_server.conf");
			String path = client.uploadFile(file.getBytes(), ext);
			String url = IMAGE_URL + path;
			//4、封装返回结果
			return new Result(true, url);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
	}
	
}
