package com.sparenparts.sparenparts;

/**
 * Created by Loknath Shankar on 2/28/2016.
 */
public class ItemList {
    public int imgIcon;
    public String categoryText;
    public String metaText;
    public ItemList(){
        super();
    }

    public ItemList(int imgIcon, String categoryText,String metaText) {
        super();
        this.imgIcon = imgIcon;
        this.categoryText = categoryText;
        this.metaText = metaText;
    }
}