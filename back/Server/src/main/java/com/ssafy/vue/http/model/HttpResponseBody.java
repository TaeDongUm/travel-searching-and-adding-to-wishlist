package com.ssafy.vue.http.model;

public class HttpResponseBody<T> {
	private String result;
	private T data;
	
	public HttpResponseBody(String result, T data) {
		this.result = result;
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "HttpResponseBody [result=" + result + ", data=" + data + "]";
	}
	
	
}
