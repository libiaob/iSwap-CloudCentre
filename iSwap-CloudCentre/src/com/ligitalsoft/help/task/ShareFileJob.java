package com.ligitalsoft.help.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.config.ConfigAccess;
import com.common.framework.help.SpringContextHolder;
import com.ligitalsoft.datasharexchange.dao.ChangeItemDao;
import com.ligitalsoft.datasharexchange.dao.ChangeItemDocumentDao;
import com.ligitalsoft.model.changemanage.ChangeItem;
import com.ligitalsoft.model.changemanage.ChangeItemDocument;

public class ShareFileJob implements Job {
	private Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private SpringContextHolder springContextHolder;
	
	private ChangeItemDao changeItemDao;
	private ChangeItemDocumentDao changeItemDocumentDao;
	/**
	 * 从交换成功目录移除文件到共享区目录
	 * 
	 * @author zhangx
	 */
	public void moveFileToShareDocument() {
		String changePath = ConfigAccess.init()
				.findProp("exchangeSuccDocument");// 得到交换成功目录路径
		String sharePath = ConfigAccess.init().findProp("shareDocument");// 得到共享目录路径
		File file = new File(changePath);
		File files[] = file.listFiles();// 得到文件下所有文件
		try {
			if (files != null && files.length != 0) {
				for (File oldFile : files) {
					FileChannel srcFile = new FileInputStream(oldFile)
							.getChannel();// 原文件
					File newFile = new File(sharePath + "/" + oldFile.getName());// 新文件
					FileChannel nowFile = new FileOutputStream(newFile)
							.getChannel();
					nowFile.transferFrom(srcFile, 0, oldFile.length());// 移动
					nowFile.close();
					srcFile.close();
					oldFile.delete();// 删除原文件
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件到发布目录 条件指标状态已经发布
	 * 
	 * @author zhangx
	 */
	public void moveFileToPublishDocument() {
		changeItemDao=springContextHolder.getBean("changeItemDao");
		changeItemDocumentDao=springContextHolder.getBean("changeItemDocumentDao");
		String sharePath = ConfigAccess.init().findProp("shareDocument");// 得到交换成功目录路径
		String publishPath = ConfigAccess.init().findProp("publishDocument");// 得到共享目录路径
		List<ChangeItem> items = changeItemDao.findListByShareState("1");
		for (ChangeItem item : items) {
			if (item != null) {
				List<ChangeItemDocument> docs = changeItemDocumentDao
						.findByItemId(item.getId(), "1");
				if (docs != null && docs.size() != 0) {
					for (ChangeItemDocument document : docs) {
						if (document != null
								&& !document.getShareState().equals("1")) {
							File file = new File(sharePath + "\\"
									+ document.getDocumentName());// 源文件
							File newFile = new File(publishPath + "\\"
									+ document.getDocumentName());// 新文件
							try {
								FileChannel srcFile = new FileInputStream(file)
										.getChannel();// 原文件
								FileChannel nowFile = new FileOutputStream(
										newFile).getChannel();
								nowFile.transferFrom(srcFile, 0, file.length());// 移动
								nowFile.close();
								srcFile.close();
								file.delete();// 删除原文件
								document.setShareState("1");// 文档已经发布
								changeItemDocumentDao.update(document);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info("发布区目录开始接受文件");
		moveFileToPublishDocument();
		log.info("发布区目录接受文件完毕");
		log.info("共享区目录开始接受文件!");
		moveFileToShareDocument();
		log.info("共享区目录接受文件完毕!");
	}
}
