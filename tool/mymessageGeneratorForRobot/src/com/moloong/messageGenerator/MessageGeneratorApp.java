/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.moloong.messageGenerator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.moloong.messageGenerator.core.project.Project;
import com.moloong.messageGenerator.core.project.ProjectConfig;
import com.moloong.messageGenerator.handler.AddFilesHandler;
import com.moloong.messageGenerator.handler.AddFolderHandler;
import com.moloong.messageGenerator.handler.ChooseProjectPathHandler;
import com.moloong.messageGenerator.handler.DropHandler;
import com.moloong.messageGenerator.handler.GenerateCodeHandler;
import com.moloong.messageGenerator.handler.RemoveAllFileshandler;
import com.moloong.messageGenerator.handler.RemoveFilesHandler;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;

/**  
 * @Description:
 * @author ye.yuan
 * @date 2011年4月11日 下午3:02:13  
 *
 */
public class MessageGeneratorApp {

    protected Shell shell;
    
    private Button generateCodeButton;
    private Text asText;
    private Text gameText;
    private Text robotText;
    private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
    private Button addFilesButton;
    private Table filesTable;
    private Button addFolderButto;
    private Button removeFiles;
    private Button removeAllFiles;
    private Text asMessagePoolClassText;
    private Text asCatalogXmlText;
    private Text gameMessagePoolClassText;
    private Text robotMessagePoolClassText;
    private Text gameCatalogXmlText;
    private Text robotCatalogXmlText;
    private Text cppText;
    private Text cppComgamemessagepoolmessagepool;
    private Text cppSrcmessagexml;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            MessageGeneratorApp window = new MessageGeneratorApp();
            window.open();
            //保存项目配置
            ProjectConfig.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        
        shell = new Shell();
        shell.setSize(1000, 600);
        shell.setText("消息代码生成器");
        shell.setLayout(new BorderLayout(0, 0));

        /*
         * shell居中
         */
        int width = shell.getMonitor().getClientArea().width;
        int height = shell.getMonitor().getClientArea().height;
        int x = shell.getSize().x;
        int y = shell.getSize().y;
        if (x > width) {
            shell.getSize().x = width;
        }
        if (y > height) {
            shell.getSize().y = height;
        }
        shell.setLocation((width - x) / 2, (height - y) / 2);
        
        {

            Composite composite = new Composite(shell, SWT.NONE);
            composite.setLayoutData(BorderLayout.NORTH);
            FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
            fl_composite.marginHeight = 15;
            fl_composite.marginWidth = 15;
            composite.setLayout(fl_composite);
            {
                Group group = new Group(composite, SWT.NONE);
                group.setText("生成代码路径配置");
                GridLayout gl_group = new GridLayout(9, false);
                group.setLayout(gl_group);
                {
                    Label lblGame = new Label(group, SWT.NONE);
                    lblGame.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblGame.setText("AS:");
                    
                }
                {
                    asText = new Text(group, SWT.BORDER);
                    asText.setEditable(false);
                    asText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    asText.setText(Project.AS.getConfig().getPath());
                    asText.setData("project", Project.AS);
                }
                
                {
                    Button asPathChoose = formToolkit.createButton(group, "选择", SWT.NONE);
                    asPathChoose.addSelectionListener(new ChooseProjectPathHandler(shell, asText));
                }
                {
                    Label lblNewLabel_11 = new Label(group, SWT.NONE);
                    lblNewLabel_11.setText("生成代码");
                }
                {
                    Button generateASCheck = new Button(group, SWT.CHECK);
                    generateASCheck.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            Project.AS.getConfig().setGenerate(((Button)e.getSource()).getSelection());
                        }
                    });
                    generateASCheck.setSelection(Project.AS.getConfig().isGenerate());
                }
                {
                    Label lblPool = new Label(group, SWT.NONE);
                    lblPool.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblPool.setText("MessagePool类:");
                }
                {
                    asMessagePoolClassText = new Text(group, SWT.BORDER);
                    asMessagePoolClassText.setEditable(false);
                    GridData gd_asMessagePoolClassText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
                    gd_asMessagePoolClassText.widthHint = 200;
                    asMessagePoolClassText.setLayoutData(gd_asMessagePoolClassText);
                    asMessagePoolClassText.setText(Project.AS.getConfig().getMessagePoolClass());
                }
                {
                    Label lblNewLabel_2 = new Label(group, SWT.NONE);
                    lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_2.setText("消息目录xml文件:");
                }
                {
                    asCatalogXmlText = new Text(group, SWT.BORDER);
                    asCatalogXmlText.setEditable(false);
                    asCatalogXmlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    asCatalogXmlText.setText(Project.AS.getConfig().getDirectoryXmlPath());
                }
                {
                    Label lblNewLabel = new Label(group, SWT.NONE);
                    lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel.setText("Game:");
                }
                {
                    gameText = new Text(group, SWT.BORDER);
                    gameText.setEditable(false);
                    gameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    gameText.setText(Project.GAME.getConfig().getPath());
                    gameText.setData("project", Project.GAME);
                }
                
                {
                    Button gamePathChoose = new Button(group, SWT.NONE);
                    gamePathChoose.addSelectionListener(new ChooseProjectPathHandler(shell, gameText));
                    formToolkit.adapt(gamePathChoose, true, true);
                    gamePathChoose.setText("选择");
                }
                {
                    Label lblNewLabel_12 = new Label(group, SWT.NONE);
                    lblNewLabel_12.setText("生成代码");
                }
                {
                    Button generateGameCheck = new Button(group, SWT.CHECK);
                    generateGameCheck.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            Project.GAME.getConfig().setGenerate(((Button)e.getSource()).getSelection());
                        }
                    });
                    generateGameCheck.setSelection(Project.GAME.getConfig().isGenerate());
                }
                {
                    Label lblNewLabel_3 = new Label(group, SWT.NONE);
                    lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_3.setText("MessagePool类:");
                }
                {
                    gameMessagePoolClassText = new Text(group, SWT.BORDER);
                    gameMessagePoolClassText.setEditable(false);
                    gameMessagePoolClassText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    gameMessagePoolClassText.setText(Project.GAME.getConfig().getMessagePoolClass());
                }
                {
                    Label lblNewLabel_7 = new Label(group, SWT.NONE);
                    lblNewLabel_7.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_7.setText("消息目录xml文件:");
                }
                {
                    gameCatalogXmlText = new Text(group, SWT.BORDER);
                    gameCatalogXmlText.setEditable(false);
                    gameCatalogXmlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    gameCatalogXmlText.setText(Project.GAME.getConfig().getDirectoryXmlPath());
                }
                {
                    Label lblRobot = new Label(group, SWT.NONE);
                    lblRobot.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblRobot.setText("Robot:");
                }
                {
                    robotText = new Text(group, SWT.BORDER);
                    robotText.setEditable(false);
                    robotText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    robotText.setText(Project.ROBOT.getConfig().getPath());
                    robotText.setData("project", Project.ROBOT);
                }
                
                {
                    Button robotPathChoose = new Button(group, SWT.NONE);
                    robotPathChoose.addSelectionListener(new ChooseProjectPathHandler(shell, robotText));
                    formToolkit.adapt(robotPathChoose, true, true);
                    robotPathChoose.setText("选择");
                }
                {
                    Label lblNewLabel_14 = new Label(group, SWT.NONE);
                    lblNewLabel_14.setText("生成代码");
                }
                {
                    Button generateRobotCheck = new Button(group, SWT.CHECK);
                    generateRobotCheck.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            Project.ROBOT.getConfig().setGenerate(((Button)e.getSource()).getSelection());
                        }
                    });
                    generateRobotCheck.setSelection(Project.ROBOT.getConfig().isGenerate());
                }
                {
                    Label lblNewLabel_5 = new Label(group, SWT.NONE);
                    lblNewLabel_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_5.setText("MessagePool类:");
                }
                {
                    robotMessagePoolClassText = new Text(group, SWT.BORDER);
                    robotMessagePoolClassText.setEditable(false);
                    robotMessagePoolClassText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    robotMessagePoolClassText.setText(Project.ROBOT.getConfig().getMessagePoolClass());
                }
                {
                    Label lblNewLabel_9 = new Label(group, SWT.NONE);
                    lblNewLabel_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_9.setText("消息目录xml文件:");
                }
                {
                    robotCatalogXmlText = new Text(group, SWT.BORDER);
                    robotCatalogXmlText.setEditable(false);
                    robotCatalogXmlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    robotCatalogXmlText.setText(Project.ROBOT.getConfig().getDirectoryXmlPath());
                }
                {
                    Label lblCpp = new Label(group, SWT.NONE);
                    lblCpp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblCpp.setText("Cpp:");
                }
                {
                    
                    cppText = new Text(group, SWT.BORDER);
                    cppText.setEditable(false);
                    cppText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    cppText.setText(Project.CPP.getConfig().getPath());
                    cppText.setData("project", Project.CPP);
                }
                {
                    Button cppPathChoose = new Button(group, SWT.NONE);
                    cppPathChoose.addSelectionListener(new ChooseProjectPathHandler(shell, cppText));
                    cppPathChoose.setText("选择");
                    
                }
                {
                    Label label = new Label(group, SWT.NONE);
                    label.setText("生成代码");
                }
                {
                    Button generateCppCheck = new Button(group, SWT.CHECK);
                    generateCppCheck.addSelectionListener(new SelectionAdapter() {
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            Project.CPP.getConfig().setGenerate(((Button)e.getSource()).getSelection());
                        }
                    });
                    generateCppCheck.setSelection(Project.CPP.getConfig().isGenerate());
                }
                {
                    Label lblNewLabel_16 = new Label(group, SWT.NONE);
                    lblNewLabel_16.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_16.setText("MessageManagerl类:");
                }
                {
                    cppComgamemessagepoolmessagepool = new Text(group, SWT.BORDER);
                    cppComgamemessagepoolmessagepool.setEditable(false);
                    cppComgamemessagepoolmessagepool.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    cppComgamemessagepoolmessagepool.setText("MessageManager");
                }
                {
                    Label lblNewLabel_17 = new Label(group, SWT.NONE);
                    lblNewLabel_17.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
                    lblNewLabel_17.setText("消息目录xml文件:");
                }
                {
                    cppSrcmessagexml = new Text(group, SWT.BORDER);
                    cppSrcmessagexml.setEditable(false);
                    cppSrcmessagexml.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    cppSrcmessagexml.setText(Project.CPP.getConfig().getDirectoryXmlPath());
                }
            }
        
        }
        
        {
            Composite composite = new Composite(shell, SWT.NONE);
            composite.setLayoutData(BorderLayout.SOUTH);
            composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
            {
                generateCodeButton = new Button(composite, SWT.NONE);
                generateCodeButton.setText("生成代码");
            }
            {
                Button exitButton = new Button(composite, SWT.NONE);
                exitButton.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        shell.close();
                    }
                });
                exitButton.setText("退出");
            }
        }
        
        {

            Composite composite = new Composite(shell, SWT.NONE);
            composite.setLayoutData(BorderLayout.CENTER);
            composite.setLayout(new BorderLayout(0, 0));
            {
                Composite composite_1 = new Composite(composite, SWT.NONE);
                composite_1.setLayoutData(BorderLayout.EAST);
                composite_1.setLayout(new RowLayout(SWT.VERTICAL));
                {
                    addFilesButton = new Button(composite_1, SWT.NONE);
                    addFilesButton.setText("增加文件");
                }
                {
                    addFolderButto = formToolkit.createButton(composite_1, "增加文件夹", SWT.NONE);
                }
                {
                    removeFiles = new Button(composite_1, SWT.NONE);
                    removeFiles.setText("移除文件");
                }
                {
                    removeAllFiles = new Button(composite_1, SWT.NONE);
                    removeAllFiles.setText("移除所有文件");
                }
            }
            {
                Composite composite_1 = new Composite(composite, SWT.NONE);
                composite_1.setLayoutData(BorderLayout.CENTER);
                FillLayout fl_composite_1 = new FillLayout(SWT.HORIZONTAL);
                fl_composite_1.marginWidth = 5;
                composite_1.setLayout(fl_composite_1);
                {
                    filesTable = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
                    filesTable.setLinesVisible(true);
                    filesTable.setHeaderVisible(true);
                    {
                        TableColumn fileName = new TableColumn(filesTable, SWT.CENTER);
                        fileName.setWidth(200);
                        fileName.setText("文件名");
                    }
                    {
                        TableColumn path = new TableColumn(filesTable, SWT.CENTER);
                        path.setWidth(570);
                        path.setText("路径");
                    }
                    
                }
            }
        }
        
        //选择xml文件
        addFilesButton.addSelectionListener(new AddFilesHandler(shell, filesTable));
        //选择文件夹
        addFolderButto.addSelectionListener(new AddFolderHandler(shell, filesTable));
        
        //移除所有文件
        removeAllFiles.addSelectionListener(new RemoveAllFileshandler(shell, filesTable));
        //移除文件
        removeFiles.addSelectionListener(new RemoveFilesHandler(shell, filesTable));
        
        //生成代码
        generateCodeButton.addSelectionListener(new GenerateCodeHandler(shell, filesTable));
        {
            // 定义拖放目标对象
            DropTarget dropTarget = new DropTarget(filesTable, DND.DROP_MOVE | DND.DROP_DEFAULT
                    | DND.DROP_COPY);
            // 设置目标对象可传输的数据类型
            dropTarget.setTransfer(new Transfer[] { FileTransfer.getInstance() });
            // 注册目标对象的事件处理
            dropTarget.addDropListener(new DropHandler(filesTable));
        }
        
    }

}
