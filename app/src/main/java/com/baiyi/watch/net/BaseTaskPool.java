package com.baiyi.watch.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseTaskPool {

	// task thread pool
	static private ExecutorService taskPool;

	// for HttpUtil.getNetType
	private Context context;

	public BaseTaskPool(Context context) {
		this.context = context;
		taskPool = Executors.newCachedThreadPool();
	}

	// http post task with params
	public void addTask(int taskId, String taskUrl, HashMap<String, String> taskArgs, HttpCallback baseTask, int delayTime) {
		try {
			taskPool.execute(new TaskThread(context, taskUrl, taskArgs, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	// http post task without params
	public void addTask(int taskId, String taskUrl, HttpCallback baseTask, int delayTime) {
		try {
			taskPool.execute(new TaskThread(context, taskUrl, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	// http post task with params files
	public void addTask(int taskId, String taskUrl, HashMap<String, String> taskArgs, HashMap<String, File> files, HttpCallback baseTask, int delayTime) {
		try {
			taskPool.execute(new TaskThread2(context, taskUrl, taskArgs, files, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	// custom task
	public void addTask(int taskId, HttpCallback baseTask, int delayTime) {
		try {
			taskPool.execute(new TaskThread(context, null, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	// task thread logic
	private class TaskThread implements Runnable {
		private Context context;
		private String taskUrl;
		private HashMap<String, String> taskArgs;
		private HttpCallback baseTask;
		private int delayTime = 0;

		public TaskThread(Context context, String taskUrl, HashMap<String, String> taskArgs, HttpCallback baseTask, int delayTime) {
			this.context = context;
			this.taskUrl = taskUrl;
			this.taskArgs = taskArgs;
			this.baseTask = baseTask;
			this.delayTime = delayTime;
		}

		@Override
		public void run() {
			try {
				String httpResult = null;
				// set delay time
				if (this.delayTime > 0) {
					Thread.sleep(this.delayTime);
				}
				try {
					// remote task
					if (this.taskUrl != null) {
						// init app client
						AppClient client = new AppClient(this.taskUrl);
						// if (HttpUtil.WAP_INT == HttpUtil.getNetType(context))
						// {
						// client.useWap();
						// }

						// http get
						if (taskArgs == null) {
							httpResult = client.get();
						} else {// http post
							httpResult = client.post(this.taskArgs);
						}

						System.out.println("");
					}
					Message message = new Message();
					if (httpResult != null && JsonUtil.isJson(httpResult)) {
						BaseMessage baseMessage = AppUtil.getMessage(httpResult, taskUrl);
						message.what = 1;
						message.obj = baseMessage;
					} else {
						message.what = 2;
						message.obj = httpResult;
					}
					handler.sendMessage(message);
				} catch (ConnectTimeoutException e) {
					handler.sendEmptyMessage(3);
				} catch (SocketTimeoutException e) {
					handler.sendEmptyMessage(4);
				} catch (Exception e) {
					handler.sendEmptyMessage(5);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(5);
			}
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1: // 成功
					BaseMessage baseMessage = (BaseMessage) msg.obj;
					baseTask.onComplete(baseMessage);
					break;
				case 2: // 服务器出错
					String str = (String) msg.obj;
					//System.out.println("cccccc-->" + str);
					baseTask.onError(str);
					break;
				case 3: // 连接出错
					baseTask.onError("无法连接到服务器！");
					break;
				case 4: // 网络出错
					baseTask.onError("连接超时！");
					break;
				case 5: // 网络出错
					baseTask.onError("其它错误！");
					break;
				}
			};
		};
	}

	// 带文件
	private class TaskThread2 implements Runnable {
		private Context context;
		private String taskUrl;
		private HashMap<String, String> taskArgs;
		private HashMap<String, File> files;
		private HttpCallback baseTask;

		public TaskThread2(Context context, String taskUrl, HashMap<String, String> taskArgs, HashMap<String, File> files, HttpCallback baseTask, int delayTime) {
			this.context = context;
			this.taskUrl = taskUrl;
			this.taskArgs = taskArgs;
			this.baseTask = baseTask;
			this.files = files;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1: // 成功
					BaseMessage baseMessage = (BaseMessage) msg.obj;
					baseTask.onComplete(baseMessage);
					break;
				case 2: // 服务器出错
					String str = (String) msg.obj;
					baseTask.onError(str);
					break;
				case 3: // 网络出错
					baseTask.onError("服务器异常！");
					break;
				}
			};
		};

		@Override
		public void run() {
			try {
				AppClient.postWithFile(taskUrl, taskArgs, files, baseTask, handler);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				baseTask.onError("网络错误！");
			}
		}
	}

}