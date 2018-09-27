package com.moloong.messageGenerator.handler;

import java.io.File;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.widgets.Table;

import com.moloong.messageGenerator.common.AddFileForTableUtil;

/**  
 * @Description:表格拖曳处理
 * @author ye.yuan
 * @date 2011年4月16日 下午6:12:28  
 *
 */
public class DropHandler implements DropTargetListener{
    private Table table;
    
    public DropHandler(Table table) {
        super();
        this.table = table;
    }

    @Override
    public void dragEnter(DropTargetEvent event) {
        if (event.detail == DND.DROP_DEFAULT)
            event.detail = DND.DROP_COPY;
    }

    @Override
    public void dragOperationChanged(DropTargetEvent event) {
        if (event.detail == DND.DROP_DEFAULT)
            event.detail = DND.DROP_COPY;
    }

    @Override
    public void dragOver(DropTargetEvent event) {
        event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SELECT;
    }

    @Override
    // 当松开鼠标时触发的事件
    public void drop(DropTargetEvent event) {
        
        if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
            // 获得文件的全路径
            String[] fileFullPaths = (String[])event.data;
            for (String fileFullPath : fileFullPaths) {
                AddFileForTableUtil.addFileItem(table, new File(fileFullPath));
            }
        }
    }

    @Override
    public void dragLeave(DropTargetEvent event) {
    }

    @Override
    public void dropAccept(DropTargetEvent event) {
    }
}
