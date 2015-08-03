package ru.prbb.middleoffice.repo;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RBr
 */
public interface UserHistory {

	public enum AccessAction {
		NONE(' '),
		/**
		 * insert (вставка, добавление) – под кнопкой «Добавить»
		 */
		INSERT('i'),
		/**
		 * update (редактирование, изменение) – под кнопкой «Изменить»
		 */
		UPDATE('u'),
		/**
		 * delete (удаление) – под кнопкой «Удалить»
		 */
		DELETE('d'),
		/**
		 * read (просмотр) – под списком
		 */
		SELECT('r'),
		/**
		 * пользователю разрешено запускать лежащую под кнопкой ХП с любыми
		 * параметрами
		 */
		OTHER('a');

		private Character permission_action;

		AccessAction(char pa) {
			permission_action = pa;
		}

		public Character getPermissionAction() {
			return permission_action;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	int putHist(String user_login, String user_ip, String command);

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	void checkAccess(String user_login, String sqlAccess, AccessAction accessAction);

}
