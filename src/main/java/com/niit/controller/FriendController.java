package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.FriendDao;
import com.niit.dao.UserDao;

import com.niit.model.Error;
import com.niit.model.Friend;
import com.niit.model.User;

@Controller
public class FriendController {
 
	 @Autowired
	 private FriendDao friendDao;
	 @Autowired
	 private UserDao userDao; 

	 @RequestMapping(value="/getsuggestedusers",method=RequestMethod.GET)
		public ResponseEntity<?> getListOfSuggestedUsers(HttpSession session){
			if(session.getAttribute("username")==null){
			Error error=new Error(5,"UnAuthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		  }
		 
		 String username=(String) session.getAttribute("username");
		 List<User> suggestedUsers=friendDao.getListOfSuggestedUsers(username);
		 return new ResponseEntity<List<User>>(suggestedUsers,HttpStatus.OK); 
				 
	   }
 
	@RequestMapping(value="/friendrequest/{toId}",method=RequestMethod.POST)
	public ResponseEntity<?>friendRequest(@PathVariable String toId,HttpSession session){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"UnAthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		String username=(String) session.getAttribute("username");
		friendDao.addFriendRequest(username, toId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getpendingrequests",method=RequestMethod.GET)
	public ResponseEntity<?>getPendingRequests(HttpSession session){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"UnAthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}	
		String username=(String) session.getAttribute("username");
		List<Friend>pendingRequests=friendDao.getPendingRequests(username);
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatependingrequest",method=RequestMethod.PUT)
	public ResponseEntity<?>updatePendingRequests(@RequestBody Friend pendingRequest,HttpSession session){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"UnAthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}	
		friendDao.updatePendingRequest(pendingRequest);//updated status 'A'/'D'
		return new ResponseEntity<Friend>(pendingRequest,HttpStatus.OK);
	}
	
	@RequestMapping(value="/listoffriends",method=RequestMethod.GET)
	public ResponseEntity<?>listOfFriends(HttpSession session){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"UnAthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		List<Friend>friends=friendDao.listOfFriends(username);
		return new ResponseEntity<List<Friend>>(friends,HttpStatus.OK);
	}
}
