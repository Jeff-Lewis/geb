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

function _fnFailure(xhr, opts) {
	App.ui.error(/*'Ошибка при обращении к серверу.', */xhr.statusText);
}

function _loadData(cmp, urlData) {
	if (urlData && cmp.loadData) {
		Ext.Ajax.request({
		    url : urlData,
		    waitMsg : 'Загрузка данных.',
		    success : _fnSetData,
		    failure : _fnFailure
		});
	}

	function _fnSetData(xhr, opts) {
		try {
			var answer = Ext.decode(xhr.responseText);
			if (answer.success) {
				cmp.loadData(answer);
			}
		} catch (error) {
			App.ui.error('Ошибка при заполнении формы', error);
		}
	}
}

function showPanel(urlForm, urlData) {
	var id = urlForm + '-component';
	var cmp = Ext.getCmp(id);

	if (cmp) {
		panelView.scrollToTab(cmp, true);
		panelView.activate(cmp);
		panelView.doLayout();
		_loadData(cmp, urlData);
		return;
	}

	Ext.Ajax.request({
	    url : 'js/' + urlForm + '.js',
	    success : fnSuccess,
	    failure : _fnFailure
	});

	function fnSuccess(xhr, opts) {
		try {
			var config = eval(xhr.responseText);
			if (!config)
				return;

			config.id = id;

			var cmp = config;
			if (config instanceof Ext.Component) {
				cmp = config;
			} else {
				cmp.closable = true;
				cmp.frame = true;
				cmp = Ext.ComponentMgr.create(config);
			}

			cmp.on('close', function(cmp) {
				Ext.ComponentMgr.unregister(cmp);
			});
			Ext.ComponentMgr.register(cmp);

			panelView.add(cmp);
			panelView.doLayout();
			panelView.scrollToTab(cmp, true);
			panelView.activate(cmp);

			_loadData(cmp, urlData);
		} catch (error) {
			App.ui.error('Ошибка при создании формы', error);
		}
	}
}

function showModal(urlForm, urlData) {
	Ext.Ajax.request({
	    url : 'js/' + urlForm + '.js',
	    success : fnSuccess,
	    failure : _fnFailure
	});

	function fnSuccess(xhr, opts) {
		try {
			var config = eval(xhr.responseText);
			if (!config)
				return;

			var cmp = config;
			if (config instanceof Ext.Component) {
				cmp = config;
			} else {
				cmp = Ext.ComponentMgr.create(config);
			}

			cmp.id = urlForm + '-component';

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

			win.setTitle(cmp.caption);
			cmp.window = win;

			if (cmp.setWindow)
				cmp.setWindow(win);

			_loadData(cmp, urlData);

			win.show();
		} catch (error) {
			App.ui.error('Ошибка при создании формы', error);
		}
	}
}
