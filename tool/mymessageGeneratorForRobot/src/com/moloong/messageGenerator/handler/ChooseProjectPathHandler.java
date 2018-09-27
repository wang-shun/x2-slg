/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator.handler;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.moloong.messageGenerator.core.project.Project;

/**  
 * @Description:选择生成项目代码路径
 * @author ye.yuan
 * @date 2011年4月11日 下午4:16:43  
 *
 */
public class ChooseProjectPathHandler extends SelectionAdapter{

    //窗口
    private Shell shell;
    //按钮对应的text
    private Text text;

    public ChooseProjectPathHandler(Shell shell, Text text) {
        super();
        this.shell = shell;
        this.text = text;
    }
    
    @Override
    public void widgetSelected(SelectionEvent e) {
        super.widgetSelected(e);
        DirectoryDialog dd = new DirectoryDialog(shell);
        dd.setMessage("请选择包含xml消息定义文件的文件夹");
        dd.setText("选择文件夹");
//        dd.setFilterPath("D:\\Source Code\\qkdly\\Resource\\resource\\message");
        dd.setFilterPath(System.getProperty("user.home"));
        
        String directoryPath = dd.open();
        
        if (directoryPath == null) {
            return;
        }
        text.setText(directoryPath);
        //更新项目path
        Project project = (Project) text.getData("project");
        project.getConfig().setSrcPath(directoryPath);
    }

}
