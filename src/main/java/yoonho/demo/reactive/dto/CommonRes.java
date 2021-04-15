package yoonho.demo.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class CommonRes<T> {
	private String returnCode;
	private String message;
	private T data;
	
}
