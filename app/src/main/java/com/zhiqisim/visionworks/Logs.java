package com.zhiqisim.visionworks;

public class Logs {
    private String name;
    private String purpose;
    private long time;
    private boolean checked;
    private String license;

    public Logs() {
        //empty constructor needed
    }

    public Logs(String name, String purpose, long time, boolean checked, String license) {
        this.name = name;
        this.purpose = purpose;
        this.time = time;
        this.checked = checked;
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

    public boolean isChecked() {
        return checked;
    }

    public String getLicense() {
        return license;
    }
}
