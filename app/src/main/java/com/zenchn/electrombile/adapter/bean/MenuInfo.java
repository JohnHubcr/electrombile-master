package com.zenchn.electrombile.adapter.bean;

/**
 * 作    者：2016/10/22 0022 22:34
 * 描    述：
 * 修订记录：
 */
public class MenuInfo {

    private String title;
    private int iconId;

    public MenuInfo(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "title='" + title + '\'' +
                ", iconId=" + iconId +
                '}';
    }
}
