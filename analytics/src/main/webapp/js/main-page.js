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
		title : 'Текущая модель',
		contentEl : 'current-model'
	}, {
		title : 'Portfolio',
		contentEl : 'task-portfolio'
	}, {
		title : 'Компании',
		contentEl : 'task-sprav-org'
	}, {
		title : 'Параметры',
		contentEl : 'task-sprav-params'
	}, {
		title : 'Отчётные формы',
		contentEl : 'task-reports'
	}, {
		title : 'Запросы к терминалу Bloomberg',
		contentEl : 'task-grid'
	}, {
		title : 'Утилиты',
		contentEl : 'task-utils'
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
	 * Отчётные формы
	 */

	// Изменение оценок брокеров
	showBrokerEstimatesChange : function() {
		var _vbcc = Ext.getCmp('view-broker-estimates-change-component');
		if (_vbcc == undefined) {
			this.showPane(this, 'reports/view-broker-estimates-change');
		} else {
			_vbcc.show();
			_vbcc.getStore().reload();
		}
	},

	/*
	 * Утилиты
	 */

	// Плановое задание
	showSheduleForm : function() {
		this.submitDataRequest(this, 'view-shedule-data-grid',
				'staticdatarequest/multi-request-form.html');
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
