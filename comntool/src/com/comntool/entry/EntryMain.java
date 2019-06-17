package com.comntool.entry;

import com.comntool.util.utility.Comn;
import com.comntool.view.BottomView;

public class EntryMain {

	public static void main(String[] args) {
		try {
			FrameMain.initFrame();
		}catch(Exception e) {
			e.printStackTrace();
			BottomView.setLogStatic(e.getMessage());
			BottomView.setLogStatic(Comn.getStackStr(e.getStackTrace()));
		}
	}


}
