package com.example.androidbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Ver_FTPUtils {
	FTPClient ftpClient = null;
	private static Ver_FTPUtils ftpUtilsInstance = null;

	// private String FTPUrl;
	// private int FTPPort;
	// private String UserName;
	// private String UserPassword;

	private Ver_FTPUtils() {
		ftpClient = new FTPClient();
	} // 得到类对象实例（因为只能有一个这样的类对象，所以用单例模式）

	public static Ver_FTPUtils getInstance() {
		if (ftpUtilsInstance == null) {
			ftpUtilsInstance = new Ver_FTPUtils();
		}
		return ftpUtilsInstance;
	}

	/**
	 * * 设置FTP服务器 * @param FTPUrl FTP服务器ip地址 * @param FTPPort FTP服务器端口号 * @param
	 * UserName 登陆FTP服务器的账号 * @param UserPassword 登陆FTP服务器的密码 * @return
	 */
	public FTPClient initFTPSetting(String FTPUrl, int FTPPort,
			String UserName, String UserPassword) {
		// this.FTPUrl = FTPUrl;
		// this.FTPPort = FTPPort;
		// this.UserName = UserName;
		// this.UserPassword = UserPassword;
		int reply;
		try { // 1.要连接的FTP服务器Url,
			ftpClient.connect(FTPUrl, FTPPort);
			// 2.登陆FTP服务器
			ftpClient.login(UserName, UserPassword);
			// 3.看返回的值是不是230，如果是，表示登陆成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				// 断开
				ftpClient.disconnect();
			}
			return ftpClient;
		} catch (SocketException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftpClient;
	}

	/**
	 * * 上传文件 * @param FilePath 要上传文件所在SDCard的路径 * @param FileName
	 * 要上传的文件的文件名(如：Sim唯一标识码) * @return true为成功，false为失败
	 */
	public boolean uploadFile(String FilePath, String FileName) {

		try {
			// 设置存储路径
			ftpClient.makeDirectory("/data");
			ftpClient.changeWorkingDirectory("/data");
			// 设置上传文件需要的一些基本信息
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 文件上传吧～
			FileInputStream fileInputStream = new FileInputStream(FilePath);
			ftpClient.storeFile(FileName, fileInputStream);
			// 关闭文件流
			fileInputStream.close();
			// 退出登陆FTP，关闭ftpCLient的连接
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * * 下载文件 * @param FilePath 要存放的文件的路径 * @param FileName 远程FTP服务器上的那个文件的名字 * @return
	 * true为成功，false为失败
	 */
	public boolean downLoadFile1(String FilePath, String FileName) {

		try {
			// 转到指定下载目录
			ftpClient.changeWorkingDirectory("/update");
			// 列出该目录下所有文件
			FTPFile[] files = ftpClient.listFiles();
			System.out.println("文件数量" + files.length);
			// 遍历所有文件，找到指定的文件
			for (FTPFile file : files) {
				System.out.println("+++==+++" + file.getName());
				if (file.getName().equals(FileName)) {
					// 根据绝对路径初始化文件
					File localFile = new File(FilePath);

					// 输出流
					OutputStream outputStream = new FileOutputStream(localFile);
					// 下载文件
					ftpClient.retrieveFile(file.getName(), outputStream);
					// 关闭流
					outputStream.close();

				}
			}
			// 退出登陆FTP，关闭ftpCLient的连接
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 从FTP服务器端，获取某个路径下文件的InputStream
	public static InputStream getFtpStream(String IP, int Port, String ftpusr,
			String pwd, String fileName, String path) throws Exception {

		// 1、从FTP获取update目录下的xml文件
		Ver_FTPUtils u = Ver_FTPUtils.getInstance();
		FTPClient ftp = u.initFTPSetting(IP, Port, ftpusr, pwd);
		path = "/" + path;
		ftp.changeWorkingDirectory(path);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.setControlEncoding("UTF-8");
		ftp.enterLocalPassiveMode();
		InputStream is = ftp.retrieveFileStream(fileName);
		return is;
	}

}
