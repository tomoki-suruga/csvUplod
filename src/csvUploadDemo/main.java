package csvUploadDemo;

import java.util.ArrayList;
import java.util.List;

public class main {
	public static void main(String[] args) {
		
		/** アップロードされたデータの作成        **/
		ArrayList<String> csvUploadData = new ArrayList<String>();
        String csvUploadStr = "1111,bbbbb";
        for(int i=0; i<3; i++) {
        	csvUploadData.add(csvUploadStr);
        }
        System.out.println(csvUploadData); 
		/** アップロードされたデータの作成        **/

        
        /** 業務側記載内容        **/
        
        csvLoad csvLoad = new csvLoad();
        // CSVデータリスト、エラーデータリストの生成
        List<Rdata> RdataList = new ArrayList<Rdata>();
        List<String> err = new ArrayList<String>();
        
        //リフレクション+アノテーション
        for(String csvStr: csvUploadData) {
        	// データクラス作成
    		Rdata data = new Rdata();
    		// 共通部品使用
    		List<csvErrObject> csvErrList =  csvLoad.dataSet(data,csvStr);
    		
    		// エラーの有無
        	if(csvErrList == null) {
        		// CSVデータのリスト作成
        		RdataList.add(data);
        	}else {
        		// エラーの場合の処理
        		for(csvErrObject csvErr:csvErrList) {
        			// 既存のエラーメッセージ出力の分岐を記載
        			err.add(csvErr.getKomokuNo()+"に"+csvErr.getErrCode()+"のエラーがあります");
        		}
        	}
        }

        System.out.println(RdataList.get(1).getData1()); 
    }

}
