/*<%@ page pageEncoding="utf-8" %>
 * 
 */

/**
 * View panel
 */
var panelView = new Ext.TabPanel({
	id : 'view-panel',
	region : 'center',
	autoScroll : true,
	enableTabScroll : true,
	activeTab : 0,
	resizeTab : true,
	layoutOnTabChange : true,
	baseCls : 'x-plain',
	items : {
		title : 'О программе',
		closable : true,
		frame : true,
		autoScroll : true,
	// contentEl : 'intro-panel'
	}
});

function showPanel(url) {
	var viewName = url;

	var id = viewName + '-component';
	var cmp = Ext.getCmp(id);

	if (cmp) {
		panelView.scrollToTab(cmp, true);
		panelView.activate(cmp);
		return;
	}

	Ext.Ajax.request({
		method : 'GET',
		url : 'js/' + viewName + '.js',
		success : fnSuccess,
		failure : fnFailure
	});

	function fnFailure(xhr, opts) {
		App.ui.error('Ошибка при загрузке формы', xhr.status);
	}

	function fnSuccess(xhr, opts) {
		try {
			cmp = eval(xhr.responseText);
			if (!cmp)
				return;

			cmp.id = id;

			panelView.add(cmp);
			panelView.doLayout();
			panelView.scrollToTab(cmp, true);
			panelView.activate(cmp);
		} catch (error) {
			App.ui.error('Ошибка при создании формы', error);
		}
	}
}

function showModal(url) {
	var viewName = url;

	Ext.Ajax.request({
		method : 'GET',
		url : 'js/' + viewName + '.js',
		success : fnSuccess,
		failure : fnFailure
	});

	function fnFailure(xhr, opts) {
		App.ui.error('Ошибка при загрузке формы', xhr.status);
	}

	function fnSuccess(xhr, opts) {
		try {
			var cmp = eval(xhr.responseText);
			if (!cmp)
				return;

			cmp.id = viewName + '-component';

			var win = new Ext.Window({
				layout : 'fit',
				width : cmp.width || 850,
				height : cmp.height || 600,
				plain : true,
				modal : true,
				border : false,
				items : cmp
			});
			win.doLayout();

			if (cmp.setWindow)
				cmp.setWindow(win);

			if (cmp.loadData)
				cmp.loadData(data, callback);

			win.show();
		} catch (error) {
			App.ui.error('Ошибка при создании формы', error);
		}
	}
}
