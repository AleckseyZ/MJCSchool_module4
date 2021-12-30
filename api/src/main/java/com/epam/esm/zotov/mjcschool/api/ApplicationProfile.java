package com.epam.esm.zotov.mjcschool.api;

public enum ApplicationProfile {
    PROD("prod"), DEV("dev");

    private String profileName;

    private ApplicationProfile(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public String toString() {
        return profileName;
    }
}