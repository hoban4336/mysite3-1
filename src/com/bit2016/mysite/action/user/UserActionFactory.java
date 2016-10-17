package com.bit2016.mysite.action.user;

import com.bit2016.mysite.action.main.MainAction;
import com.bit2016.web.Action;
import com.bit2016.web.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if( "joinform".equals( actionName ) ) {
			action = new JoinFormAction();
		} else if( "join".equals( actionName ) ){
			action = new JoinAction();
		} else if( "joinsuccess".equals( actionName ) ){
			action = new JoinSuccessAction();
		} else {
			action = new MainAction();
		}
		
		return action;
	}

}
