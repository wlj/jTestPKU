package plugin.ui.window.configuration.detailtabs;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import plugin.ui.window.configuration.entity.AuthorFilter;
import plugin.ui.window.configuration.entity.CodeFilter4Scope;
import plugin.ui.window.configuration.entity.FileFilter4Scope;
import plugin.ui.window.configuration.entity.MethodFilters4Scope;
import plugin.ui.window.configuration.entity.ScopeEntity;
import plugin.ui.window.configuration.entity.TimeFilter;
import plugin.util.Const;
import plugin.util.SWTResourceManager;

public class ScopeTab {
	TabItem tbtmScope;
	
	private Text text_lastDaysInLineFilter;
	private Text text_lastDaysInFileFilter;
	private Text text_authorNameInLineFilter;
	private Text text_authorNameInFileFilter;
	private Text text_minMethodNum;
	private Table tableMethodsPattern;
	
	public Button btnNoTimeFilters;
	public Button btnSinceDate;
	public DateTime sinceDateTime;
	public Button btnTileDate;
	public DateTime tileDateTime;
	public Button btnTestFilesInLast;
	public Button btnTestLocalFile;
	public Button btnNoAuthorFilters;
	public Button btnFilesAuthoredByUser;
	public Button btnLimitSimpleMethods;
	public Button btnLimitsDeprecatedClassMethod;
	public Button btnNoTimeFiltersInLineFilter;
	public Button btnSinceDateInLineFilter;
	public Button btnTestFilesInLastInLineFilter;
	public Button btnTestLocalFileInLineFilter;
	public Button btnNoAuthorFiltersInLineFilter;
	public Button btnFilesAuthoredByUserInLineFilter;
	public Button btnRemoveMethodPattern;
	public Button btnSkipMethedByPattern;
	public Button btnAddMethodPattern;
	public DateTime sinceDateTimeInLineFilter;
	

	public ScopeTab(TabFolder tabFolder, int style, ScopeEntity entity) {
		tbtmScope = new TabItem(tabFolder, style);
		tbtmScope.setImage(SWTResourceManager.getImage(Const.SCOPE_ICON_PATH));
		tbtmScope.setText("Scope");

		ScrolledComposite scrolledCompositeScope = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tbtmScope.setControl(scrolledCompositeScope);
		scrolledCompositeScope.setExpandHorizontal(true);
		scrolledCompositeScope.setExpandVertical(true);
		
		Composite compositeInScrolledCompositeScope = new Composite(scrolledCompositeScope, SWT.NONE);
		compositeInScrolledCompositeScope.setLayout(new FormLayout());

		TabFolder tabFolderInScope = new TabFolder(compositeInScrolledCompositeScope, SWT.NONE);
		FormData fd_tabFolderInScope = new FormData();
		fd_tabFolderInScope.right = new FormAttachment(100, -10);
		fd_tabFolderInScope.top = new FormAttachment(0, 10);
		fd_tabFolderInScope.left = new FormAttachment(0, 10);
		tabFolderInScope.setLayoutData(fd_tabFolderInScope);
		
		// add and set File Filter in Scope
		{
			TabItem tbtmFileFilter = new TabItem(tabFolderInScope, SWT.NONE);
			tbtmFileFilter.setText("File Filters");

			Composite fileFilterComposite = new Composite(tabFolderInScope, SWT.NONE);
			tbtmFileFilter.setControl(fileFilterComposite);
			fileFilterComposite.setLayout(new FormLayout());

			Group grpTimeOptions = new Group(fileFilterComposite, SWT.NONE);
			grpTimeOptions.setText("Time options");
			grpTimeOptions.setLayout(new FormLayout());
			FormData fd_grpTimeOptions = new FormData();
			fd_grpTimeOptions.right = new FormAttachment(100, -5);
			fd_grpTimeOptions.left = new FormAttachment(0, 5);
			fd_grpTimeOptions.top = new FormAttachment(0, 5);
			grpTimeOptions.setLayoutData(fd_grpTimeOptions);

			btnNoTimeFilters = new Button(grpTimeOptions, SWT.RADIO);
			FormData fd_btnNoTimeFilters = new FormData();
			fd_btnNoTimeFilters.top = new FormAttachment(0, 10);
			btnNoTimeFilters.setLayoutData(fd_btnNoTimeFilters);
			btnNoTimeFilters.setText("No time filters");

			btnSinceDate = new Button(grpTimeOptions, SWT.RADIO);
			FormData fd_btnSinceDate = new FormData();
			fd_btnSinceDate.top = new FormAttachment(btnNoTimeFilters, 5);
			btnSinceDate.setLayoutData(fd_btnSinceDate);
			btnSinceDate.setText("Test only files added or modified since the cutoff date");
			btnSinceDate.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					sinceDateTime.setEnabled(btnSinceDate.getSelection());
					btnTileDate.setEnabled(btnSinceDate.getSelection());
					tileDateTime.setEnabled(btnSinceDate.getSelection()&&btnTileDate.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			sinceDateTime = new DateTime(grpTimeOptions, SWT.BORDER);
			FormData fd_dateTime = new FormData();
			fd_dateTime.left = new FormAttachment(btnSinceDate, 15);
			fd_dateTime.top = new FormAttachment(btnNoTimeFilters);
			sinceDateTime.setLayoutData(fd_dateTime);
			
			

			btnTileDate = new Button(grpTimeOptions, SWT.CHECK);
			FormData fd_btnTileDate = new FormData();
			fd_btnTileDate.top = new FormAttachment(sinceDateTime, 5);
			fd_btnTileDate.left = new FormAttachment(0, 15);
			btnTileDate.setLayoutData(fd_btnTileDate);
			btnTileDate.setText("and added or modified before (now when disabled)");
			btnTileDate.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					tileDateTime.setEnabled(btnTileDate.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			tileDateTime = new DateTime(grpTimeOptions, SWT.BORDER);
			FormData fd_TileDataTime = new FormData();
			fd_TileDataTime.top = new FormAttachment(btnSinceDate, 3);
			fd_TileDataTime.left = new FormAttachment(sinceDateTime, 0, SWT.LEFT);
			tileDateTime.setLayoutData(fd_TileDataTime);

			btnTestFilesInLast = new Button(grpTimeOptions, SWT.RADIO);
			FormData fd_btnTestFilesInLast = new FormData();
			fd_btnTestFilesInLast.top = new FormAttachment(btnTileDate, 5);
			btnTestFilesInLast.setLayoutData(fd_btnTestFilesInLast);
			btnTestFilesInLast.setText("Test only files added or modified in the last");
			btnTestFilesInLast.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					text_lastDaysInFileFilter.setEnabled(btnTestFilesInLast.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			text_lastDaysInFileFilter = new Text(grpTimeOptions, SWT.BORDER);
			FormData fd_text_lastDays = new FormData();
			fd_text_lastDays.top = new FormAttachment(btnTileDate, 3);
			fd_text_lastDays.left = new FormAttachment(btnTestFilesInLast, 5);
			text_lastDaysInFileFilter.setLayoutData(fd_text_lastDays);

			Label lblDays = new Label(grpTimeOptions, SWT.NONE);
			FormData fd_lblDays = new FormData();
			fd_lblDays.top = new FormAttachment(btnTileDate, 3);
			fd_lblDays.left = new FormAttachment(text_lastDaysInFileFilter, 5);
			lblDays.setLayoutData(fd_lblDays);
			lblDays.setText("days");

			btnTestLocalFile = new Button(grpTimeOptions, SWT.RADIO);
			FormData fd_btnTestLocalFile = new FormData();
			fd_btnTestLocalFile.bottom = new FormAttachment(100, -5);
			fd_btnTestLocalFile.top = new FormAttachment(btnTestFilesInLast, 5);
			btnTestLocalFile.setLayoutData(fd_btnTestLocalFile);
			btnTestLocalFile.setText("Test only files added or modified locally");

			Group grpAuthorOptions = new Group(fileFilterComposite, SWT.NONE);
			grpAuthorOptions.setText("Author options");
			grpAuthorOptions.setLayout(new FormLayout());
			FormData fd_grpAuthorOptions = new FormData();
			fd_grpAuthorOptions.left = new FormAttachment(0, 5);
			fd_grpAuthorOptions.right = new FormAttachment(100, -5);
			fd_grpAuthorOptions.bottom = new FormAttachment(100, -10);
			fd_grpAuthorOptions.top = new FormAttachment(grpTimeOptions, 10);
			grpAuthorOptions.setLayoutData(fd_grpAuthorOptions);

			btnNoAuthorFilters = new Button(grpAuthorOptions, SWT.RADIO);
			FormData fd_btnNoAuthorFilters = new FormData();
			fd_btnNoAuthorFilters.top = new FormAttachment(0, 10);
			btnNoAuthorFilters.setLayoutData(fd_btnNoAuthorFilters);
			btnNoAuthorFilters.setText("No author filters");

			btnFilesAuthoredByUser = new Button(grpAuthorOptions, SWT.RADIO);
			FormData fd_btnRadioButton = new FormData();
			fd_btnRadioButton.top = new FormAttachment(btnNoAuthorFilters, 5);
			fd_btnRadioButton.bottom = new FormAttachment(100, -5);
			btnFilesAuthoredByUser.setLayoutData(fd_btnRadioButton);
			btnFilesAuthoredByUser.setText("Test only files authored by user");
			btnFilesAuthoredByUser.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					text_authorNameInFileFilter.setEnabled(btnFilesAuthoredByUser.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			text_authorNameInFileFilter = new Text(grpAuthorOptions, SWT.BORDER);
			FormData fd_text = new FormData();
			fd_text.top = new FormAttachment(btnNoAuthorFilters, 5);
			fd_text.left = new FormAttachment(btnFilesAuthoredByUser, 5);
			fd_text.bottom = new FormAttachment(100, -5);
			text_authorNameInFileFilter.setLayoutData(fd_text);
		}

		// add and set Line Filter in Scope
		{
			TabItem tbtmLineFilters = new TabItem(tabFolderInScope, SWT.NONE);
			tbtmLineFilters.setText("Line Filters");

			Composite lineFilterComposite = new Composite(tabFolderInScope, SWT.NONE);
			tbtmLineFilters.setControl(lineFilterComposite);
			lineFilterComposite.setLayout(new FormLayout());

			Group grpTimeOptionsInLineFilter = new Group(lineFilterComposite, SWT.NONE);
			grpTimeOptionsInLineFilter.setText("Time options");
			grpTimeOptionsInLineFilter.setLayout(new FormLayout());
			FormData fd_grpTimeOptions = new FormData();
			fd_grpTimeOptions.right = new FormAttachment(100, -5);
			fd_grpTimeOptions.left = new FormAttachment(0, 5);
			fd_grpTimeOptions.top = new FormAttachment(0, 5);
			grpTimeOptionsInLineFilter.setLayoutData(fd_grpTimeOptions);

			btnNoTimeFiltersInLineFilter = new Button(grpTimeOptionsInLineFilter, SWT.RADIO);
			FormData fd_btnNoTimeFilters = new FormData();
			fd_btnNoTimeFilters.top = new FormAttachment(0, 10);
			btnNoTimeFiltersInLineFilter.setLayoutData(fd_btnNoTimeFilters);
			btnNoTimeFiltersInLineFilter.setText("No time filters");

			btnSinceDateInLineFilter = new Button(grpTimeOptionsInLineFilter, SWT.RADIO);
			FormData fd_btnSinceDate = new FormData();
			fd_btnSinceDate.top = new FormAttachment(btnNoTimeFiltersInLineFilter, 5);
			btnSinceDateInLineFilter.setLayoutData(fd_btnSinceDate);
			btnSinceDateInLineFilter.setText("Test only lines added or modified since the cutoff date");
			btnSinceDateInLineFilter.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					sinceDateTimeInLineFilter.setEnabled(btnSinceDateInLineFilter.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			sinceDateTimeInLineFilter = new DateTime(grpTimeOptionsInLineFilter, SWT.BORDER);
			FormData fd_dateTime = new FormData();
			fd_dateTime.left = new FormAttachment(btnSinceDateInLineFilter, 15);
			fd_dateTime.top = new FormAttachment(btnNoTimeFiltersInLineFilter);
			sinceDateTimeInLineFilter.setLayoutData(fd_dateTime);

			btnTestFilesInLastInLineFilter = new Button(grpTimeOptionsInLineFilter, SWT.RADIO);
			FormData fd_btnTestFilesInLast = new FormData();
			fd_btnTestFilesInLast.top = new FormAttachment(btnSinceDateInLineFilter, 7);
			btnTestFilesInLastInLineFilter.setLayoutData(fd_btnTestFilesInLast);
			btnTestFilesInLastInLineFilter.setText("Test only lines added or modified in the last");
			btnTestFilesInLastInLineFilter.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					text_lastDaysInLineFilter.setEnabled(btnTestFilesInLastInLineFilter.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			text_lastDaysInLineFilter = new Text(grpTimeOptionsInLineFilter, SWT.BORDER);
			FormData fd_text_lastDays = new FormData();
			fd_text_lastDays.top = new FormAttachment(btnSinceDateInLineFilter, 5);
			fd_text_lastDays.left = new FormAttachment(btnTestFilesInLastInLineFilter, 5);
			text_lastDaysInLineFilter.setLayoutData(fd_text_lastDays);

			Label lblDays = new Label(grpTimeOptionsInLineFilter, SWT.NONE);
			FormData fd_lblDays = new FormData();
			fd_lblDays.top = new FormAttachment(btnSinceDateInLineFilter, 7);
			fd_lblDays.left = new FormAttachment(text_lastDaysInLineFilter, 5);
			lblDays.setLayoutData(fd_lblDays);
			lblDays.setText("days");

			btnTestLocalFileInLineFilter = new Button(grpTimeOptionsInLineFilter, SWT.RADIO);
			FormData fd_btnTestLocalFile = new FormData();
			fd_btnTestLocalFile.bottom = new FormAttachment(100, -5);
			fd_btnTestLocalFile.top = new FormAttachment(btnTestFilesInLastInLineFilter, 5);
			btnTestLocalFileInLineFilter.setLayoutData(fd_btnTestLocalFile);
			btnTestLocalFileInLineFilter.setText("Test only lines added or modified locally");
			
			Group grpAuthorOptionsInLineFilter = new Group(lineFilterComposite, SWT.NONE);
			grpAuthorOptionsInLineFilter.setText("Author options");
			grpAuthorOptionsInLineFilter.setLayout(new FormLayout());
			FormData fd_grpAuthorOptions = new FormData();
			fd_grpAuthorOptions.top = new FormAttachment(grpTimeOptionsInLineFilter, 10);
			fd_grpAuthorOptions.left = new FormAttachment(0, 5);
			fd_grpAuthorOptions.right = new FormAttachment(100, -5);
			fd_grpAuthorOptions.bottom = new FormAttachment(100, -10);
			grpAuthorOptionsInLineFilter.setLayoutData(fd_grpAuthorOptions);

			btnNoAuthorFiltersInLineFilter = new Button(grpAuthorOptionsInLineFilter, SWT.RADIO);
			FormData fd_btnNoAuthorFilters = new FormData();
			fd_btnNoAuthorFilters.top = new FormAttachment(0, 10);
			btnNoAuthorFiltersInLineFilter.setLayoutData(fd_btnNoAuthorFilters);
			btnNoAuthorFiltersInLineFilter.setText("No author filters");

			btnFilesAuthoredByUserInLineFilter = new Button(grpAuthorOptionsInLineFilter, SWT.RADIO);
			FormData fd_btnRadioButton = new FormData();
			fd_btnRadioButton.top = new FormAttachment(btnNoAuthorFiltersInLineFilter, 5);
			btnFilesAuthoredByUserInLineFilter.setLayoutData(fd_btnRadioButton);
			btnFilesAuthoredByUserInLineFilter.setText("Test only lines authored by user");
			btnFilesAuthoredByUserInLineFilter.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					text_authorNameInLineFilter.setEnabled(btnFilesAuthoredByUserInLineFilter.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			text_authorNameInLineFilter = new Text(grpAuthorOptionsInLineFilter, SWT.BORDER);
			FormData fd_text = new FormData();
			fd_text.top = new FormAttachment(btnNoAuthorFiltersInLineFilter, 5);
			fd_text.left = new FormAttachment(btnFilesAuthoredByUserInLineFilter, 5);
			text_authorNameInLineFilter.setLayoutData(fd_text);
		}
		// add and set Method Filters in Scope
		{
			TabItem tbtmMethodFilters = new TabItem(tabFolderInScope, SWT.NONE);
			tbtmMethodFilters.setText("Method Filters");

			Composite methodFiltersComposite = new Composite(tabFolderInScope, SWT.NONE);
			tbtmMethodFilters.setControl(methodFiltersComposite);
			methodFiltersComposite.setLayout(new FormLayout());

			btnSkipMethedByPattern = new Button(methodFiltersComposite, SWT.CHECK);
			FormData fd_btnCheckButton = new FormData();
			fd_btnCheckButton.top = new FormAttachment(0, 5);
			fd_btnCheckButton.right = new FormAttachment(100, -5);
			fd_btnCheckButton.left = new FormAttachment(0, 5);
			btnSkipMethedByPattern.setLayoutData(fd_btnCheckButton);
			btnSkipMethedByPattern.setText("Skip methods with names that match one of the following regular expressions");
			btnSkipMethedByPattern.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					tableMethodsPattern.setEnabled(btnSkipMethedByPattern.getSelection());
					btnAddMethodPattern.setEnabled(btnSkipMethedByPattern.getSelection());
					btnRemoveMethodPattern.setEnabled(btnSkipMethedByPattern.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			tableMethodsPattern = new Table(methodFiltersComposite, SWT.BORDER | SWT.FULL_SELECTION);
			FormData fd_table = new FormData();
			fd_table.left = new FormAttachment(btnSkipMethedByPattern, 5, SWT.LEFT);
			fd_table.right = new FormAttachment(100, -70);
			fd_table.bottom = new FormAttachment(100, -5);
			fd_table.top = new FormAttachment(btnSkipMethedByPattern, 5);
			tableMethodsPattern.setLayoutData(fd_table);
			tableMethodsPattern.setHeaderVisible(true);
			tableMethodsPattern.setLinesVisible(true);

			TableColumn tblclmnMethodPattern = new TableColumn(tableMethodsPattern, SWT.NONE);
			tblclmnMethodPattern.setWidth(210);
			tblclmnMethodPattern.setText("Method name regular expression");
			//双击可编辑单元格
			tableMethodsPattern.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					int itemIndex = tableMethodsPattern.getSelectionIndex();
					if(itemIndex<0){
						return;
					}
					TableItem item= tableMethodsPattern.getItem(itemIndex);
					editorTableItem(item);
					
					
				}
			});
			btnAddMethodPattern = new Button(methodFiltersComposite, SWT.NONE);
			FormData fd_btnAddMethodPattern = new FormData();
			fd_btnAddMethodPattern.left = new FormAttachment(tableMethodsPattern, 5);
			fd_btnAddMethodPattern.width = 60;
			fd_btnAddMethodPattern.top = new FormAttachment(btnSkipMethedByPattern, 5);
			btnAddMethodPattern.setLayoutData(fd_btnAddMethodPattern);
			btnAddMethodPattern.setText("Add");
			btnAddMethodPattern.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub
					TableItem item=new TableItem(tableMethodsPattern, SWT.NONE);
					
					editorTableItem(item);
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			btnRemoveMethodPattern = new Button(methodFiltersComposite, SWT.NONE);
			FormData fd_btnRemoveMethodPattern = new FormData();
			fd_btnRemoveMethodPattern.width = 60;
			fd_btnRemoveMethodPattern.left = new FormAttachment(btnAddMethodPattern, 0, SWT.LEFT);
			fd_btnRemoveMethodPattern.top = new FormAttachment(btnAddMethodPattern, 5);
			btnRemoveMethodPattern.setLayoutData(fd_btnRemoveMethodPattern);
			btnRemoveMethodPattern.setText("Remove");
			btnRemoveMethodPattern.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub
					int itemIndex = tableMethodsPattern.getSelectionIndex();
					if(itemIndex<0){
						return;
					}
					tableMethodsPattern.remove(itemIndex);
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}

		// add and set bottom composite in Scope
		{
			Composite bottomCompositeInScope = new Composite(compositeInScrolledCompositeScope, SWT.NONE);
			bottomCompositeInScope.setLayout(new FormLayout());
			FormData fd_bottomCompositeInScope = new FormData();
			fd_bottomCompositeInScope.top = new FormAttachment(tabFolderInScope, 10);
			fd_bottomCompositeInScope.right = new FormAttachment(100, -10);
			fd_bottomCompositeInScope.left = new FormAttachment(0, 10);
			bottomCompositeInScope.setLayoutData(fd_bottomCompositeInScope);

			btnLimitSimpleMethods = new Button(bottomCompositeInScope, SWT.CHECK);
			FormData fd_btnLimitSimpleMethods = new FormData();
			fd_btnLimitSimpleMethods.top = new FormAttachment(0, 3);
			btnLimitSimpleMethods.setLayoutData(fd_btnLimitSimpleMethods);
			btnLimitSimpleMethods.setText("Do not test methods with cyclomatic complexity less than");
			btnLimitSimpleMethods.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					text_minMethodNum.setEnabled(btnLimitSimpleMethods.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			btnLimitsDeprecatedClassMethod = new Button(bottomCompositeInScope, SWT.CHECK);
			FormData fd_btnLimitsDeprecatedClassMethod = new FormData();
			fd_btnLimitsDeprecatedClassMethod.bottom = new FormAttachment(100, -10);
			fd_btnLimitsDeprecatedClassMethod.top = new FormAttachment(btnLimitSimpleMethods, 5);
			btnLimitsDeprecatedClassMethod.setLayoutData(fd_btnLimitsDeprecatedClassMethod);
			btnLimitsDeprecatedClassMethod.setText("Do not test @deprecated classes or methods");

			text_minMethodNum = new Text(bottomCompositeInScope, SWT.BORDER);
			FormData fd_text_minMethodNum = new FormData();
			fd_text_minMethodNum.left = new FormAttachment(btnLimitSimpleMethods, 5);
			text_minMethodNum.setLayoutData(fd_text_minMethodNum);

			scrolledCompositeScope.setContent(compositeInScrolledCompositeScope);
			scrolledCompositeScope.setMinSize(compositeInScrolledCompositeScope.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
		fileFilterInit(entity);
		codeFilterInit(entity);
	}
    /**
     * 设置选中的值
     * @param fileFiler
     */
	private void fileFilterInit(ScopeEntity entity){
		if(entity==null){
			sinceDateTime.setEnabled(false);
			tileDateTime.setEnabled(false);
			text_lastDaysInFileFilter.setEnabled(false);
			text_authorNameInFileFilter.setEnabled(false);
			btnAddMethodPattern.setEnabled(false);
			btnRemoveMethodPattern.setEnabled(false);
		}else {
			sinceDateTime.setYear(entity.fileFilters.timeFilter.getStartDate().getYear());
			sinceDateTime.setMonth(entity.fileFilters.timeFilter.getStartDate().getMonth());
			sinceDateTime.setDay(entity.fileFilters.timeFilter.getStartDate().getDay());
			tileDateTime.setYear(entity.fileFilters.timeFilter.endDate.getYear());
			tileDateTime.setMonth(entity.fileFilters.timeFilter.endDate.getMonth());
			tileDateTime.setDay(entity.fileFilters.timeFilter.endDate.getDay());
			text_lastDaysInFileFilter.setText(String.valueOf(entity.fileFilters.timeFilter.nearestDays));
			btnTestFilesInLast.setEnabled(entity.fileFilters.timeFilter.timeOption==2);
			this.tileDateTime.setEnabled(entity.fileFilters.timeFilter.isEnabledEndDate);
			text_lastDaysInFileFilter.setEnabled(entity.fileFilters.timeFilter.timeOption==3);
			if(entity.fileFilters.timeFilter.timeOption==1){
				btnNoTimeFilters.setSelection(true);
			}else if(entity.fileFilters.timeFilter.timeOption==2){
				btnSinceDate.setSelection(true);
				sinceDateTime.setEnabled(true);
				if(entity.fileFilters.timeFilter.isEnabledEndDate){
					btnTestFilesInLast.setSelection(true);
				}else{
					btnTestFilesInLast.setSelection(false);
				}
			}else if(entity.fileFilters.timeFilter.timeOption==3){
				btnTestLocalFile.setEnabled(false);
			}
			
			
			btnNoAuthorFilters.setSelection(entity.fileFilters.authorFilter.authorOption==1);
			btnFilesAuthoredByUser.setSelection(entity.fileFilters.authorFilter.authorOption==2);
			text_authorNameInFileFilter.setEnabled(entity.fileFilters.authorFilter.authorOption==2);
			text_authorNameInFileFilter.setText(entity.fileFilters.authorFilter.authorNames==null?"":entity.fileFilters.authorFilter.authorNames);
			
			//
			
			
			btnSkipMethedByPattern.setSelection(entity.methodFilters.isEnabled);
			btnSkipMethedByPattern.setEnabled(entity.methodFilters.isEnabled);
			btnRemoveMethodPattern.setEnabled(entity.methodFilters.isEnabled);
			if(entity.methodFilters.expressions!=null&&entity.methodFilters.expressions.length>0){
				for(String s : entity.methodFilters.expressions){
					TableItem item=new TableItem(tableMethodsPattern, SWT.NONE);
					item.setText(s);
				}
				
			}
		}
		
		
	}
	
	//
//	public Button btnNoTimeFiltersInLineFilter;
//	public Button btnSinceDateInLineFilter;
//	public Button btnTestFilesInLastInLineFilter;
//	public Button btnTestLocalFileInLineFilter;
//	public Button btnNoAuthorFiltersInLineFilter;
//	public Button btnFilesAuthoredByUserInLineFilter;
//	private Text text_lastDaysInLineFilter;
//	private Text text_authorNameInLineFilter;
	private void codeFilterInit(ScopeEntity entity){
		if(entity==null){
			sinceDateTimeInLineFilter.setEnabled(false);
			//tileDateTime.setEnabled(false);
			text_lastDaysInLineFilter.setEnabled(false);
			text_authorNameInLineFilter.setEnabled(false);
			
			//btnAddMethodPattern.setEnabled(false);
			//btnRemoveMethodPattern.setEnabled(false);
		}else {
			sinceDateTimeInLineFilter.setYear(entity.codeFilter.timeFilter.getStartDate().getYear());
			sinceDateTimeInLineFilter.setMonth(entity.codeFilter.timeFilter.getStartDate().getMonth());
			sinceDateTimeInLineFilter.setDay(entity.codeFilter.timeFilter.getStartDate().getDay());
			//tileDateTime.setYear(entity.fileFilters.timeFilter.endDate.getYear());
			//tileDateTime.setMonth(entity.fileFilters.timeFilter.endDate.getMonth());
			//tileDateTime.setDay(entity.fileFilters.timeFilter.endDate.getDay());
			//text_lastDaysInLineFilter.setText(String.valueOf(entity.codeFilter.timeFilter.nearestDays));
			//btnTestFilesInLastInLineFilter.setEnabled(entity.codeFilter.timeFilter.timeOption==2);
			//this.tileDateTime.setEnabled(entity.fileFilters.timeFilter.isEnabledEndDate);
			//text_lastDaysInLineFilter.setEnabled(entity.codeFilter.timeFilter.timeOption==3);
			if(entity.codeFilter.timeFilter.timeOption==1){
				btnNoTimeFiltersInLineFilter.setSelection(true);
			}else if(entity.codeFilter.timeFilter.timeOption==2){
				btnSinceDateInLineFilter.setSelection(true);
				sinceDateTimeInLineFilter.setEnabled(true);
				//if(entity.codeFilter.timeFilter.isEnabledEndDate){
					//btnTestFilesInLast.setSelection(true);
				//}else{
					//btnTestFilesInLast.setSelection(false);
				//}
			}else if(entity.codeFilter.timeFilter.timeOption==3){
			    text_lastDaysInLineFilter.setEnabled(true);
			    text_lastDaysInLineFilter.setText(String.valueOf(entity.codeFilter.timeFilter.nearestDays));
				btnTestFilesInLastInLineFilter.setSelection(true);
				
			}else if(entity.codeFilter.timeFilter.timeOption==4){
				btnTestLocalFileInLineFilter.setSelection(true);
			}
			
			
			btnNoAuthorFiltersInLineFilter.setSelection(entity.codeFilter.authorFilter.authorOption==1);
			btnFilesAuthoredByUserInLineFilter.setSelection(entity.codeFilter.authorFilter.authorOption==2);
			text_authorNameInLineFilter.setEnabled(entity.codeFilter.authorFilter.authorOption==2);
			text_authorNameInLineFilter.setText(entity.codeFilter.authorFilter.authorNames==null?"":entity.codeFilter.authorFilter.authorNames);
			
			
		}
		
		
	}
	/**
	 * 获取过滤选项
	 * @return
	 */
	public ScopeEntity getScope(){
		ScopeEntity scope=new ScopeEntity();
		//FileFilter4Scope
		FileFilter4Scope fileFilter4Scope = new FileFilter4Scope();
		TimeFilter timeFilter=new TimeFilter();
		if(this.btnNoTimeFilters.getSelection()){
			timeFilter.timeOption=1;
		}
		if(this.btnSinceDate.getSelection()){
			timeFilter.timeOption=2;
			timeFilter.setStartDate(new Date(sinceDateTime.getYear(), sinceDateTime.getMonth(), sinceDateTime.getDay()));
			timeFilter.isEnabledEndDate=btnTileDate.getSelection();
			timeFilter.endDate=new Date(tileDateTime.getYear(), tileDateTime.getMonth(), tileDateTime.getMonth());
		}
		if(btnTestFilesInLast.getSelection()){
			timeFilter.nearestDays=Integer.parseInt(this.text_lastDaysInFileFilter.getText());
		}
		
		if(btnTestLocalFile.getSelection()){
			timeFilter.timeOption=3;
		}
		fileFilter4Scope.timeFilter=timeFilter;
		
		
		AuthorFilter authorFilter=new AuthorFilter();
		
		if(btnNoAuthorFilters.getSelection()){
			authorFilter.authorOption=1;
		}
		if(btnFilesAuthoredByUser.getSelection()){
			authorFilter.authorOption=2;
			String authorName = text_authorNameInFileFilter.getText();
			authorFilter.authorNames=authorName;
		}
		fileFilter4Scope.authorFilter=authorFilter;
		scope.fileFilters=fileFilter4Scope;
		
		if(btnLimitSimpleMethods.getSelection()){
			scope.testMethodsCyclomatic = true;
			String cyclomaticComplexity = text_minMethodNum.getText();
			if(cyclomaticComplexity != null){
				scope.cyclomaticComplexity = Integer.parseInt(cyclomaticComplexity);
			}
			
		}
		if(btnLimitsDeprecatedClassMethod.getSelection()){
			scope.deprecatedClassesOrMethods = true;
		}
		//CodeFilter4Scope
		CodeFilter4Scope codeFilter4Scope = new CodeFilter4Scope();
		TimeFilter timeFilterInLineFilter=new TimeFilter();
		if(this.btnNoTimeFiltersInLineFilter.getSelection()){
			timeFilterInLineFilter.timeOption=1;
		}
		timeFilterInLineFilter.setStartDate(new Date(sinceDateTime.getYear()+1900, sinceDateTime.getMonth(), sinceDateTime.getDay()));
		if(this.btnSinceDateInLineFilter.getSelection()){
			timeFilterInLineFilter.timeOption=2;
			
		}
		timeFilterInLineFilter.nearestDays=Integer.parseInt(this.text_lastDaysInLineFilter.getText());
		if(btnTestFilesInLastInLineFilter.getSelection()){
			timeFilterInLineFilter.timeOption=3;
			
		}
		if(btnTestLocalFileInLineFilter.getSelection()){
			timeFilterInLineFilter.timeOption=4;
		}
		codeFilter4Scope.timeFilter = timeFilterInLineFilter;
		
		
		AuthorFilter authorFilterInLineFilter=new AuthorFilter();
		
		if(btnNoAuthorFiltersInLineFilter.getSelection()){
			authorFilterInLineFilter.authorOption=1;
		}
		if(btnFilesAuthoredByUserInLineFilter.getSelection()){
			text_authorNameInFileFilter.setEnabled(true);
			authorFilterInLineFilter.authorOption=2;
			String authorName = text_authorNameInFileFilter.getText();
			authorFilterInLineFilter.authorNames=authorName;
		}
		codeFilter4Scope.authorFilter = authorFilterInLineFilter;
		scope.codeFilter=codeFilter4Scope;
		
		if(btnLimitSimpleMethods.getSelection()){
			scope.testMethodsCyclomatic = true;
			text_minMethodNum.setEnabled(true);
			String cyclomaticComplexity = text_minMethodNum.getText();
			if(cyclomaticComplexity != null){
				scope.cyclomaticComplexity = Integer.parseInt(cyclomaticComplexity);
			}
			
		}
		if(btnLimitsDeprecatedClassMethod.getSelection()){
			scope.deprecatedClassesOrMethods = true;
		}
		
		//methodfilters4Scope
		MethodFilters4Scope methodfilters4Scope =  new MethodFilters4Scope();
		methodfilters4Scope.isEnabled=btnSkipMethedByPattern.getSelection();
		String[] patterns=new String[tableMethodsPattern.getItemCount()];
		
		for(int index=0;index<tableMethodsPattern.getItemCount();index++){
			patterns[index]=tableMethodsPattern.getItem(index).getText();
		}
		methodfilters4Scope.expressions=patterns;
		scope.methodFilters=methodfilters4Scope;
		return scope;
	}
	
	public TabItem getTabItem(){
		return this.tbtmScope;
	}
	/**
	 * 编辑单元格
	 * @param item 单元格
	 */
	private void editorTableItem(TableItem item){
		final TableEditor tEditor=new TableEditor(tableMethodsPattern);
		final Text nameText = new Text(tableMethodsPattern, SWT.SINGLE | SWT.BORDER);
		tEditor.grabHorizontal = true; // 宽度
		tEditor.grabVertical = false; // 高度不占满
		tEditor.minimumHeight = 25;
		tEditor.minimumWidth = 210;
		tEditor.horizontalAlignment = SWT.CENTER;
		
		nameText.setText(item.getText());
		tEditor.setEditor(nameText, item, 0);
		nameText.setFocus();
		nameText.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				tEditor.getItem().setText(nameText.getText());
				tEditor.getEditor().dispose();
				tEditor.dispose();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
