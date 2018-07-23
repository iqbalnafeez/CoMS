package com.tsnc.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.ApplicationSale;
import com.tsnc.model.Fee;
import com.tsnc.model.FeeHistorys;
import com.tsnc.model.FeeParticulars;
import com.tsnc.model.FeeStructure;
import com.tsnc.model.Login;
import com.tsnc.model.StudentFormInfo;

@Repository
public class FeesDAO extends BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public Fee fetchFeeUSAppNo(String applicationNo, int sem)throws Exception {
		// TODO Auto-generated method stub
		Fee fee=(Fee)currentSession().createQuery(" FROM Fee F WHERE F.applicationNo='"+applicationNo+"' AND F.sem="+sem+"").uniqueResult();
		
		return fee;
	}
	@Transactional
	public void addFee(Fee fee)throws Exception {
		// TODO Auto-generated method stub
		currentSession().save(fee);
	}
	@Transactional
	public 		List<FeeParticulars> fetchFeeParticularsUSfid(int id)throws Exception {
		// TODO Auto-generated method stub
		List<FeeParticulars> lsfeeParticulars=(List<FeeParticulars>)currentSession().createQuery(" FROM FeeParticulars FP WHERE FP.fid="+id+" AND FP.overrided=FALSE").list();
		
		return lsfeeParticulars;


	}
	@Transactional
	public List<FeeStructure> fetchfeeStructure(int sem,
			String lastAcademicType, String course, int batchStrYr, int fid)throws Exception {
		// TODO Auto-generated method stub
		String hql="FROM FeeStructure F WHERE F.sem="+sem+" AND F.lastAcademicType='"+lastAcademicType+"' AND F.course='"+course+"' AND F.batchStrYr="+batchStrYr+""
				+ " AND F.id not in "
				+ "(SELECT FP.sid FROM FeeParticulars FP WHERE FP.fid="+fid+" AND FP.overrided=FALSE)";
		List<FeeStructure> feeStructureList = (List<FeeStructure>)currentSession().createQuery(hql).list();
		return feeStructureList;
	}
	@Transactional
	public void updateFee(Fee fee)throws Exception  {
		// TODO Auto-generated method stub
		currentSession().update(fee);
	}
	@Transactional
	public Integer saveFee(Fee fee)throws Exception  {
		// TODO Auto-generated method stub
		System.out.println("******* "+fee.getId()+" *"+fee.getReceiptNo()+"********");
		Integer id=(Integer) currentSession().save(fee);
		System.out.println("******* "+id+" *********");
	
		return id;
	}
	@Transactional
	public Integer addFeeHistorys(FeeHistorys feeHistorys)throws Exception  {
		// TODO Auto-generated method stub
		System.out.println("******* "+feeHistorys.getId()+" *"+feeHistorys.getReceiptNo()+"********");
		Integer id=(Integer) currentSession().save(feeHistorys);
		System.out.println("******* "+id+" *********");
		/*FeeHistorys feeHis=(FeeHistorys) currentSession().save(feeHistorys);
		System.out.println(feeHis.getId()+"  *******************");*/
		return id;
	}

/*	@Transactional
	public int fetchFeeHistorys(FeeHistorys feeHistorys)throws Exception  {
		// TODO Auto-generated method stub
		String sql="SELECT FH.id FROM FeeHistorys "
				+ "FH WHERE FH.fid="+feeHistorys.getFid()+" AND  FH.due="+feeHistorys.getDue()+""
				+" AND  FH.actualFee="+feeHistorys.getActualFee()+""
				+" AND  FH.feeNeedTOPay="+feeHistorys.getFeeNeedTOPay()+""
				+" AND  FH.fine="+feeHistorys.getFine()+""
			
				+" AND  FH.paymenttype='"+feeHistorys.getPaymenttype()+"'"
				+" AND  FH.sem="+feeHistorys.getSem()+""
				+" AND  FH.receiptNo='"+feeHistorys.getReceiptNo()+"'"
		        +" AND  FH.remarks='"+feeHistorys.getRemarks()+"'";
		int id=(Integer) currentSession().createQuery(sql).uniqueResult();
		int id=(Integer) currentSession().createQuery("SELECT FH.id FROM FeeHistorys FH WHERE FH.fid="+feeHistorys.getFid()+" AND FH.date= ? AND FH.due="+feeHistorys.getDue()+"").setDate(0, feeHistorys.getDate()).uniqueResult();
		
		System.out.println(id+"  *******************");
		return id;
	}*/
	@Transactional
	public void addFeeParticulars(FeeParticulars feeParticulars)throws Exception {
		// TODO Auto-generated method stub
		currentSession().save(feeParticulars);
	}
	@Transactional
	public void updateFeeParticulars(FeeParticulars feeParticulars)throws Exception {
		// TODO Auto-generated method stub
		currentSession().update(feeParticulars);
	}
	@Transactional
	public List<FeeHistorys> fetchLsFeeHistorys(int fid)throws Exception {
		// TODO Auto-generated method stub
	List<FeeHistorys> lsFeeHistorys=(List<FeeHistorys>)currentSession().createQuery(" FROM FeeHistorys FH WHERE FH.fid="+fid+"").list();
		
		return lsFeeHistorys;
		
	}
	@Transactional
	public List<FeeParticulars> fetchLsFeeParticulars(int hid)throws Exception {
		// TODO Auto-generated method stub
List<FeeParticulars> lsfeeParticulars=(List<FeeParticulars>)currentSession().createQuery(" FROM FeeParticulars FP WHERE FP.hid="+hid+"" ).list();
		
		return lsfeeParticulars;
	}
	@Transactional
	public Date fetchDuedate(int batchStrYr, int sem) throws Exception{
		// TODO Auto-generated method stub
		
		Date dueDate=	(Date) currentSession().createQuery("SELECT FNS.dueDate FROM FineStructure FNS WHERE FNS.batchStrYr="+batchStrYr+" AND FNS.sem="+sem+"").uniqueResult();
		return dueDate;
	}
	@Transactional
	public FeeHistorys fetchFeeHistorysUsid(int id)throws Exception {
		// TODO Auto-generated method stub
		
		FeeHistorys feeHistorys=(FeeHistorys) currentSession().get(FeeHistorys.class, id);
		return feeHistorys;
	}
	@Transactional
	public Fee fetchFeeUSid(int id) throws Exception{
		// TODO Auto-generated method stub
		Fee fee=(Fee) currentSession().get(Fee.class, id);
		return fee;
	}
	@Transactional
	public void updateFeesaveHistorys(Fee fee, FeeHistorys feeHistorys, int hid)throws Exception {
		// TODO Auto-generated method stub
		currentSession().update(fee);
		currentSession().save(feeHistorys);
		FeeHistorys feeHistoryolds=(FeeHistorys) currentSession().get(FeeHistorys.class, hid);
		feeHistoryolds.setPaymenttype("Cheque_Reverted");
		currentSession().update(feeHistoryolds);
		
	}
}
