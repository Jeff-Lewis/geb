/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


/**
 * @author RBr
 *
 */
@Repository
public class DealsLoadingDaoImpl implements DealsLoadingDao {

	@Override
	public Map<Record, SQLException> put(List<Record> records) {
		// TODO Auto-generated method stub
		return new HashMap<>();
	}

}
