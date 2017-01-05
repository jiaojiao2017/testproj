package proj;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @description 序列服务
 */
public class BaseSeq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long idSeq=2L;
	
  	/**
	 * 序列代码
	 */
	private String seqCode;
	
  	/**
	 * N:不循环 Y：按年 M：按月 D：按天
	 */
	private String circleFlag;
	
  	/**
	 * 最近值
	 */
	private Long lastValue = 0L;
	
  	/**
	 * 长度
	 */
	private Integer len = 20;
	
	private AtomicLong curSeq = new AtomicLong(0);
	
  	/**
	 * 最近时间
	 */
	private java.util.Date lastDate = new Date();
	
  	public Long getIdSeq() {
		return idSeq;
	}
	
  	public String getSeqCode() {
		return seqCode;
	}
	
  	public String getCircleFlag() {
		return circleFlag;
	}
	
  	public Long getLastValue() {
		return lastValue;
	}
	
  	public Integer getLen() {
		return len;
	}
	
  	public java.util.Date getLastDate() {
		return lastDate;
	}
	
  	public void setIdSeq(Long idSeq) {
		this.idSeq = idSeq;
	}
	
  	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode;
	}
	
  	public void setCircleFlag(String circleFlag) {
		this.circleFlag = circleFlag;
	}
	
  	public void setLastValue(Long lastValue) {
		this.lastValue = lastValue;
	}
	
  	public void setLen(Integer len) {
		this.len = len;
	}
	
  	public void setLastDate(java.util.Date lastDate) {
		this.lastDate = lastDate;
	}

	public AtomicLong getCurSeq() {
		return curSeq;
	}

	public void setCurSeq(AtomicLong curSeq) {
		this.curSeq = curSeq;
	}
	
  	
  
}
