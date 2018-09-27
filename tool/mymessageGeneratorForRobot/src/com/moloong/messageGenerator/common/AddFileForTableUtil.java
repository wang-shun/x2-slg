package com.moloong.messageGenerator.common;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**  
 * @Description:向table中增加file的工具类
 * @author ye.yuan
 * @date 2011年4月16日 下午6:10:40  
 *
 */
public class AddFileForTableUtil {

    /**
     * 增加表格行
     * @param table
     * @param name
     * @param directory
    */
    public static void addFileItem(Table table, File file) {
        if (!file.exists()) {
            return;
        }
        
        if (file.isDirectory()) {
            for (String fileName : file.list()) {
                addFileItem(table, new File(file.getPath() + File.separator + fileName));
            }
        }else {
            TableItem[] tableItems = table.getItems();
            if (!file.getName().endsWith(".xml")) {
                return;
            }
            for (TableItem tableItem : tableItems) {
                //文件已经存在
                if (file.getName().equals(tableItem.getText(0)) && file.getPath().equals(tableItem.getText(1))) {
                    return;
                }
            }
            TableItem newTableItem = new TableItem(table, SWT.NONE);
            newTableItem.setText(new String[]{ file.getName(), file.getPath()});
        }
    }
}
