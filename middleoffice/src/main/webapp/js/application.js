/**
 * Application UI
 */

// Show a progress bar during all Ajax requests
Ext.Ajax.on('beforerequest', function(conn, options) {
	if (options.waitMsg) {
		Ext.MessageBox.wait(options.waitMsg, 'Пожалуйста подождите', {
			animate : true
		});
	}
	if (options.progress) {
		Ext.MessageBox.progress(options.progress, '', '0 %');
		Ext.MessageBox.updateProgress(0, '');
	}
});

Ext.Ajax.on('requestcomplete', function(conn, response, options) {
	if (options.waitMsg || options.progress) {
		Ext.MessageBox.hide();
	}
	if ((response.responseText + '#').charAt(0) == '{') {
		var msg = Ext.decode(response.responseText, true);
		if (msg) {
			if (msg.code == 'login') {
				App.ui.sessionExpired();
			} else {
//				if (!msg.success) {
//					App.ui.error("Неизвестная ошибка");
//				}
			}
		} else {
			App.ui.error('requestcomplete<br/>' + response);
		}
	}
});

Ext.Ajax.on('requestexception', function(conn, response, options) {
	Ext.MessageBox.hide();
	App.ui.error(response.statusText);
});

Ext.namespace('App.util.Renderer');

App.util.Renderer = (function() {
	return {
		number : function(prec) {
			if (prec == undefined) {
				prec = 3;
			}
			var format = '0,000';
			if (prec > 0) {
				format = '0,000.' + String.leftPad('0', prec, '0');
			}

			return function(v) {
				v = Ext.util.Format.number(v, format);
				return v.replace(/,/g, ' ');// .replace('.', ',');
			};
		},

		date : function(format) {
			format = format || 'd.m.Y';
			return function(v) {
				v = Ext.util.Format.substr(v, 0, 10);
				v = Date.parseDate(v, 'Y-m-d');
				return v ? v.format(format) : '';
			};
		},

		time : function(format) {
			format = format || 'H:i:s';
			return function(v) {
				if (v && v.length > 11) {
					v = Ext.util.Format.substr(v, 11, 8);
				}
				if (v && v.length == 5) {
					v += ':00';
				}
				v = Date.parseDate(v, 'H:i:s');
				return v ? v.format(format) : '';
			};
		},

		datetime : function(format) {
			format = format || 'd.m.Y H:i:s';
			return function(v) {
				v = Ext.util.Format.substr(v, 0, 19);
				v = Date.parseDate(v, 'Y-m-d H:i:s');
				return v ? v.format(format) : '';
			};
		},

		bool : function(trueValue) {
			return function(v, m) {
				if (trueValue != undefined) {
					v = (trueValue == v);
				}
				var img = v ? 'vwicn082' : 'vwicn081';
				m.attr = 'style="background: url(images/' + img
						+ '.gif) no-repeat center transparent;"';
				return '';
			};
		}
	};
})();

Ext.namespace('App.util.Format');

App.util.Format = (function() {
	return {
		ruMoney : function(val) {
			val = "" + val;
			val = val.replace(",", ".");
			return Ext.util.Format.usMoney(val).replace(/,/gi, " ").replace(
					".", ",").replace("$", "")
					+ " руб";
		},

		datetime : 'Y-m-dTH:i:s',
		dateYMD : function(v) {
			return v ? v.format('Y-m-d') : null;
		}
	};
})();

Ext.namespace('App.Combo');

App.Combo = (function() {
	return {
		getValueId : function(combo) {
			var store = combo.getStore();
			var value = combo.getValue();
			if (store && value) {
				var _idx = store.find('name', value);
				if (-1 != _idx) {
					return store.getAt(_idx).data.id;
				}
			}
			return null;
		}
	};
})();

Ext.namespace('App.ui');

App.ui.error = function(text, additional) {
	if (additional) {
		text += '<br/><br/><span class="z-tooltip">' + additional + '</span>';
	}
	Ext.MessageBox.show({
		title : 'Ошибка',
		msg : text,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.ERROR
	});
};

App.ui.message = function(text, additional, callback, animEl) {
	var msg = String.format(text);
	if (additional) {
		msg += '<br/><br/><span class="z-tooltip">' + additional + '</span>';
	}
	Ext.MessageBox.show({
		title : 'Информация',
		msg : msg,
		modal : true,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.INFO,
		fn : callback,
		animEl : animEl
	});
};

App.ui.confirm = function(text, callback) {
	Ext.MessageBox.confirm('Подтверждение', text, function(button) {
		if (button == 'yes')
			callback.call(this);
	});
};

App.ui.sessionExpired = function() {
	var msg = 'Вы не зарегистрированы в системе или Ваша сессия устарела<br/>Пожалуйста, войдите в систему повторно';
	Ext.MessageBox.show({
		title : 'Ошибка',
		msg : msg,
		buttons : Ext.MessageBox.OK,
		icon : Ext.MessageBox.ERROR,
		fn : function() {
			window.location = '';
		}
	});
};

var countLoadMsg = 0;

App.ui.listenersJsonStore = function() {

	return {
		beforeload : function(This, options) {
			++countLoadMsg;
			if (countLoadMsg == 1) {
				Ext.Msg.wait("Загрузка записей...",
						Ext.form.BasicForm.prototype.waitTitle,
						Ext.form.BasicForm.prototype.waitTitle);
			}
		},
		load : function(This, records, options) {
			--countLoadMsg;
			if (countLoadMsg <= 0) {
				countLoadMsg = 0;
				Ext.Msg.hide();
			}
		}
	};
};

App.ui.HeaderPanel = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.apply(this, config);
		App.ui.MainMenu.superclass.constructor.call(this);
	}
});

App.ui.View = Ext.extend(Ext.TabPanel, {
	autoScroll : true,
	enableTabScroll : true,
	activeTab : 0,
	resizeTab : true,
	layoutOnTabChange : true,
	constructor : function(config) {
		Ext.apply(this, config);
		App.ui.View.superclass.constructor.call(this, config);
	},
	onAction : function(ct, viewName, data) {
		var id = viewName + '-component';

		var cmp = this.getItem(id);
		if (!cmp) {
			// will load from sever
			var self = this;
			Ext.Ajax.request({
				method : 'GET',
				url : 'js/' + viewName + '.js',
				success : function(xhr) {
					try {
						cmp = eval(xhr.responseText);
						cmp.id = id;
					} catch (Exception) {
						try {
							var msg = Ext.decode(xhr.responseText);
							if (msg.code == 'login') {
								App.ui.sessionExpired();
							} else {
								App.ui.error("Неизвестная ошибка");
							}
						} catch (e) {
							App.ui.error(
									"Ошибка в процессе создания документа", e);
						}
						return;
					}
					if (data && cmp.loadData) {
						cmp.loadData(data);
					}
					self.add(cmp);
					self.doLayout();
					self.scrollToTab(cmp, true);
					self.activate(cmp);
				},
				failure : function() {
					App.ui.error('Ошибка при создании документа',
							'Скрипт не найден');
				}
			});
		} else {
			if (data)
				cmp.loadData(data, ct);
			this.scrollToTab(cmp, true);
			this.activate(cmp);
		}
	}

});

App.ui.MainMenu = Ext
		.extend(
				Ext.Panel,
				{
					collapsible : true,
					width : 300,
					minWidth : 300,
					autoScroll : true,
					header : false,
					items : this.items,
					listeners : this.listeners,
					constructor : function(config) {
						Ext.apply(this, config);
						this.items = [];
						Ext.each(config.items, function(item, index) {
							this.items.push(new Ext.Panel({
								id : 'action-panel-section-' + index,
								frame : true,
								title : item.title,
								collapsible : true,
								contentEl : item.contentEl
							}));
						}, this);
						this.listeners = config.listeners;
						App.ui.MainMenu.superclass.constructor.call(this);
					},

					initComponent : function() {
						App.ui.MainMenu.superclass.initComponent.call(this);
						this.addEvents('action');
					},

					// private
					submitData : function(ct, dataUrl, params, callback,
							showWaitMsg) {
						Ext.Ajax
								.request({
									url : dataUrl,
									// timeout: 60 * 1000, // 1 min
									timeout : 1000000000,
									params : params,
									waitMsg : showWaitMsg == undefined
											|| showWaitMsg ? 'Поиск в базе данных'
											: null,
									success : function(xhr) {
										var answer = Ext
												.decode(xhr.responseText);
										if (answer.success
												&& answer.success === true) {
											if (callback) {
												callback.call(ct, answer);
											}
										} else if (answer.code == 'login') {
											App.ui.sessionExpired();
										} else {
											App.ui.error(answer.message);
										}
									},
									failure : function() {
										App.ui.error('Сервер недоступен');
									}
								});
					},

					// private
					submitDataRequest : function(ct, viewName, dataUrl, params) {
						this.submitData(ct, dataUrl, params, function(answer) {
							ct.fireEvent('action', this, viewName, answer);
						}, true);
					},

					// private
					showModalForm : function(ct, viewName, data, callback) {
						Ext.Ajax
								.request({
									method : 'GET',
									url : 'js/' + viewName + '.js',
									success : function(xhr) {
										try {
											var cmp = eval(xhr.responseText);
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
											if (cmp.setWindow)
												cmp.setWindow(win);
											win.doLayout();
											if (cmp.loadData) {
												cmp.loadData(data, callback);
											}
											win.show();
										} catch (Exception) {
											try {
												var msg = Ext
														.decode(xhr.responseText);
												if (msg.code == 'login') {
													App.ui.sessionExpired();
												} else {
													App.ui
															.error("Неизвестная ошибка");
												}
											} catch (Exception) {
												App.ui
														.error("Ошибка в процессе создания документа");
											}
										}
									},
									failure : function() {
										App.ui
												.error(
														'Ошибка при создании документа',
														'Скрипт не найден');
									}
								});
					},

					// private
					showModal : function(ct, viewName, dataUrl, params) {
						if (dataUrl) {
							this.submitData(ct, dataUrl, params, function(
									answer) {
								this.showModalForm(ct, viewName, answer);
							});
						} else {
							this.showModalForm(ct, viewName);
						}
					},

					// private
					showPane : function(ct, viewName) {
						ct.fireEvent('action', this, viewName);
					}
				});

Ext.namespace('App.Ajax');

App.Ajax.request = function(config) {
	var cfg = {
		success : function(xhr) {
			var answer = Ext.decode(xhr.responseText);
			if (answer.success) {
				if (this.onSuccess)
					this.onSuccess.call(this, answer);
				if (this.statusBar)
					this.statusBar.setStatus({
						text : this.successText || 'Готово',
						iconCls : 'x-status-valid',
						clear : {
							wait : 8000,
							anim : true,
							useDefaults : false
						}
					});
			} else if (answer.code == 'login') {
				App.ui.sessionExpired();
			} else {
				if (this.statusBar) {
					this.statusBar.setStatus({
						text : this.errorText
								|| 'Ошибка при выполнении операции',
						iconCls : 'x-status-error',
						clear : {
							wait : 8000,
							anim : true,
							useDefaults : false
						}
					});
				}
				if (answer.code == 'business') {
					App.ui.error(answer.message);
				} else {
					App.ui.error('Внутрення ошибка сервера', answer.message
							+ '\n' + answer.code);
				}
			}
		},
		failure : function(xhr) {
			if (this.statusBar)
				this.statusBar.setStatus({
					text : 'Ошибка при выполнении операции',
					iconCls : 'x-status-error',
					clear : {
						wait : 8000,
						anim : true,
						useDefaults : false
					}
				});
			Ext.MessageBox.show({
				title : 'Ошибка',
				msg : xhr.statusText,
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
		}
	};
	Ext.apply(cfg, config);
	cfg.scope = cfg;
	if (cfg.statusBar)
		cfg.statusBar.showBusy({
			text : cfg.busyText || 'Запрос выполняется...'
		});
	return Ext.Ajax.request(cfg);
};
