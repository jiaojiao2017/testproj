package proj;

import proj.BaseSeq;

import proj.ObServiceSupport;



/**
 * 
 * @description 序列服务Service
 */
public interface BaseSeqService extends ObServiceSupport<BaseSeq, Long> {
	public BaseSeq findBySeqCode(String seqCode);
}
