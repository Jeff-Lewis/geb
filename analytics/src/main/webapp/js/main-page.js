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

	// EPS по компаниям
	// showEPSforCompanies : function() {
	// var _vbcc = Ext.getCmp('view-eps-for-companies-component');
	// if (_vbcc == undefined) {
	// this.showPane(this, 'reports/view-eps-for-companies');
	// } else {
	// _vbcc.show();
	// _vbcc.getStore().reload();
	// }
	// },

	/*
	 * Запросы к терминалу Bloomberg
	 */

	// BDH запрос с EPS
	// showEpsRequest : function() {
	// this.showPane(this, 'bloomberg/bdh-request-eps');
	// this.submitDataRequest(this, 'bloomberg/bdh-request-eps',
	// 'bloomberg/BdhRequestEpsParams.html');
	// },
	// BDH запрос
	// showMultiHistorParamsData : function() {
	// this.showPane(this, 'bloomberg/bdh-request');
	// this.submitDataRequest(this, 'bloomberg/bdh-request',
	// 'bloomberg/BdhRequestParams.html');
	// },
	// BDP запрос
	// showCurrentParamsData : function() {
	// this.showPane(this, 'bloomberg/bdp-request');
	// this.submitDataRequest(this, 'bloomberg/bdp-request',
	// 'bloomberg/BdpRequestParams.html');
	// },
	// BDP с override
	// showOverrides : function() {
	// this.showPane(this, 'bloomberg/bdp-request-override');
	// this.submitDataRequest(this, 'bloomberg/bdp-request-override',
	// 'bloomberg/BdpRequestOverrideParams.html');
	// },
	// BDS запрос
	// showBDSForm : function() {
	// this.showPane(this, 'bloomberg/bds-request');
	// this.submitDataRequest(this, 'bloomberg/bds-request',
	// 'bloomberg/BdsRequestParams.html');
	// },
	// Subscription
	// showEditSubscription : function() {
	// var _vsfc = Ext.getCmp('view-subscription-form-component');
	// if (_vsfc == undefined) {
	// this.showPane(this, 'bloomberg/view-subscription-form');
	// } else {
	// _vsfc.show();
	// _vsfc.getStore().reload();
	// }
	// },
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
