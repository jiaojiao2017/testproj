package proj;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;

import proj.BaseSeq;
import proj.DateUtil;

public class SeqServiceImpl implements SeqService {

	private BaseSeqService baseSeqService;
	private Map<String, BaseSeq> seqMap = new ConcurrentHashMap<String, BaseSeq>();

	private static Map<String, AtomicBoolean> runningMap = new ConcurrentHashMap<String, AtomicBoolean>();

	public Long getSeqNextValue(String seqCode, BaseSeq baseConf, Integer step) {
		BaseSeq baseSeq = null;
		AtomicBoolean runningFlag = new AtomicBoolean(false);
		if (seqMap.containsKey(seqCode)) {
			baseSeq = seqMap.get(seqCode);
		} else {
			if (runningMap.containsKey(seqCode)) {
				runningFlag = runningMap.get(seqCode);
			} else {
				runningMap.put(seqCode, runningFlag);
			}
		}

		if (runningFlag.compareAndSet(false, true)) {
			if (baseSeq != null) {
				long increment = step == null ? 0l : step.longValue();
				if((baseSeq.getCurSeq().longValue() + increment) >= baseSeq.getLastValue().longValue() || checkCircle(baseSeq))
				{
					refreshValue(baseSeq, false, step);
				}
				
			} else if (baseSeq == null) {
				baseSeq = baseSeqService.findBySeqCode(seqCode);
				refreshValue(baseSeq, false, step);
			}
			if (baseSeq == null) {
				baseConf.setSeqCode(seqCode);
				refreshValue(baseConf, true, step);
			}
			runningMap.put(seqCode, new AtomicBoolean(false));
			seqMap.put(seqCode, baseSeq);
		}

		long increment = 1l;
		if (step != null) {
			increment = step.longValue();
		}
		long curSeq = baseSeq.getCurSeq().addAndGet(increment);
		baseSeq.setCurSeq(new AtomicLong(curSeq));
		return  baseSeq.getCurSeq().get();
	}

	
	/**
	 * 刷新值至数据库
	 * @param baseSeq
	 * @param newFlag
	 */
	private void refreshValue(BaseSeq baseSeq, boolean newFlag, Integer step) {
		Long curSeq = 0l;
		if (newFlag) {
			curSeq = baseSeq.getCurSeq().longValue();
		} else {
			//String circleFlag =  baseSeq.getCircleFlag();
			curSeq = baseSeq.getLastValue();
		}
		
		int len = baseSeq.getLen().intValue();
		if (step != null && len < step.intValue()) {
			len = step.intValue();
		}
		long lastValue = new BigDecimal(curSeq.longValue()).add(
				new BigDecimal(len)).longValue();
		baseSeq.setLastValue(lastValue);
		baseSeq.setCurSeq(new AtomicLong(curSeq));
		baseSeq = baseSeqService.saveModelInIsolatedTx(baseSeq);
	}
	
	
	/**
	 * 检查循环
	 * @param baseSeq
	 * @return
	 */
	private boolean checkCircle(BaseSeq baseSeq)
	{
		boolean circleRefreshFlag = false;
		String circleFlag = baseSeq.getCircleFlag();
	if(StringUtils.isNotEmpty(circleFlag))
	{
		Date curDate = new Date();
		Date lastDate = baseSeq.getLastDate();
		String lastDateStr = DateUtil.formatDateTime("yyyy-MM-DD", lastDate);
		lastDate = DateUtil.parse("yyyy-MM-DD", lastDateStr);
		if(circleFlag.equalsIgnoreCase("Y"))
		{
			int curYear = curDate.getYear();
			int lastYear = lastDate.getYear();
			if(curYear> lastYear) 
				circleRefreshFlag  = true;
		}else if(circleFlag.equalsIgnoreCase("M"))
		{
			if(DateUtil.monthCompare(lastDateStr))
				circleRefreshFlag  = true;
		}else if(circleFlag.equalsIgnoreCase("D"))
		{
			if(DateUtil.dateCompare(lastDateStr))
				circleRefreshFlag  = true;
		}else if(circleFlag.equalsIgnoreCase("L"))
		{
			long lastValue = baseSeq.getLastValue();
			int len = baseSeq.getLen();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<len;i++)
			{
				sb.append("9");
			}
			String maxValStr = sb.toString();
			int maxVal = Integer.parseInt(maxValStr);
			 if(lastValue >= maxVal)
			 {
				 circleRefreshFlag  = true;
			 }
		}
	}
	if(circleRefreshFlag) 
	{
		baseSeq.setLastValue(0L);
	}
	return circleRefreshFlag;
	}

	public void setBaseSeqService(BaseSeqService baseSeqService) {
		this.baseSeqService = baseSeqService;
	}
	
	public static void main(String[] args) {
//		System.err.println(DateUtil.monthCompare("2015-04-01"));
		StringBuilder sb = new StringBuilder();
		int s = 5;
		for(int i=0;i<s;i++)
		{
			sb.append("9");
		}
		
		System.err.println("sb=="+sb.toString());
	}


	@Override
	public Long getSeqNextValue(String seqCode) {
		return getSeqNextValue(seqCode,null,null);
	}


	@Override
	public Long getSeqNextValue(String seqCode, Integer step) {
		return getSeqNextValue(seqCode,null,step);
	}


}
