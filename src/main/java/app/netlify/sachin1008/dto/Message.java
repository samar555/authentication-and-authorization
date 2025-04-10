package app.netlify.sachin1008.dto;

import java.util.List;

import lombok.Data;


@Data
public class Message {

	boolean isSuccess;
	List<String> message;
	Object data;

}
