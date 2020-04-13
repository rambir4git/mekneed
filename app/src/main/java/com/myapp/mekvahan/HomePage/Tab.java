package com.myapp.mekvahan.HomePage;

public class Tab {
    private int id;
    private String title;
    private int icons;

    //1-car, 2-bike

    private int tabType;

    public Tab(int id, String title, int icons, int tabType) {
        this.id = id;
        this.title = title;
        this.icons = icons;
        this.tabType = tabType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getIcons() {
        return icons;
    }

    public int getTabType() {
        return tabType;
    }
}
