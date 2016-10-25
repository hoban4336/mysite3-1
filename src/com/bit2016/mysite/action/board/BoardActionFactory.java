package com.bit2016.mysite.action.board;

import com.bit2016.web.Action;
import com.bit2016.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action  =null;
		if("writeform".equals(actionName)){
			action = new BoardWriteFormAction(); 
		}else if("write".equals(actionName)){
			action = new BoardWriteAction(); 
		}else if("view".equals(actionName)){
			action = new BoardViewAction(); 
		}else if("replyform".equals(actionName)){
			action = new BoardReplyFormAction(); 
		}else if("modify".equals(actionName)){
			action = new BoardModifyAction(); 
		}else if("modifyform".equals(actionName)){
			action = new BoardModifyFormAction(); 
		}else if("delete".equals(actionName)){
			action = new BoardDeleteAction(); 
		}else{
			action = new BoardListAction();
		}
		return action;
	}

}
