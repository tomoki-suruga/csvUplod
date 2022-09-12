package csvUploadDemo;

public class Rdata extends Data {
	// ランク管理アップロード用
	
	//　アップロードされるリスト　{ 管理点番号,ランク１,ランク２}
 private static final String[] dataList = {"data1","data2"};
 
 //住所
 @Min(Value = 0)
 @Max(Value = 60)
 @Type(Pattern = "is全角文字")
 private String data1;
 
 //会社名
 @Type(Pattern = "is全角文字")
 private String data2;

public String[] getdataList() {
		return dataList;
	}
public String getData1() {
	return data1;
}
public void setData1(String data1) {
	this.data1 = data1;
}
public String getData2() {
	return data2;
}
public void setData2(String data2) {
	this.data2 = data2;
}

}
