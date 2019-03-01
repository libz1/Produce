package com.example.androidbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;

/**
 * 文件读写工具类
 * 
 * @author 
 * 
 */

public class AndrdFileIO {

	static Context context;

	// 一次读取文件的大小
	static final int READ_BLOCK_SIZE = 2000;

	public AndrdFileIO(Context c) {
		context = c;
	}

	// 获取\data\data\...\files目录下的文件列表
	// 参考http://www.2cto.com/kf/201301/186614.html
	public String[] getFiles() {
		String[] s = context.fileList();
		for (String a : s) {
			System.out.println(a);
		}
		return s;
	}

	//  2014.10.10 得到指定目录下的所有文件
	public String[] getFiles(String dir) {

		File Directory = context.getDir(dir, Context.MODE_PRIVATE);
		File[] entries = Directory.listFiles();
		int num = entries.length;
		String[] s = new String[num];
		for (int i = 0; i < entries.length; i++) {
			s[i] = entries[i].getName();
			System.out.println("AndrdFileIO-getFiles(String dir) filename:"
					+ s[i]);
		}

		// String[] s = context.fileList();
		// for (String a : s) {
		// System.out.println(a);
		// }
		return s;
	}

	// // 读取res\raw目录下的文件
	// public String readFromRaw(int id) {
	// // 1、读取res\raw目录下的资源 ID是文件名，不带扩展名
	// InputStream is = context.getResources().openRawResource(id);
	// // 2、创建读取对象BufferedReader
	// BufferedReader br;
	// String ret = "";
	// try {
	// //  2014.04.18 按照gb2312编码读取数据
	// // 参考 http://blog.sina.com.cn/s/blog_90cdca4c01012epl.html
	// br = new BufferedReader(new InputStreamReader(is,"gb2312"));
	// String str = null;
	// // 3、逐行读取，直到文件末尾
	// while ((str = br.readLine()) != null) {
	// ret = ret + str;
	// }
	// // 4、关闭文件；关闭读取对象
	// is.close();
	// br.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return ret;
	//
	// }

	// 读取res\raw目录下的文件
	static int READ_SIZE = 10000;

	public String readFromRaw(int id) {
		// 1、读取res\raw目录下的资源 ID是文件名，不带扩展名
		InputStream is = context.getResources().openRawResource(id);
		// 2、创建读取对象BufferedReader
		BufferedReader br;
		String ret = "";
		try {
			//  2014.04.18 按照gb2312编码读取数据
			// 参考 http://blog.sina.com.cn/s/blog_90cdca4c01012epl.html
			br = new BufferedReader(new InputStreamReader(is, "gb2312"));
			// String str = null;
			// 3、逐行读取，直到文件末尾

			// while ((str = br.readLine()) != null) {
			// ret = ret + str;
			// }

			int charRead;
			char[] inputBuffer = new char[READ_SIZE];
			// 4、循环读取，如果到达文件末尾，read会返回-1
			while ((charRead = br.read(inputBuffer)) > 0) {
				// ---convert the chars to a String---
				// 5、将char数组中的数据转为strig
				String readString = String
						.copyValueOf(inputBuffer, 0, charRead);
				ret += readString;
				// 5、每次读取数据前，清空读取缓冲区内容
				inputBuffer = new char[READ_SIZE];
			}
			// 4、关闭文件；关闭读取对象
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;

	}

	// 根据传入的参数进行文件读取:文件名、保存内容、保存类型（覆盖、追加）、是否存储到SD卡
	// 返回字符串类型的数据
	public String readFromFile(String fileName, int fileType, String dir) {
		String ret = "";
		InputStreamReader isr = null;
		FileInputStream fIn = null;
		try {

			if (fileType == 1) {
				// dir 格式举例 "||Not GB" 表示根目录下非国标

				String path = Environment.getExternalStorageDirectory()
						+ File.separator;

				if (!dir.split("||")[0].equals(""))
					path += dir.split("||")[0] + File.separator;
				fileName = path + fileName;
				File file = new File(fileName);

				// 从SD Storage提取文件
				// File sdCard = Environment.getExternalStorageDirectory();
				// File directory = new File(sdCard.getAbsolutePath() +
				// "/"+dir);
				// File file = new File(directory, fileName);

				//  2014.10.23 添加文件是否存在的判断
				if (!file.exists()) {
					System.out.println("readFromFile:" + fileName
							+ " file not exists ");
					return "";
				}
				fIn = new FileInputStream(file);

			} else if (fileType == 2) {
				// 从手机的内部存储区域提取文件

				// 1、打开文件

				String[] f = context.fileList();

				// 判断手机内部存储空间中是否存在文件
				Boolean fileexists = false;
				for (String s : f) {
					if (s.equals(fileName)) {
						fileexists = true;
						break;
					}
				}

				if (!fileexists)
					return "";
				fIn = context.openFileInput(fileName);

				// 2、定义文件读取流
			} else if (fileType == 3) {
				// 第3种模式，文件保存到手机的内部存储区域，指定目录下 app_dir
				//  2014.09.01
				// 参考
				// http://blog.csdn.net/yuansuruanjian/article/details/8075189
				File Directory = context.getDir(dir, Context.MODE_PRIVATE);
				File file = new File(Directory, fileName);
				//  2014.10.23 添加文件是否存在的判断
				if (!file.exists())
					return "";
				fIn = new FileInputStream(file);
			}

			// 根据目录参数进行字符集设定
			if (dir.equals("") || dir.indexOf("||") < 0) {
				isr = new InputStreamReader(fIn, "GB2312");
			} else {
				if (dir.split("||")[1].equals("Not GB"))
					isr = new InputStreamReader(fIn);
				else
					isr = new InputStreamReader(fIn, "GB2312");
			}

			// 3、按照一次性读取READ_BLOCK_SIZE字节来创建读取数据缓冲区
			char[] inputBuffer = new char[READ_BLOCK_SIZE];
			String s = "";

			int charRead;

			// 4、循环读取，如果到达文件末尾，read会返回-1
			while ((charRead = isr.read(inputBuffer)) > 0) {
				// ---convert the chars to a String---
				// 5、将char数组中的数据转为strig
				String readString = String
						.copyValueOf(inputBuffer, 0, charRead);
				s += readString;
			}
			// read---
			ret = s;
			isr.close();
			fIn.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return ret;
	}

	/*
	 * //  2014.10.23 判断手机内部存储空间中是否存在文件 private boolean fileExists(String
	 * fileName) { String[] f = context.fileList(); // 判断手机内部存储空间中是否存在文件 Boolean
	 * fileexists = false; for (String s : f) { if (s.equals(fileName)) {
	 * fileexists = true; break; } } return fileexists; }
	 */
	// 从内部存储区域读取文件，写入到外部SD卡同名文件
	//  需要调整
	public void readAndCopyToSDFile(String fileName) {
		try {
			// 外部SD卡路径信息
			String path = Environment.getExternalStorageDirectory()
					+ File.separator;

			// 写文件
			FileOutputStream fos = new FileOutputStream(path + fileName);

			// 读文件(读写“/data/data/<应用程序名>”中的文件)
			FileInputStream fis = context.openFileInput(fileName);

			// 3、按照一次性读取READ_BLOCK_SIZE字节来创建读取数据缓冲区
			byte[] inputBuffer = new byte[READ_BLOCK_SIZE];
			// String s = "";
			int charRead;
			while ((charRead = fis.read(inputBuffer)) > 0) {
				fos.write(inputBuffer, 0, charRead);
			}
			// 7、关闭文件
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 从内部存储区域读取文件，写入到外部SD卡同名文件
	//  需要调整
	public void readAndCopyToSDFile(String fileName, String dir) {
		try {
			// 外部SD卡路径信息
			String path = Environment.getExternalStorageDirectory()
					+ File.separator;

			// 写文件
			FileOutputStream fos = new FileOutputStream(path + fileName);

			// 读文件(读写“/data/data/<应用程序名>”中的文件)
			File Directory = context.getDir(dir, Context.MODE_PRIVATE);
			File file = new File(Directory, fileName);
			FileInputStream fis = new FileInputStream(file);

			// 3、按照一次性读取READ_BLOCK_SIZE字节来创建读取数据缓冲区
			byte[] inputBuffer = new byte[READ_BLOCK_SIZE];
			// String s = "";
			int charRead;
			while ((charRead = fis.read(inputBuffer)) > 0) {
				fos.write(inputBuffer, 0, charRead);
			}
			// 7、关闭文件
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * // char数组转为byte数组 //
	 * 参考http://blog.csdn.net/chenzhanhai/article/details/6367842 private byte[]
	 * getBytes(char[] chars) { Charset cs = Charset.forName("GB2312");
	 * CharBuffer cb = CharBuffer.allocate(chars.length); cb.put(chars);
	 * cb.flip(); ByteBuffer bb = cs.encode(cb); return bb.array(); }
	 */

	/*
	 * // byte转char //
	 * 参考http://blog.csdn.net/chenzhanhai/article/details/6367842 private char[]
	 * getChars(byte[] bytes) { Charset cs = Charset.forName("GB2312");
	 * ByteBuffer bb = ByteBuffer.allocate(bytes.length); bb.put(bytes);
	 * bb.flip(); CharBuffer cb = cs.decode(bb); return cb.array(); }
	 */
	// 根据传入的参数进行文件保存:文件名、保存内容、保存类型（覆盖、追加）、是否存储到SD卡
	// dir在type=2时表示子目录的名称
	public void writeToFile(String fileName, String content, int Type,
			String dir) {
		String str = content;
		FileOutputStream fOut = null;
		try {
			if (Type == 1) {

				// 第1种模式，文件保存在SD卡 mnt\sdcard\MyFiles\...
				// 1、获取SD卡的读写路径
				File sdCard = Environment.getExternalStorageDirectory();
				// 1、新建MyFiles目录
				File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
				directory.mkdirs();
				// 1、新建textfile.txt文件 不清楚文件读写模式
				File file = new File(directory, fileName);
				fOut = new FileOutputStream(file);

			} else if (Type == 2) {

				// 第2种模式，文件保存到手机的内部存储区域 保存在data\data\....\files\目录下
				fOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);

			} else if (Type == 3) {
				// 第3种模式，文件保存到手机的内部存储区域 保存在data\data\....\files\ 的某个XXXX 目录下
				File Directory = context.getDir(dir, Context.MODE_PRIVATE);
				File file = new File(Directory, fileName);

				fOut = new FileOutputStream(file);
			}

			// 2、定义流输出对象
			OutputStreamWriter osw = new OutputStreamWriter(fOut, "GB2312");
			// ---write the string to the file---
			// 3、写数据
			osw.write(str);
			// 4、保证所有字节写入了文件
			osw.flush();
			// 5、关闭文件
			osw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// 从SD目录下读取文件，写入到内部存储空间
	//  需要调整
	public void readSDFileAndCopy(String fileName) {
		try {

			// 写文件
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);

			// 读文件
			String path = Environment.getExternalStorageDirectory()
					+ File.separator;
			FileInputStream fis = new FileInputStream(path + fileName);

			// 3、按照一次性读取READ_BLOCK_SIZE字节来创建读取数据缓冲区
			byte[] inputBuffer = new byte[READ_BLOCK_SIZE];
			int charRead;
			// 4、循环读取，如果到达文件末尾，read会返回-1
			while ((charRead = fis.read(inputBuffer)) > 0) {
				fos.write(inputBuffer, 0, charRead);
			}
			fos.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// 从SD目录下读取文件，写入到内部存储空间
	//  需要调整
	public void readSDFileAndCopy(String fileName, String dir) {
		try {

			// 写文件
			File Directory = context.getDir(dir, Context.MODE_PRIVATE);
			File file = new File(Directory, fileName);
			FileOutputStream fos = new FileOutputStream(file);

			// 读文件
			String path = Environment.getExternalStorageDirectory()
					+ File.separator;
			FileInputStream fis = new FileInputStream(path + fileName);

			// 3、按照一次性读取READ_BLOCK_SIZE字节来创建读取数据缓冲区
			byte[] inputBuffer = new byte[READ_BLOCK_SIZE];
			int charRead;
			// 4、循环读取，如果到达文件末尾，read会返回-1
			while ((charRead = fis.read(inputBuffer)) > 0) {
				fos.write(inputBuffer, 0, charRead);
			}
			fos.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// 在内部存储区域中将一个文件复制为另一个文件
	public void saveAs(String path, String fileName, String fileName1) {
		try {

			//  2014.10.14 添加目录下文件复制的支持
			FileOutputStream fOut = null;
			FileInputStream fIn = null;

			if (path.equals("")) {
				// 1、打开目的文件
				// 表示替换原先的内容
				fOut = context.openFileOutput(fileName1, Context.MODE_PRIVATE);
				// 1、打开源文件
				fIn = context.openFileInput(fileName);
			} else {
				File Directory = context.getDir(path, Context.MODE_PRIVATE);
				File file1 = new File(Directory, fileName1);
				fOut = new FileOutputStream(file1);

				File file = new File(Directory, fileName);
				fIn = new FileInputStream(file);

			}

			// 2、定义流输出对象
			OutputStreamWriter osw = new OutputStreamWriter(fOut, "GB2312");
			// 2、定义文件读取流
			InputStreamReader isr = new InputStreamReader(fIn, "GB2312");

			// 3、按照一次性读取READ_BLOCK_SIZE字节来创建读取数据缓冲区
			char[] inputBuffer = new char[READ_BLOCK_SIZE];
			// String s = "";
			int charRead;
			// 4、循环读取，如果到达文件末尾，read会返回-1
			// String all = "";
			while ((charRead = isr.read(inputBuffer)) > 0) {
				// 5、char[]转为byte[]写入到fs

				osw.write(inputBuffer, 0, charRead);

				// 6、每次读取数据前，清空读取缓冲区内容
				inputBuffer = new char[READ_BLOCK_SIZE];
			}
			// 7、关闭文件
			osw.flush();
			osw.close();
			isr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 复制文件
	// 参考http://www.oschina.net/code/snippet_575610_23126

	// /////////////////////复制文件//////////////////////////////
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public boolean copyFile(String oldPath, String newPath) {
		boolean isok = true;
		try {
			// int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				// int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					// bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				fs.close();
				inStream.close();
			} else {
				isok = false;
			}
		} catch (Exception e) {
			// System.out.println("复制单个文件操作出错");
			// e.printStackTrace();
			isok = false;
		}
		return isok;
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public boolean copyFolder(String oldPath, String newPath) {
		boolean isok = true;
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			isok = false;
		}
		return isok;
	}

	public static void main(String[] args) {

	}

}
