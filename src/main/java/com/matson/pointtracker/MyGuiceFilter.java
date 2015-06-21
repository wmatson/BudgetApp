package com.matson.pointtracker;

import com.google.inject.servlet.GuiceFilter;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class MyGuiceFilter extends GuiceFilter {}
