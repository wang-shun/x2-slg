package com.moloong.messageGenerator.handler;

import java.io.File;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.moloong.messageGenerator.common.AddFileForTableUtil;

/**  
 * @Description:AddFolderHandler
 * @author ye.yuan
 * @date 2011年4月16日 下午6:12:16  
 *
 */
public class AddFolderHandler extends SelectionAdapter {

    private Shell shell;
    private Table table;

    public AddFolderHandler(Shell shell, Table table) {
        super();
        this.shell = shell;
        this.table = table;
    }
    
    @Override
    public void widgetSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        DirectoryDialog dd=new DirectoryDialog(shell);
        dd.setMessage("请选择包含xml消息定义文件的文件夹");
        dd.setText("选择文件夹");
//        dd.setFilterPath("D:\\Source Code\\qkdly\\Resource\\resource\\message");
        dd.setFilterPath(System.getProperty("user.home"));
        
        String directoryPath = dd.open();
        
        if (directoryPath == null) {
            return;
        }
        AddFileForTableUtil.addFileItem(table, new File(directoryPath));
    }
}
