/**
 * Main page
 */

var header = new App.ui.HeaderPanel({
	id : 'header-panel',
	region : 'north',
	height : 84,
	contentEl : 'header-table'
});

var view = new App.ui.View({
	id : 'view-panel',
	region : 'center',
	baseCls : 'x-plain',
	items : {
		xtype : 'panel',
		frame : true,
		title : 'О программе',
		autoScroll : true,
		items : {
			contentEl : 'intro-panel',
			frame : true
		}
	}
});

var menu = new App.ui.MainMenu({
	id : 'action-panel',
	region : 'west',
	baseCls : 'x-plain',
	split : true,

	items : [ {
		title : 'Справочники',
		contentEl : 'task-dictionary'
	}, {
		title : 'Сервис',
		contentEl : 'task-services'
	}, {
		title : 'Операции с ЦБ',
		contentEl : 'operations-cb'
	}, {
		title : 'Портфель',
		contentEl : 'task-portfolio'
	}, {
		title : 'Журнализация',
		contentEl : 'task-logs'
	} ],

	listeners : {
		'action' : {
			fn : view.onAction,
			scope : view
		}
	},

	/*
	 * Сервис
	 */

	// Справочник контактов - редактирование группы
	contactsGroupEdit : function(id, name) {
		this.submitDataRequest(this, 'contacts/mail-group-edit',
				'contacts/GroupShow.html', {
					id : id,
					name : name
				});
	},

	/*
	 * Портфель
	 */

	// Текущий финрез
	showDeleteCompanyForm : function() {
		this.submitDataRequest(this, 'portfolio/view-portfolio-ticker-grid',
				'portfolio/show-ticker.html');
	},
	// Список перекидок

	showTransferOperation : function(id) {
		this.submitDataRequest(this, 'portfolio/transfer-operation',
				'portfolio/TransferOperationsShow.html', {
					id : id
				});
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var vp = new Ext.Viewport({
		layout : 'border',
		forceLayout : true,
		items : [ header, menu, view ],
		listeners : {
			afterrender : function() {
				// menu.showCurrentModel();
			}
		}
	});
	view.addListener('add', function(container, component, index) {
		if (index == 0)
			vp.doLayout();
	});

	setTimeout(function() {
		Ext.get('loading-mask').fadeOut({
			remove : true
		});
	}, 400);

});
