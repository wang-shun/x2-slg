package com.moloong.messageGenerator.handler;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

/**  
 * @Description:移除表格中选择的文件
 * @author ye.yuan
 * @date 2011年4月16日 下午6:13:33  
 *
 */
public class RemoveFilesHandler extends SelectionAdapter{

    private Shell shell;
    private Table table;
    
    public RemoveFilesHandler(Shell shell, Table table) {
        super();
        this.shell = shell;
        this.table = table;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        // TODO Auto-generated method stub
        table.remove(table.getSelectionIndices());
    }

}
