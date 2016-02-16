/**
 * 
 */
package com.github.zinntikumugai.zinchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author zinntikumugai
 * @Licence GPL v3.0
 */
public class ZinChatGoogleImeCon {
	//漢字変換のURL(このURLの後にひらがなを続けて送ると戻り値に変換される)
	private static final String GOOGLE_IME_URL = "http://www.google.com/transliterate?langpair=ja-Hira|ja&text=";
	
	public static String GoogleCon(String org) {
		
		//引数の文字数が無いなら終了
		if(org.length() == 0) {
			return "";
		}
		
		HttpURLConnection urlcon = null;
		BufferedReader render = null;
		
		try {
			String baseurl = null;
			String encorde = "UTF-8";
			
			//変換URL文字列を作成
			baseurl = GOOGLE_IME_URL + URLEncoder.encode(org, encorde);
			
			//URLにエンコード
			URL url = new URL(baseurl);
			
			urlcon = (HttpURLConnection)url.openConnection();
			urlcon.setRequestMethod("GET");
			urlcon.setInstanceFollowRedirects(false);
			urlcon.connect();
			
			render = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), encorde));
			String line,resurlt;
			line = resurlt = "";
			
			while( (line = render.readLine()) != null)
				resurlt +=parseGoogleIMEResult(line);
			
			return resurlt.toString();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(ProtocolException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(urlcon != null) {
				urlcon.disconnect();
			}
			if(render != null) {
				try {
					render.close();
				}catch(IOException e) {
					
				}
			}
		}
		
		return "";
	}
	
	private static String parseGoogleIMEResult(String result) {
		
		String buf = "";
		int level, index;
		level = index = 0;
		
		while(index < result.length()) {
			if(level < 3) {
				int nextStart = result.indexOf("[", index);
				int nextEnd = result.indexOf("]", index);
				
				if(nextStart == -1) {
					return buf.toString();
				}else {
					if(nextStart < nextEnd) {
						level++;
						index = nextStart+1;
					}else {
						level--;
						index = nextEnd+1;
					}
				}
			}else {
				int start = result.indexOf("\"", index);
				int end = result.indexOf("\"", start+1);
				
				if(start == -1 || end == -1) {
					return buf.toString();
				}
				buf += result.substring(start + 1, end);
				int next = result.indexOf("]", end);
				
				if(next == -1) {
					return buf.toString();
				}else {
					level--;
					index = next + 1;
				}
			}
		}
		
		return buf.toString();
	}
}
