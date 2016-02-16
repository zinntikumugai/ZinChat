/**
 *
 */
package com.github.zinntikumugai.zinchat;

/**
 * @author zinntikumugai
 * @Licence GPL v3.0
 */
public class ZinChatKanaCon {

	//変数宣言&初期化
	static String last = "";
	static String data = "";
	static String tmp = "";


	/**
	 * ローマ字からひらがなへの変換の定義
	 * Micrasft IME 基準(Windows 10 PROに内蔵のIME)
	 *
	 * なお、存在しない(xkuなど)ものは基本あ行に揃えます(カタカナになるもの"ヵ"の行はカタカナのア行に変換)
	 */
	static final String[][] teigi = {
			{"","あ","い","う","え","お"},
			{"k","か","き","く","け","こ"},
			{"c","か","し","く","せ","こ"},
			{"g","が","ぎ","ぐ","げ","ご"},
			{"s","さ","し","す","せ","そ"},
			{"z","ざ","じ","ず","ぜ","ぞ"},
			{"t","た","ち","つ","て","と"},
			{"d","だ","ぢ","づ","で","ど"},
			{"n","な","に","ぬ","ね","の"},
			{"h","は","ひ","ふ","へ","ほ"},
			{"p","ぱ","ぴ","ぷ","ぺ","ぽ"},
			{"b","ば","び","ぶ","べ","ぼ"},
			{"m","ま","み","む","め","も"},
			{"y","や","い","ゆ","い","よ"},
			{"r","ら","り","る","れ","ろ"},
			{"w","わ","うぃ","う","うぇ","を"},
			{"v","ヴぁ","ヴぃ","ヴ","ヴぇ","ヴぉ"},
			{"f","ふぁ","ふぃ","ふ","ふぇ","ふぉ"},
			{"q","くぁ","くぃ","く","くぇ","くぉ"},
			{"j","じゃ","じ","じゅ","じぇ","じょ"},
			{"x","ぁ","ぃ","ぅ","ぇ","ぉ"},
			{"xk","ヵ","ィ","ゥ","ヶ","ォ"},							//"xki","xku","xko"は存在しない
			{"xw","ゎ","ぃ","ぅ","ぇ","ぉ"},							//"xwi","xwu","xwe","xwo"は存在しない
			{"xy","ゃ","ぃ","ゅ","ぇ","ょ"},
			{"xt","ぁ","ぃ","っ","ぇ","ぉ"},							//"xta","xti","xte","xto"は存在しない
			{"l","ぁ","ぃ","ぅ","ぇ","ぉ"},
			{"lk","ヵ","ィ","ゥ","ヶ","ォ"},							//"lki","lku","lke","lko"は存在しない
			{"lw","ゎ","ぃ","ぅ","ぇ","ぉ"},							//"lwi","lwu","lwe","lwo"は存在しない
			{"ly","ゃ","ぃ","ゅ","ぇ","ょ"},
			{"lt","ぁ","ぃ","っ","ぇ","ぉ"},							//"lta","tli","lte","lto"は存在しない
			{"ky","きゃ","きぃ","きゅ","きぇ","きょ"},
			{"cy","ちゃ","ちぃ","ちゅ","ちぇ","ちょ"},
			{"ch","ちゃ","ち","ちゅ","ちぇ","ちょ"},
			{"sy","しゃ","しぃ","しゅ","しぇ","しょ"},
			{"sh","しゃ","し","しゅ","しぇ","しょ"},
			{"zy","じゃ","じぃ","じゅ","じぇ","じょ"},
			{"ty","ちゃ","ちぃ","ちゅ","ちぇ","ちょ"},
			{"th","てゃ","てぃ","てゅ","てぇ","てょ"},
			{"dh","でゃ","でぃ","でゅ","でぇ","でょ"},
			{"ny","にゃ","にぃ","にゅ","にぇ","にょ"},
			{"my","みゃ","みぃ","みゅ","みぇ","みょ"},
			{"hy","ひゃ","ひぃ","ひゅ","ひぇ","ひょ"},
			{"py","ぴゃ","ぴぃ","ぴゅ","ぴぇ","ぴょ"},
			{"by","びゃ","びぃ","びゅ","びぇ","びょ"},
			{"ry","りゃ","りぃ","りゅ","りぇ","りょ"},
			{"wh","うぁ","うぃ","う","うぇ","うぉ"},
			{"fy","ふゃ","ふぃ","ふゅ","ふぇ","ふょ"},
			{"vy","ヴゃ","ヴぃ","ヴゅ","ヴぇ","ヴょ"},
			{"dw","どぁ","どぃ","どぅ","どぇ","どぉ"},
			{"kw","くぁ","き","く","け","こ"},			//"kwi","kwu","kwe","kwo"は存在しない
			{"tw","とぁ","とぃ","とぅ","とぇ","とぉ"},
			{"gw","ぐぁ","ぐぃ","ぐぅ","ぐぇ","ぐぉ"}
	};

	/**
	 * 子音から文字を取り出す
	 * @param idx あいうえお=12345
	 * @param buf 子音
	 * @return	該当する文字 or 空文字
	 */
	static String getkana(int idx,String buf) {
		for(int con = 0; con < teigi.length; con++) {
			if(buf.equals(teigi[con][0]))
				return teigi[con][idx];
		}
		return "";

	}

	static String converter(String org) {

		//文字変換
		for(int con = 0; con < org.length(); con++) {
			tmp = org.substring(con, con+1);

			switch(tmp) {
			case "a":
				dataadd(getkana(1, last));
				break;
			case"i":
				dataadd(getkana(2, last));
				break;
			case"u":
				dataadd(getkana(3, last));
				break;
			case"e":
				dataadd(getkana(4, last));
				break;
			case"o":
				dataadd(getkana(5, last));
				break;
			default:

				if(last.equals("n") && !tmp.equals("y")) {
					data += "ん";
					last = "";
					if(tmp.equals("n"))
						continue;
				}
				if(Character.isLetter(tmp.charAt(0))) {
					if(Character.isUpperCase(tmp.charAt(0))) {
						data = last + tmp;
						last = "";
					} else if(last.equals(tmp)) {
							data += "っ";
							last = tmp;
						}else {
							last = last + tmp;
						}
				}else {
					switch(tmp) {
					case"-":
						dataadd("ー");
						break;
					case".":
						dataadd("。");
						break;
					case",":
						dataadd("、");
						break;
					case"?":
						dataadd("?");
						break;
					case"!":
						dataadd("!");
						break;
					case"[":
						dataadd("「");
						break;
					case"]":
						dataadd("」");
						break;
					case"(":
						dataadd("(");
						break;
					case")":
						dataadd(")");
						break;
					default:
						data += last + tmp;
						last = "";
					}
				}
			}
		}
		if(last.endsWith("n")) {
			dataadd("ん");
		}
		data += last;
		return data;
	}

	/**
	 * dataに該当文字追加+lastの中身の消去の短縮
	 * @param add 連続させる文字
	 */
	private static void dataadd(String add) {
		data += add;
		last = "";
	}
}
