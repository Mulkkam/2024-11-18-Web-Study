package com.sist.vo;

import java.util.*;

import lombok.Data;

@Data
public class DataBoardVO {
	private int no,hit,filesize;
	private String name,subject,content,pwd,filename;
	private Date regdate;
}
