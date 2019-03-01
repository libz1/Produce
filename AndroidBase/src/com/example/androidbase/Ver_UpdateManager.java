package com.example.androidbase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class Ver_UpdateManager extends Activity {
	private Context mContext;
	private String upmsg = "有新版本可更新";
	private String apkurl = "DataAcqu.apk";
	// 提示窗口
	private Dialog noticeDialog;
	// 下载窗口
	private Dialog downloadDialog;
	private static final String savePath = "/sdcard";
	// 进度条
	private ProgressBar mProgress; // =(ProgressBar)
									// this.findViewById(R.id.progress);
	// 状态常量
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	// 进度信息
	private int progress = 0;
	// 下载线程
	private Thread downLoadThread;
	// 状态常量
	private boolean interceptFlag = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				// 设置进度条的进度信息
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			default:
				break;
			}
		};
	};
	
	String v_ip,v_usr,v_pwd;
	int v_port ;
	int v_ver_progress_id,v_progress_id;
	public void setParam(String ip,int port,String usr,String pwd,int ver_progress_id,int progress_id){
		v_ip = ip;
		v_usr = usr;
		v_pwd = pwd;
		v_port = port;
		v_ver_progress_id = ver_progress_id; 
		v_progress_id = progress_id;
//		View v = inflatger.inflate(R.layout.ver_progress, null);
//		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		
		
	}

	/**
	 * 执行更新操作
	 * 
	 * @throws Exception
	 */
	public void checkUpdataInfo(String msg) throws Exception {
		showNoticeDialog(msg);
	}
	
	/**
	 * 执行更新操作
	 * 
	 * @throws Exception
	 */
	// 升级程序将在多个程序中使用
	public void checkUpdataInfo(String msg, String apk_name) throws Exception {
		apkurl = apk_name;
		showNoticeDialog(msg+" "+apkurl);
		
	}

	/**
	 * 执行下载的子线程
	 * 
	 */
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				// 创建FTPClient单例
				Ver_FTPUtils ftp = Ver_FTPUtils.getInstance();
				// 连接ftp
				String IP = v_ip;
				int port = v_port;
				String usr = v_usr;
				String pwd = v_pwd;
				FTPClient ftpT = ftp.initFTPSetting(IP, port, usr, pwd);
				// 连接状态判断
				if (ftpT.getReplyCode() == 230) {
					System.out.println("ftp连接成功");
				} else {
					System.out.println("ftp连接失败，请检查输入信息");
					return;
				}
				// 更改ftp工作空间
				ftpT.changeWorkingDirectory("/update");

				// 二进制上传
				ftpT.setFileType(FTP.BINARY_FILE_TYPE);
				// 设置字符集
				ftpT.setControlEncoding("UTF-8");
				ftpT.enterLocalPassiveMode();
				FTPFile[] f = ftpT.listFiles(apkurl);
				long size = f[0].getSize();
				System.out.println("Android文件大小：" + size);
				String saveFileName = savePath + "//" + apkurl;
				File localFile = new File(saveFileName);
				if (localFile.exists()) {
					localFile.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(localFile);
				InputStream is = ftpT.retrieveFileStream(apkurl);
				int n = 0;
				byte buf[] = new byte[1024];
				// 下载
				do {
					int numread = is.read(buf);
					n += numread;
					progress = (int) (((float) n / size) * 100);
					System.out.println("已经下载：" + progress + "%");
					// 发送下载进度消息
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 发送下载完成消息
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);
				is.close();
				fos.close();
				ftpT.logout();
				ftpT.disconnect();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	/**
	 * Description:带参构造函数
	 * 
	 * @param context由主activity传入Context类的成员
	 * @param type
	 *            :Context
	 */
	public Ver_UpdateManager(Context context) {
		this.mContext = context;
	}

	private void downloadApk() {

		// 启动新线程执行下载
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
		System.out.println("下载中");
	}

	/**
	 * 在本地执行安装操作
	 * 
	 */
	private void installApk() {
		String saveFileName = savePath + "//" + apkurl;
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		
		// langjp 2014.10.18 安装新版程序后，弹窗允许用户再次启动程序
		//参考：http://bbs.csdn.net/topics/390398395
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
		
		
	}

	/**
	 * 下载弹窗 点击取消则弹窗消失且取消下载
	 * 
	 */
	private void showDownloadDialog() {

		// 1、初始化下载进度窗口，此窗口包含取消下载功能按钮
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("版本更新");
		final LayoutInflater inflatger = LayoutInflater.from(mContext);
		View v = inflatger.inflate(v_ver_progress_id, null);
		mProgress = (ProgressBar) v.findViewById(v_progress_id);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dig, int which) {
				dig.dismiss();

				// 设置取消标志，在接收文件线程中会检测，并停止
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();

		// 2、启动下载
		System.out.println("程序验证2");
		downloadApk();

	}

	/**
	 * 提示版本更新的弹窗 选择下载弹窗消失且出现下载窗口 选择取消弹窗消失
	 */
	private void showNoticeDialog(String msg) {

		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("版本更新 "+msg);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(upmsg);
		builder.setPositiveButton("下载", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dig, int which) {
				dig.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dig, int which) {
				// TODO Auto-generated method stub
				dig.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}
}