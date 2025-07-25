package com.example.backendproject.threadlocal;

public class TraceIdHolder {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    //ThreadLocal에 값을 저장하고 꺼낼 set메서드와 get메서드
    public static void set(String traceId){
        threadLocal.set(traceId);
    }

    public static String get(){
        return threadLocal.get();
    }

    //하나의 요청이 끝났을 때 ThreadLocal에 저장된 값을 지우기 위한 clear 메서드
    public static void clear(){
        threadLocal.remove();
    }
}
