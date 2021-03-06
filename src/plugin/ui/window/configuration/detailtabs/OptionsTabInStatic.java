package plugin.ui.window.configuration.detailtabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import plugin.ui.window.configuration.entity.BugDetectionEntity;
import plugin.ui.window.configuration.entity.StaticGeneral;

public class OptionsTabInStatic {

	TabItem tabItem;
	TabFolder subTabFolder;
	GeneralInOptionsTab generalInOptionsTab;
	BugDetectiveInOptionsTab bugDetectiveInOptionsTab;

	public OptionsTabInStatic(TabFolder tabFolder, StaticGeneral entity1, BugDetectionEntity entity2) {
		// add components into tabItem
		tabItem = new TabItem(tabFolder, SWT.None);
		tabItem.setText("Options");

		ScrolledComposite scrolledComposite = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		tabItem.setControl(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite compositeInScrolledComposite = new Composite(scrolledComposite, SWT.None);
		compositeInScrolledComposite.setLayout(new FormLayout());

		// to be complete
		subTabFolder = new TabFolder(compositeInScrolledComposite, SWT.None);
		FormData fd_tabFolderData = new FormData();
		fd_tabFolderData.left = new FormAttachment(0, 0);
		fd_tabFolderData.right = new FormAttachment(100, 0);
		fd_tabFolderData.top = new FormAttachment(0, 5);
		fd_tabFolderData.bottom = new FormAttachment(100, -5);
		subTabFolder.setLayoutData(fd_tabFolderData);

		generalInOptionsTab = new GeneralInOptionsTab(subTabFolder, entity1);
		bugDetectiveInOptionsTab = new BugDetectiveInOptionsTab(subTabFolder, entity2);

		scrolledComposite.setContent(compositeInScrolledComposite);
		scrolledComposite.setMinSize(compositeInScrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}
	
	

}
