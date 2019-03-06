package com.zhiqisim.visionworks;

public class Logs {
    private String name;
    private String purpose;
    private long time;
    private String outTime;
    private String license;


    public Logs() {
        //empty constructor needed
    }

    public Logs(String name, String purpose, long time, String license, String outTime) {
        this.name = name;
        this.purpose = purpose;
        this.time = time;
        this.outTime = outTime;
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public long getTime() {
        return time;
    }

    public String getOutTime() {
        return outTime;
    }

    public String getLicense() {
        return license;
    }
}
