/*<%@ page pageEncoding="utf-8" %>
 * 
 */

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

Ext.Ajax.on('requestcomplete', function(conn, xhr, options) {
	if (options.waitMsg || options.progress) {
		Ext.MessageBox.hide();
	}
	var text = xhr.responseText;
	if (text && text.charAt(0) == '{') {
		var res = Ext.decode(text);
		if (res && !res.success) {
			if (res.code == 'login') {
				loginVisible(true);
				App.ui.error('Зарегистрируйтесь в системе.');
				xhr.responseText = '';
			}
			if (res.error) {
				App.ui.error(/*'Ошибка на сервере.', */res.error);
				xhr.responseText = '';
			}
		}
	}
});

Ext.Ajax.on('requestexception', function(conn, response, options) {
	if (options.waitMsg || options.progress) {
		Ext.MessageBox.hide();
	}
	App.ui.error(/*'Ошибка при обращении к серверу.', */response.statusText);
});

Ext.namespace('App.util.Renderer');

App.util.Renderer = {
	number : function(prec) {
		if (prec == undefined) {
			prec = 3;
		}
		var format = '0,000';
		if (prec > 0) {
			format = '0,000.' + String.leftPad('0', prec, '0');
		}

		return function(v) {
			if (isNaN(Number(v)))
				return v;
			v = Ext.util.Format.number(v, format);
			return v.replace(/,/g, ' ');// .replace('.', ',');
		};
	},

	date : function(format) {
		format = format || 'd.m.Y';
		return function(v) {
			if (v) {
	            v = Ext.util.Format.substr(v, 0, 10);
	            v = Date.parseDate(v, 'Y-m-d');
	            return v ? v.format(format) : '';
            }
			return v;
		};
	},

	time : function(format) {
		format = format || 'H:i:s';
		return function(v) {
			if (v) {
	            if (v && v.length > 11) {
		            v = Ext.util.Format.substr(v, 11, 8);
	            }
	            if (v && v.length == 5) {
		            v += ':00';
	            }
	            v = Date.parseDate(v, 'H:i:s');
	            return v ? v.format(format) : '';
            }
			return v;
		};
	},

	datetime : function(format) {
		format = format || 'd.m.Y H:i:s';
		return function(v) {
			if (v) {
	            v = Ext.util.Format.substr(v, 0, 19);
	            v = Date.parseDate(v, 'Y-m-d H:i:s');
	            return v ? v.format(format) : '';
            }
			return v;
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

Ext.namespace('App.util.Format');

App.util.Format = {
		dateYMD : function(v) {
			return v ? v.format('Y-m-d') : null;
		}
	};

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
	console.error(text);
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
				Ext.Msg.wait('Загрузка записей...',
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
