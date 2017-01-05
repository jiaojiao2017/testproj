package proj;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Format {

	public static void main(String[] args) {
		
//		String seqFormat = "00000";
//		DecimalFormat format = new DecimalFormat(seqFormat);
//		Long i = 127L;
//		System.out.println(format.format(i));

//		int cutIndex = Integer.valueOf("-" + seqFormat.length());
//		System.out.println("cutIndex=" + cutIndex);
//		String newSeq = StringUtils.substring(format.format(i), cutIndex);
//		String ii = format.format(i);
//		String newSeq1 = ii.substring(ii.length() - seqFormat.length());
//		System.out.println(newSeq);
//		System.out.println(newSeq1);
//
//		String seqCode = "GZHP_FILE_SEQ";
//		BaseSeq baseSeq = new BaseSeq();
//		Long a = new SeqServiceImpl().getSeqNextValue(seqCode, baseSeq, 1);
//		System.out.println("a =" + a);
		
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMmddhhmmssSSS");
		Date date=new Date();
		System.out.print(dateFormater.format(date));

	}

}
