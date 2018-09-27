package com.moloong.messageGenerator.handler;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

/**  
 * @Description:移除表格中所有的文件
 * @author ye.yuan
 * @date 2011年4月16日 下午6:13:11  
 *
 */
public class RemoveAllFileshandler extends SelectionAdapter{

    private Shell shell;
    private Table table;
    
    public RemoveAllFileshandler(Shell shell, Table table) {
        super();
        this.shell = shell;
        this.table = table;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        table.removeAll();
    }

}
