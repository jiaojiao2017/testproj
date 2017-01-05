package proj;

public interface SeqService {

	public Long getSeqNextValue(String string);
	
	/**
	 * 根据指定步长，获得下个值
	 */
	public Long getSeqNextValue(String string, Integer step);

}
