package org.digijava.module.gis.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.AmpIndicator;
import org.digijava.module.aim.dbentity.AmpIndicatorValue;
import org.digijava.module.aim.dbentity.AmpSector;
import org.digijava.module.aim.dbentity.AmpSectorScheme;
import org.digijava.module.aim.dbentity.IndicatorConnection;
import org.digijava.module.aim.dbentity.IndicatorSector;
import org.digijava.module.aim.util.IndicatorUtil;
import org.digijava.module.aim.util.SectorUtil;
import org.digijava.module.gis.form.TableWidgetTeaserForm;
import org.digijava.module.gis.widget.table.DaRow;
import org.digijava.module.gis.widget.table.DaTable;

/**
 * This action is just for testing purposes.
 * It generates IndicatorSector random values in db because we do not have such UI yet.
 * Wee need this data for GIS testing. for GIS such data may come from devinfo.
 * TODO after creating UI for such indicators, this should be removed.
 * @author Irakli Kobiashvili
 *
 */
public class TestWidget extends Action {

	Random random = new Random(System.currentTimeMillis());

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TableWidgetTeaserForm fTeaser=(TableWidgetTeaserForm)form;
		String act = request.getParameter("act");
		if (act==null){
			DaTable table=new DaTable();
			table.setWidth("50%");
			table.setDataRows(new ArrayList<DaRow>(10));
			for (int i = 0; i < 10; i++) {
				DaRow row=new DaRow();
				row.setId(new Long(i));
				row.setPk(new Long(i));
				table.getDataRows().add(row);
			}
			
			fTeaser.setTable(table);
			return mapping.findForward("forward");
		}
		if (act.equals("delete")){
			
		}
		if (act.equals("insert")){
			insertSectorIndicators();
		}
		
		return mapping.findForward("forward");
		
	}
	private void insertSectorIndicators() throws DgException{
		AmpSectorScheme scheme = getRandomScheme();
		if (scheme == null) {
			return;
		}
		Collection sectors = SectorUtil.getAllSectorsFromScheme(scheme.getAmpSecSchemeId());
		List<AmpIndicator> indicators = IndicatorUtil.getAllIndicators();
		List<IndicatorSector> connections = new ArrayList<IndicatorSector>();
		if (sectors!=null && sectors.size()>0 && indicators!=null && indicators.size()>0){
			for(int i=0; i<10; i++){
				int pos = (sectors.size()>1)?random.nextInt(sectors.size()-1):0;
				AmpSector sector = (AmpSector)getAtPos(sectors, pos);
				pos = (indicators.size()>1)?random.nextInt(indicators.size()-1):0;
				AmpIndicator indicator = indicators.get(pos);
				if (sector !=null && indicator !=null){
					IndicatorSector connection = new IndicatorSector();
					Set<AmpIndicatorValue> values = genRandmValues(4, connection);
					connection.setSector(sector);
					connection.setIndicator(indicator);
					connection.setValues(values);
					connections.add(connection);
				}
			}
			saveAllConnections(connections);
		}
		
	}
	private void saveAllConnections(List<IndicatorSector> connections) throws DgException{
		if (connections==null || connections.size()==0){
			return;
		}
		Session session = PersistenceManager.getRequestDBSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (IndicatorSector connection : connections) {
				session.save(connection);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx==null){
				try {
					tx.rollback();
				} catch (Exception e1) {
					throw new DgException(e1);
				}
			}
			throw new DgException(e);
		}
	}
	
	private AmpSectorScheme getRandomScheme(){
		Collection schemes = SectorUtil.getSectorSchemes();
		AmpSectorScheme scheme = null;
		if (schemes!=null && schemes.size()>0){
			int pos = (schemes.size()>1)?random.nextInt(schemes.size()-1):0;
			scheme = (AmpSectorScheme)getAtPos(schemes, pos);
		}
		return scheme;
	}
	
	private <E> E getAtPos(Collection<E> col, int pos){
		int c=0;
		for (E element : col) {
			if (c==pos){
				return element;
			}
			c++;
		}
		return null;
	}
	
	private Set<AmpIndicatorValue> genRandmValues(int numOfValues, IndicatorConnection conn){
		Random random=new Random();
		Calendar cal = Calendar.getInstance();
		Date baseDate=cal.getTime();
		Set<AmpIndicatorValue> values = new HashSet<AmpIndicatorValue>(numOfValues+2);
		for(int i=0;i<numOfValues;i++){
			AmpIndicatorValue value = new AmpIndicatorValue();
			value.setValue(random.nextDouble()*100);
			value.setIndicatorConnection(conn);
			value.setValueDate(cal.getTime());
			value.setValueType(AmpIndicatorValue.ACTUAL);
			values.add(value);
			cal.add(2, cal.MONTH);
		}
		AmpIndicatorValue base=new AmpIndicatorValue();
		base.setValueType(AmpIndicatorValue.BASE);
		base.setValue(random.nextDouble()*100);
		base.setValueDate(baseDate);
		base.setIndicatorConnection(conn);
		AmpIndicatorValue target = new AmpIndicatorValue();
		target.setValueType(AmpIndicatorValue.TARGET);
		target.setValue(random.nextDouble()*100);
		target.setValueDate(cal.getTime());
		target.setIndicatorConnection(conn);
		values.add(base);
		values.add(target);
		return values;
		
	}
}
