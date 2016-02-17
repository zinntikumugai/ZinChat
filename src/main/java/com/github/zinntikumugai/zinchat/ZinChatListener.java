/**
 *
 */
package com.github.zinntikumugai.zinchat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author zinntikumugai
 * @Licence GPL v3.0
 */
public class ZinChatListener implements Listener {

	//2016/2/16	変数でやったほうが管理しやすいと思い追加
	//2016/2/17 変更
	private static final String chek = ""
			+ "[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%# \t\n\f\r]*"
			+ "(https?|ftp)"
			+ "(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%# \t\n\f\r]+)";

	//2016/2/16	MessageManagerとの競合(カラーコード)のため変更
	@EventHandler(priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		//変数宣言
		int str_len, byte_len;
		Player player = event.getPlayer();
		String kana, japan, me;
		//初期化
		str_len = byte_len = 0;
		kana = japan = "";
		me = event.getMessage();

		//URLがあったら終了
		if( me.matches(chek))
			return;

		//文字数とバイト数を変数に入れる
		str_len = me.length();
		byte_len = me.getBytes().length;

		//文字がないなら終了
		if(str_len == 0 || me == null)
			return;

		//文字数とバイト数が合わない(2バイト文字がある)なら終了
		if(str_len != byte_len)
			return;

		//ひらがな変換を行ったものを代入
		kana = ZinChatKanaCon.converter(me);
		//漢字変換を行ったものを代入
		japan = ZinChatGoogleImeCon.GoogleCon(kana);

		if(japan.equals("")) {
			japan = kana;
			player.sendMessage("§2[ZInChat]§4変換に問題が起きました。ひらがなの文字数が多い可能性があります。"
					+ "\n文字数を減らしてみてください。それでも起きる場合は管理者に問い合わせください。");
		}


		//出力
		event.setMessage(japan + " §7(" + me + ")");
	}
}
