package com.di;

@CustomComponent
public class UserServiceImpl implements UserService {
   @Override
   public String getUserName() {
     return "shital.devalkar";
   }
}