package com.bit2016.mysite.action.board;

import com.bit2016.web.Action;
import com.bit2016.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action  =null;
		if("writeform".equals(actionName)){
			action = new BoardWriteFormAction(); 
		}else if("view".equals(actionName)){
			action = new BoardViewAction(); 
		}else if("delete".equals(actionName)){
			action = new BoardDeleteAction(); 
		}else{
			action = new BoardListAction();
		}
		return action;
	}

}
