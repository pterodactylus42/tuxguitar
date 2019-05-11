package org.herac.tuxguitar.app.view.toolbar.edit;

import java.util.List;

import org.herac.tuxguitar.app.TuxGuitar;
import org.herac.tuxguitar.app.action.TGActionProcessorListener;
import org.herac.tuxguitar.app.system.icons.TGIconManager;
import org.herac.tuxguitar.app.ui.TGApplication;
import org.herac.tuxguitar.app.view.component.tab.Tablature;
import org.herac.tuxguitar.app.view.component.tab.TablatureEditor;
import org.herac.tuxguitar.app.view.toolbar.model.TGToolBarSection;
import org.herac.tuxguitar.document.TGDocumentManager;
import org.herac.tuxguitar.song.models.TGSong;
import org.herac.tuxguitar.ui.UIFactory;
import org.herac.tuxguitar.ui.layout.UITableLayout;
import org.herac.tuxguitar.ui.toolbar.UIToolBar;
import org.herac.tuxguitar.ui.widget.*;

public abstract class TGEditToolBarSection implements TGToolBarSection {
	
	private String sectionTitle;
	private TGEditToolBar toolBar;
	private UIPanel toolBarContainer;
	private UILabel toolBarHeader;
	
	public TGEditToolBarSection(TGEditToolBar toolBar, String sectionTitle) {
		this.toolBar = toolBar;
		this.sectionTitle = sectionTitle;
	}
	
	public abstract void loadSectionIcons();
	
	public abstract void loadSectionProperties();
	
	public abstract void updateSectionItems();
	
	public abstract void createSectionToolBars();
	
	public UIControl createSection(UIContainer container) {
		UIFactory uiFactory = TGApplication.getInstance(this.toolBar.getContext()).getFactory();
		
		UIPanel outerContainer = uiFactory.createPanel(container, false);
		UITableLayout outerLayout = new UITableLayout();
		outerContainer.setLayout(outerLayout);

        this.toolBarHeader = uiFactory.createLabel(outerContainer);
        outerLayout.set(this.toolBarHeader, 1, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_TOP, true, false, 1, 1, null, null, 0f);

        this.toolBarContainer = uiFactory.createPanel(outerContainer, false);
		outerLayout.set(this.toolBarContainer, 2, 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_TOP, true, false, 1, 1, null, null, 0f);
		UITableLayout layout = new UITableLayout();
		this.toolBarContainer.setLayout(layout);

		this.createSectionToolBars();
		this.loadIcons();
		this.loadProperties();
		
		List<UIControl> toolBars = this.toolBarContainer.getChildren();
		for(int i = 0; i < toolBars.size(); i ++) {
			layout.set(toolBars.get(i), (i + 1), 1, UITableLayout.ALIGN_FILL, UITableLayout.ALIGN_TOP, true, false, 1, 1, null, null, 0f);
		}
		return outerContainer;
	}
	
	public UIToolBar createToolBar() {
		UIFactory uiFactory = TGApplication.getInstance(this.getToolBar().getContext()).getFactory();
		UIToolBar toolBar = uiFactory.createHorizontalToolBar(this.toolBarContainer);
		
		return toolBar;
	}

	public UIPanel createPanel() {
		UIFactory uiFactory = TGApplication.getInstance(this.getToolBar().getContext()).getFactory();
		UIPanel panel = uiFactory.createPanel(this.toolBarContainer, false);

		return panel;
	}

	public TGActionProcessorListener createActionProcessor(String actionId) {
		return new TGActionProcessorListener(this.toolBar.getContext(), actionId);
	}
	
	public void loadIcons() {
		this.loadSectionIcons();
	}
	
	public void loadProperties() {
		this.toolBarHeader.setText(this.getText(this.sectionTitle));
		this.loadSectionProperties();
	}
	
	public void updateItems() {
		this.updateSectionItems();
	}
	
	public String getText(String key) {
		return TuxGuitar.getProperty(key);
	}
	
	public TGIconManager getIconManager() {
		return TuxGuitar.getInstance().getIconManager();
	}
	
	public Tablature getTablature() {
		return TablatureEditor.getInstance(this.toolBar.getContext()).getTablature();
	}
	
	public TGSong getSong() {
		return TGDocumentManager.getInstance(this.toolBar.getContext()).getSong();
	}

	public TGEditToolBar getToolBar() {
		return toolBar;
	}
}
