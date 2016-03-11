package com.sparenparts.sparenparts;

/**
 * Created by Loknath Shankar on 2/28/2016.
 */
public class ListModel {

    private  String categoryText="";
    private  String imgIcon="";
    private  String metaText="";

    /*********** Set Methods ******************/

    public void setcategoryText(String categoryText)
    {
        this.categoryText = categoryText;
    }

    public void setimgIcon(String imgIcon)
    {
        this.imgIcon = imgIcon;
    }

    public void setmetaText(String metaText)
    {
        this.metaText = metaText;
    }

    /*********** Get Methods ****************/

    public String getcategoryText()
    {
        return this.categoryText;
    }

    public String getimgIcon()
    {
        return this.imgIcon;
    }

    public String getmetaText()
    {
        return this.metaText;
    }
}