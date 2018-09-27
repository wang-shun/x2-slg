package com.moloong.messageGenerator.handler;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.moloong.messageGenerator.common.AddFileForTableUtil;

/**  
 * @Description:AddFilesHandler
 * @author ye.yuan
 * @date 2011年4月16日 下午6:12:06  
 *
 */
public class AddFilesHandler extends SelectionAdapter{

    private Shell shell;
    private Table table;
    
    public AddFilesHandler(Shell shell, Table table) {
        super();
        this.shell = shell;
        this.table = table;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        FileDialog dialog = new FileDialog(shell,SWT.OPEN|SWT.MULTI);
        dialog.setText("选择需要转换消息的xml文件");
        dialog.setFilterNames(new String[]{"xml文件(*.xml)"});
        dialog.setFilterExtensions(new String[]{"*.xml"});
//        dialog.setFilterPath("D:\\Source Code\\qkdly\\Resource\\resource\\message");
        dialog.setFilterPath(System.getProperty("user.home"));
        dialog.open();
        
        //返回所有选择的文件名，不包括路径
        String[] fileNames = dialog.getFileNames();
        //返回选择的路径，这个和fileNames配合可以得到所有的文件的全路径
        String path = dialog.getFilterPath();
        
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            AddFileForTableUtil.addFileItem(table, new File(path + File.separator + fileName));
        }
    }
}
