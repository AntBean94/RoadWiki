package com.web.blog.model.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.lang.Assert;

@Service
public class FileServiceImpl implements FileService {
	
	@Override
	public Object uploadImg(MultipartFile file, HttpServletRequest request, int uid) throws Exception {
		// 파일이 존재하면 삭제
		try {
			String filename = fileSearch("./target/classes/statics/upload/" + uid, 1);
			Thread.sleep(1500);// 삭제가 쓰레드형태로 같이 진행되는거같아서 임시로 슬립
			filename = fileSearch("./target/classes/statics/upload/" + uid, 1);
		} catch (Exception e) {
			System.out.println("no folder");
		}
		Thread.sleep(1500);// 삭제가 쓰레드형태로 같이 진행되는거같아서 임시로 슬립
		// parent directory를 찾는다.
		Path directory = Paths.get(".\\target\\classes\\statics\\upload\\" + uid + "\\").toAbsolutePath().normalize();

		// directory 해당 경로까지 디렉토리를 모두 만든다.
		Files.createDirectories(directory);

		// 파일명을 바르게 수정한다.
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
		Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");

		// 파일을 저장할 경로를 Path 객체로 받는다.
		Path targetPath = directory.resolve(fileName).normalize();

		// 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
		Assert.state(!Files.exists(targetPath), fileName + " File alerdy exists.");

		file.transferTo(targetPath);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "sucess");
		result.put("path", targetPath);
		System.out.println(targetPath);
		return result;
	}

	public String fileSearch(String path, int type) throws Exception {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		String str = "";
		for (int i = 0; i < fileList.length; i++) {
			File file = fileList[i];

			if (file.isFile()) {
				System.out.println(type == 1 ? "삭제 : " : "조회 : " + file.getName());
				if (type == 1) { // 1 이면 기존파일이 있으면 삭제 이후 업로드
					file.delete();
				} else { // 0 이면 파일 이름 반환해서 이미지 파일 출력
					str = file.getName();
				}
//				if (!file.getName().equals(".DS_Store")) {
//					System.out.println("파일 : " + file.getName());
//					writer.write(file.getName() + "\n");
//					writer.flush();
//				}
			}
//			else if (file.isDirectory()) {
//				fileSearch(file.getCanonicalPath().toString());
//			}
		}
		return str;
	}

	@Override
	public byte[] showImg(String uid, HttpServletRequest request) throws Exception {
		System.out.println("image uid : " + uid);
		String filename = "";
		try {
			filename = fileSearch("./target/classes/statics/upload/" + uid, 0);
		} catch (Exception e) {
			System.out.println("no folder");
		}
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream("/statics/upload/" + uid + "/" + filename);
			if (in == null) {
				in = getClass().getResourceAsStream("/default.png");
			}
		} catch (Exception e) {
			in = getClass().getResourceAsStream("/default.png");
		}
		return IOUtils.toByteArray(in);
	}
	
//	@GetMapping(value = "image")
//	public Object userSearch() throws IOException {
//		Path file = Paths.get(".\\src\\main\\resources\\statics\\upload\\" + 1 + "\\").resolve("1.jpg");
//		Resource resource = new UrlResource(file.toUri());
//		if (resource.exists() || resource.isReadable()) {
//			System.out.println("파일 리소스 나오긴 함 @@@@@@@@@@@@@@@@@@");
//		}
//
//		InputStream imageStream = new FileInputStream(
//				".\\src\\main\\resources\\statics\\upload\\" + 1 + "\\" + "1.jpg");
//		System.out.println("File null? : " + imageStream);
//		byte[] imageByteArray = IOUtils.toByteArray(imageStream);
//		imageStream.close();
//
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("image", imageByteArray);
//		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
//	}

	@Override
	public Object uploadbgImg(MultipartFile file, HttpServletRequest request, int uid) throws Exception {
		// 파일이 존재하면 삭제
		try {
			String filename = fileSearch("./target/classes/statics/bgupload/" + uid, 1);
			Thread.sleep(1500);// 삭제가 쓰레드형태로 같이 진행되는거같아서 임시로 슬립
			filename = fileSearch("./target/classes/statics/bgupload/" + uid, 1);
		} catch (Exception e) {
			System.out.println("no folder");
		}
		Thread.sleep(1500);// 삭제가 쓰레드형태로 같이 진행되는거같아서 임시로 슬립
		// parent directory를 찾는다.
		Path directory = Paths.get(".\\target\\classes\\statics\\bgupload\\" + uid + "\\").toAbsolutePath().normalize();

		// directory 해당 경로까지 디렉토리를 모두 만든다.
		Files.createDirectories(directory);

		// 파일명을 바르게 수정한다.
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
		Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");

		// 파일을 저장할 경로를 Path 객체로 받는다.
		Path targetPath = directory.resolve(fileName).normalize();

		// 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
		Assert.state(!Files.exists(targetPath), fileName + " File alerdy exists.");

		file.transferTo(targetPath);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "sucess");
		return result;
	}

	@Override
	public byte[] showbgImg(String uid, HttpServletRequest request) throws Exception {
		System.out.println("image uid : " + uid);
		String filename = "";
		try {
			filename = fileSearch("./target/classes/statics/bgupload/" + uid, 0);
		} catch (Exception e) {
			System.out.println("no folder");
		}
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream("/statics/bgupload/" + uid + "/" + filename);
			if (in == null) {
				in = getClass().getResourceAsStream("/defaultbg.png");
			}
		} catch (Exception e) {
			in = getClass().getResourceAsStream("/defaultbg.png");
		}
		return IOUtils.toByteArray(in);
	}
}
