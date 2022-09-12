package csvUploadDemo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class csvLoad {
	// データリスト
	final String listMethodName = "getdataList"; 
	// set
	final String SET = "set"; 
	
	public List<csvErrObject> dataSet(Object strClass, String strCsvRecod) {
		
		List<csvErrObject> errDataList = null;
		
	    try {
	    	
	    		// クラス名取得
	    		String className =  strClass.getClass().getCanonicalName();
			    // データリストの取得
			    Method getListMethod = Class.forName(className).getMethod(listMethodName);
			    String[] methodNameList = (String[])getListMethod.invoke(strClass);
			    
			    // アップロードデータの分割
				List<String> csvDataList = Arrays.asList(strCsvRecod.split(","));
					
				// データリスト数ループを回す
				for (int i = 0; i < methodNameList.length; i++ ) {
					
					// メソッド名の取得と作成
					String FieldName = methodNameList[i];
					String setMethodName = SET + FieldName.substring(0,1).toUpperCase() + FieldName.substring(1);
					
					// バリデーションチェック
					String errCode = validationCheck(className, FieldName, csvDataList.get(i));
					if(errCode != null) {
						csvErrObject errData = new csvErrObject();
						errData.setKomokuNo(i);
						errData.setErrCode(errCode);
						errDataList.add(errData);
						continue;
					}
					
					// メソッド取得
					Class[] classArgs = new Class[]{String.class};
					Method setMethod = Class.forName(className).getMethod(setMethodName,classArgs);
					
					// データの設定
					Object[] setter = new Object[] {csvDataList.get(i)};
					setMethod.invoke(strClass, setter);
					
					/********   設定値確認用 start   ********/
					String getMmethodName = methodNameList[i];
					getMmethodName = "get" + getMmethodName.substring(0,1).toUpperCase() + getMmethodName.substring(1);
					Method getMethod = Class.forName(className).getMethod(getMmethodName);
					String res2 = (String)getMethod.invoke(strClass);
					System.out.println(res2); 
					/********   設定値確認用 end   ********/
				}
				
	    }catch(ReflectiveOperationException e) {
	        e.printStackTrace();
	    }
	    
		return errDataList;
		
	}
	
	// コード値は別クラス記載
	String NULL_ERR = "1"; 
	String TYPE_ERR = "2"; 
	String MAX_ERR = "3"; 
	String MIN_ERR = "4"; 
	String NUM_RANGE_ERR = "5"; 
	
	//バリデーションメソッド
	private String validationCheck(String className, String methodName, String data) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		
		String errCode = null;
		
		// フィールド名取得
		Class<?> clazz = Class.forName(className);
		Field field = clazz.getDeclaredField(methodName);
		
		// 各チェック要素取得
		Min Min = (Min) field.getAnnotation(Min.class);
		Max Max = (Max) field.getAnnotation(Max.class);
		NumRange NumRange = (NumRange) field.getAnnotation(NumRange.class);
		NotNull NotNull = (NotNull) field.getAnnotation(NotNull.class);
		Type Type = (Type) field.getAnnotation(Type.class);
		
		// NULLチェック
		if (NotNull != null) {
	        System.out.println(NotNull.Value()); 
	        if(data != null) {
	        	errCode = NULL_ERR;
	        }
		}
		
		// 属性チェック
		if (Type != null && errCode==null) {
	        System.out.println(Type.Pattern()); 
	        // 部品使用してチェック記載
		}
		
		// 桁数チェック
		if (Min != null &&  errCode==null) {
	        System.out.println(Min.Value() ); 
	        //最小桁チェック
	        if(Integer.valueOf(data) < Min.Value()) {
	        	errCode = MIN_ERR;
	        }
		}
		
		if (Max != null &&  errCode==null) {
	        System.out.println(Max.Value()); 
	        //最大桁チェック
	        if(Integer.parseInt(data) < Max.Value()) {
	        	errCode = MAX_ERR;
	        }
		}
		
		// 数値範囲チェック
		if (NumRange != null &&  errCode==null) {
	        System.out.println(NumRange.Min() + ":" + NumRange.Max() ); 
		}
		
		return errCode;
	}

}
