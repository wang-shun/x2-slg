package com.moloong.messageGenerator.handler;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.moloong.messageGenerator.bean.Message;
import com.moloong.messageGenerator.bean.messagexml.IMessageXML;
import com.moloong.messageGenerator.bean.messagexml.MessageXMLFactory;
import com.moloong.messageGenerator.core.CodeType;
import com.moloong.messageGenerator.core.MessageVerifier;
import com.moloong.messageGenerator.core.generator.imp.ASCodeGenerator;
import com.moloong.messageGenerator.core.generator.imp.CppCodeGenerator;
import com.moloong.messageGenerator.core.generator.imp.JavaCodeGenerator;
import com.moloong.messageGenerator.core.project.Project;

/**  
 * @Description:生成代码handler
 * @author ye.yuan
 * @date 2011年4月16日 下午6:12:47  
 *
 */
public class GenerateCodeHandler extends SelectionAdapter {

    private Shell shell;

    private Table table;

    public GenerateCodeHandler(Shell shell, Table table) {
        this.shell = shell;
        this.table = table;
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
        
    	/*
    	 * 读取表格中所有的xml消息定义文件
    	 */
        TableItem[] items = table.getItems();
        if (items.length < 1) {
            MessageDialog.openWarning(shell, "警告", "请选择xml文件或将xml文件拖曳到表格中！");
            return;
        }
        
        //所有xml文件中定义的消息
        List<Message> messages = new ArrayList<Message>();
        //所有的xml消息文件封装对象
        List<IMessageXML> messageXMLs = new ArrayList<IMessageXML>();
        for (int i = 0; i < items.length; i++) {
            TableItem tableItem = items[i];
            String fullName = tableItem.getText(1);
            IMessageXML messageXML = MessageXMLFactory.INSTANCE.create(new File(fullName));
            
            messageXMLs.add(messageXML);
            messages.addAll(messageXML.getMessages());
        }
        
        //校验文件
        if (!MessageVerifier.INSTANCE.verify(messageXMLs)) {
            return;
        }
        
        /*
         * xml文件一般100个左右，分别在5个项目中生成代码，采用100个大小固定的线程池
         * 利用java 1.5的CountDownLatch类分解任务 主要用于多线程执行进度的监控，一个xml文件用一个线程来生成消息代码（加快生成速度），
         * message.xml和messagepool类在单独的线程中生成
         */
        //100个大小固定的线程池
        ExecutorService executor = Executors.newFixedThreadPool(100);
        
        /*
         * 利用java 1.5的CountDownLatch类分解任务和监控任务进度
         */
        CountDownLatch countDownLatch = new CountDownLatch(items.length);
        // 进度条
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);

        //多线程生成消息
        for (IMessageXML messageXML : messageXMLs) {
            //执行生成消息文件(message,handler,bean)的线程
            executor.execute(new GenerateMessageFilesWork(messageXML, countDownLatch));
        }
        executor.shutdown();

        //执行生成meesage.xml和messagepool类的线程
        Executors.newSingleThreadExecutor().execute(new GenerateDirectoryFilesWork(messages));
        
        //进度条监控生成代码任务的执行情况
        try {
        	//阻塞，直到生成消息文件的线程结束
            dialog.run(true, false, new MonitorWork(items.length, countDownLatch));
        } catch (InvocationTargetException  e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        
    }

    /**
     * 监控任务 
     * 项目名称：messageGenerator 
     * 类名称：MonitorWork 类描述：
     * 监控xml生成代码的情况
     * @version
     * 
     */
    private class MonitorWork implements IRunnableWithProgress {

        /** xml文件数量 */
        private int xmlFileCount;

        /** 计数器 */
        private CountDownLatch countDownLatch;

        public MonitorWork(int xmlFileCount, CountDownLatch countDownLatch) {
            this.xmlFileCount = xmlFileCount;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

            monitor.beginTask("正在生成代码文件：", xmlFileCount);

            // 剩余没有被线程生成代码的xml文件数量
            long laveFileNum = countDownLatch.getCount();

            // 当前xml数量,currentFilesNum始终大于等于laveFilesNum
            long currentFileNum = xmlFileCount;

            // 已经生成代码的xml文件数量
            long workedFileNum = currentFileNum - laveFileNum;

            while (true && laveFileNum > 0) {
                monitor.worked((int) (workedFileNum));
                monitor.subTask("已生成代码xml文件: " + (int) (xmlFileCount - laveFileNum) + "/" + xmlFileCount);

                //每半秒刷新一下进度条
                TimeUnit.MILLISECONDS.sleep(500);

                currentFileNum = laveFileNum;
                laveFileNum = countDownLatch.getCount();
                workedFileNum = currentFileNum - laveFileNum;
            }

            // 阻塞线程，直到所有的xml文件都已经生成代码
            countDownLatch.await();
            monitor.done();
        }

    }

    /**
     * 创建消息代码文件的任务
     * @author liushilong
     */
    private class GenerateMessageFilesWork implements Runnable {

        private IMessageXML messageXML;

        private CountDownLatch countDownLatch;

        public GenerateMessageFilesWork(IMessageXML messageXML, CountDownLatch countDownLatch) {
            this.messageXML = messageXML;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            // 每个xml文件都要根据项目工程的配置生成代码文件
            for (Project project : Project.values()) {
                if (!project.getConfig().isGenerate()) {
                    continue;
                }
                if (project.getCodeType() == CodeType.JAVA) {
                    JavaCodeGenerator.getInstance().generateMessageFiles(project, messageXML);
                } else if (project.getCodeType() == CodeType.AS) {
                    ASCodeGenerator.getInstance().generateMessageFiles(project, messageXML);
                } else if (project.getCodeType() == CodeType.CPP) {
                    CppCodeGenerator.getInstance().generateMessageFiles(project, messageXML);
                }
            }
            
            //生成代码任务完成一个
            countDownLatch.countDown();
        }

    }
    
    /**
     * 生成目录文件(message.xml和messagepool类)
     * @author liushilong
     */
    private class GenerateDirectoryFilesWork implements Runnable {
    	private List<Message> messages;

		public GenerateDirectoryFilesWork(List<Message> messages) {
			super();
			this.messages = messages;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
            for (Project project : Project.values()) {
                if (!project.getConfig().isGenerate()) {
                    continue;
                }
                if (project.getCodeType() == CodeType.JAVA) {
                    JavaCodeGenerator.getInstance().generateDirectoryFiles(project, messages);
                } else if (project.getCodeType() == CodeType.AS) {
                    ASCodeGenerator.getInstance().generateDirectoryFiles(project, messages);
                } else if (project.getCodeType() == CodeType.CPP) {
                    CppCodeGenerator.getInstance().generateDirectoryFiles(project, messages);
                }
            }
		}
    	
    }
}
